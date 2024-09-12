import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAgence } from '../agence.model';
import { AgenceService } from '../service/agence.service';

const agenceResolve = (route: ActivatedRouteSnapshot): Observable<null | IAgence> => {
  const id = route.params.idAgence;
  if (id) {
    return inject(AgenceService)
      .find(id)
      .pipe(
        mergeMap((agence: HttpResponse<IAgence>) => {
          if (agence.body) {
            return of(agence.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default agenceResolve;

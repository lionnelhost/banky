import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJourFerier } from '../jour-ferier.model';
import { JourFerierService } from '../service/jour-ferier.service';

const jourFerierResolve = (route: ActivatedRouteSnapshot): Observable<null | IJourFerier> => {
  const id = route.params.idJourFerie;
  if (id) {
    return inject(JourFerierService)
      .find(id)
      .pipe(
        mergeMap((jourFerier: HttpResponse<IJourFerier>) => {
          if (jourFerier.body) {
            return of(jourFerier.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default jourFerierResolve;

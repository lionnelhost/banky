import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContrat } from '../contrat.model';
import { ContratService } from '../service/contrat.service';

const contratResolve = (route: ActivatedRouteSnapshot): Observable<null | IContrat> => {
  const id = route.params.idContrat;
  if (id) {
    return inject(ContratService)
      .find(id)
      .pipe(
        mergeMap((contrat: HttpResponse<IContrat>) => {
          if (contrat.body) {
            return of(contrat.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default contratResolve;

import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContratAbonnement } from '../contrat-abonnement.model';
import { ContratAbonnementService } from '../service/contrat-abonnement.service';

const contratAbonnementResolve = (route: ActivatedRouteSnapshot): Observable<null | IContratAbonnement> => {
  const id = route.params.id;
  if (id) {
    return inject(ContratAbonnementService)
      .find(id)
      .pipe(
        mergeMap((contratAbonnement: HttpResponse<IContratAbonnement>) => {
          if (contratAbonnement.body) {
            return of(contratAbonnement.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default contratAbonnementResolve;

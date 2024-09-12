import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContratAbonnementCompte } from '../contrat-abonnement-compte.model';
import { ContratAbonnementCompteService } from '../service/contrat-abonnement-compte.service';

const contratAbonnementCompteResolve = (route: ActivatedRouteSnapshot): Observable<null | IContratAbonnementCompte> => {
  const id = route.params.id;
  if (id) {
    return inject(ContratAbonnementCompteService)
      .find(id)
      .pipe(
        mergeMap((contratAbonnementCompte: HttpResponse<IContratAbonnementCompte>) => {
          if (contratAbonnementCompte.body) {
            return of(contratAbonnementCompte.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default contratAbonnementCompteResolve;

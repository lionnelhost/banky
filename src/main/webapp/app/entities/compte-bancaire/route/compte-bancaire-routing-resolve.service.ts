import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICompteBancaire } from '../compte-bancaire.model';
import { CompteBancaireService } from '../service/compte-bancaire.service';

const compteBancaireResolve = (route: ActivatedRouteSnapshot): Observable<null | ICompteBancaire> => {
  const id = route.params.idCompteBancaire;
  if (id) {
    return inject(CompteBancaireService)
      .find(id)
      .pipe(
        mergeMap((compteBancaire: HttpResponse<ICompteBancaire>) => {
          if (compteBancaire.body) {
            return of(compteBancaire.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default compteBancaireResolve;

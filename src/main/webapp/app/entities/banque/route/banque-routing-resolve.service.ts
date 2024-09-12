import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBanque } from '../banque.model';
import { BanqueService } from '../service/banque.service';

const banqueResolve = (route: ActivatedRouteSnapshot): Observable<null | IBanque> => {
  const id = route.params.idBanque;
  if (id) {
    return inject(BanqueService)
      .find(id)
      .pipe(
        mergeMap((banque: HttpResponse<IBanque>) => {
          if (banque.body) {
            return of(banque.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default banqueResolve;

import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAbonne } from '../abonne.model';
import { AbonneService } from '../service/abonne.service';

const abonneResolve = (route: ActivatedRouteSnapshot): Observable<null | IAbonne> => {
  const id = route.params.idAbonne;
  if (id) {
    return inject(AbonneService)
      .find(id)
      .pipe(
        mergeMap((abonne: HttpResponse<IAbonne>) => {
          if (abonne.body) {
            return of(abonne.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default abonneResolve;

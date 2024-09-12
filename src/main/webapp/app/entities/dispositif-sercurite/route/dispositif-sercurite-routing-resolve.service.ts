import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDispositifSercurite } from '../dispositif-sercurite.model';
import { DispositifSercuriteService } from '../service/dispositif-sercurite.service';

const dispositifSercuriteResolve = (route: ActivatedRouteSnapshot): Observable<null | IDispositifSercurite> => {
  const id = route.params.id;
  if (id) {
    return inject(DispositifSercuriteService)
      .find(id)
      .pipe(
        mergeMap((dispositifSercurite: HttpResponse<IDispositifSercurite>) => {
          if (dispositifSercurite.body) {
            return of(dispositifSercurite.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default dispositifSercuriteResolve;

import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICanal } from '../canal.model';
import { CanalService } from '../service/canal.service';

const canalResolve = (route: ActivatedRouteSnapshot): Observable<null | ICanal> => {
  const id = route.params.idCanal;
  if (id) {
    return inject(CanalService)
      .find(id)
      .pipe(
        mergeMap((canal: HttpResponse<ICanal>) => {
          if (canal.body) {
            return of(canal.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default canalResolve;

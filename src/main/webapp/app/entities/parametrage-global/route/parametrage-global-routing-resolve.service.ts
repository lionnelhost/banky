import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IParametrageGlobal } from '../parametrage-global.model';
import { ParametrageGlobalService } from '../service/parametrage-global.service';

const parametrageGlobalResolve = (route: ActivatedRouteSnapshot): Observable<null | IParametrageGlobal> => {
  const id = route.params.idParamGlobal;
  if (id) {
    return inject(ParametrageGlobalService)
      .find(id)
      .pipe(
        mergeMap((parametrageGlobal: HttpResponse<IParametrageGlobal>) => {
          if (parametrageGlobal.body) {
            return of(parametrageGlobal.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default parametrageGlobalResolve;

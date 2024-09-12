import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITypeClient } from '../type-client.model';
import { TypeClientService } from '../service/type-client.service';

const typeClientResolve = (route: ActivatedRouteSnapshot): Observable<null | ITypeClient> => {
  const id = route.params.idTypeClient;
  if (id) {
    return inject(TypeClientService)
      .find(id)
      .pipe(
        mergeMap((typeClient: HttpResponse<ITypeClient>) => {
          if (typeClient.body) {
            return of(typeClient.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default typeClientResolve;

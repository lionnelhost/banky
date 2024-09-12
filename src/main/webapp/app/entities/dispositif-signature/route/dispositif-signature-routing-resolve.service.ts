import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDispositifSignature } from '../dispositif-signature.model';
import { DispositifSignatureService } from '../service/dispositif-signature.service';

const dispositifSignatureResolve = (route: ActivatedRouteSnapshot): Observable<null | IDispositifSignature> => {
  const id = route.params.idDispositif;
  if (id) {
    return inject(DispositifSignatureService)
      .find(id)
      .pipe(
        mergeMap((dispositifSignature: HttpResponse<IDispositifSignature>) => {
          if (dispositifSignature.body) {
            return of(dispositifSignature.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default dispositifSignatureResolve;

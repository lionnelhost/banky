import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITypeTransaction } from '../type-transaction.model';
import { TypeTransactionService } from '../service/type-transaction.service';

const typeTransactionResolve = (route: ActivatedRouteSnapshot): Observable<null | ITypeTransaction> => {
  const id = route.params.idTypeTransaction;
  if (id) {
    return inject(TypeTransactionService)
      .find(id)
      .pipe(
        mergeMap((typeTransaction: HttpResponse<ITypeTransaction>) => {
          if (typeTransaction.body) {
            return of(typeTransaction.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default typeTransactionResolve;

import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVariableNotification } from '../variable-notification.model';
import { VariableNotificationService } from '../service/variable-notification.service';

const variableNotificationResolve = (route: ActivatedRouteSnapshot): Observable<null | IVariableNotification> => {
  const id = route.params.idVarNotification;
  if (id) {
    return inject(VariableNotificationService)
      .find(id)
      .pipe(
        mergeMap((variableNotification: HttpResponse<IVariableNotification>) => {
          if (variableNotification.body) {
            return of(variableNotification.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default variableNotificationResolve;

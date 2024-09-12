import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IParametrageNotification } from '../parametrage-notification.model';
import { ParametrageNotificationService } from '../service/parametrage-notification.service';

const parametrageNotificationResolve = (route: ActivatedRouteSnapshot): Observable<null | IParametrageNotification> => {
  const id = route.params.idParamNotif;
  if (id) {
    return inject(ParametrageNotificationService)
      .find(id)
      .pipe(
        mergeMap((parametrageNotification: HttpResponse<IParametrageNotification>) => {
          if (parametrageNotification.body) {
            return of(parametrageNotification.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default parametrageNotificationResolve;

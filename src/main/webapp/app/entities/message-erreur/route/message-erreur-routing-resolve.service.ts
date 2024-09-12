import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMessageErreur } from '../message-erreur.model';
import { MessageErreurService } from '../service/message-erreur.service';

const messageErreurResolve = (route: ActivatedRouteSnapshot): Observable<null | IMessageErreur> => {
  const id = route.params.idMessageErreur;
  if (id) {
    return inject(MessageErreurService)
      .find(id)
      .pipe(
        mergeMap((messageErreur: HttpResponse<IMessageErreur>) => {
          if (messageErreur.body) {
            return of(messageErreur.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default messageErreurResolve;

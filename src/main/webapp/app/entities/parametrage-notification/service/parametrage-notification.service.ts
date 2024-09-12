import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParametrageNotification, NewParametrageNotification } from '../parametrage-notification.model';

export type PartialUpdateParametrageNotification = Partial<IParametrageNotification> & Pick<IParametrageNotification, 'idParamNotif'>;

export type EntityResponseType = HttpResponse<IParametrageNotification>;
export type EntityArrayResponseType = HttpResponse<IParametrageNotification[]>;

@Injectable({ providedIn: 'root' })
export class ParametrageNotificationService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/parametrage-notifications');

  create(parametrageNotification: NewParametrageNotification): Observable<EntityResponseType> {
    return this.http.post<IParametrageNotification>(this.resourceUrl, parametrageNotification, { observe: 'response' });
  }

  update(parametrageNotification: IParametrageNotification): Observable<EntityResponseType> {
    return this.http.put<IParametrageNotification>(
      `${this.resourceUrl}/${this.getParametrageNotificationIdentifier(parametrageNotification)}`,
      parametrageNotification,
      { observe: 'response' },
    );
  }

  partialUpdate(parametrageNotification: PartialUpdateParametrageNotification): Observable<EntityResponseType> {
    return this.http.patch<IParametrageNotification>(
      `${this.resourceUrl}/${this.getParametrageNotificationIdentifier(parametrageNotification)}`,
      parametrageNotification,
      { observe: 'response' },
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IParametrageNotification>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParametrageNotification[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getParametrageNotificationIdentifier(parametrageNotification: Pick<IParametrageNotification, 'idParamNotif'>): string {
    return parametrageNotification.idParamNotif;
  }

  compareParametrageNotification(
    o1: Pick<IParametrageNotification, 'idParamNotif'> | null,
    o2: Pick<IParametrageNotification, 'idParamNotif'> | null,
  ): boolean {
    return o1 && o2 ? this.getParametrageNotificationIdentifier(o1) === this.getParametrageNotificationIdentifier(o2) : o1 === o2;
  }

  addParametrageNotificationToCollectionIfMissing<Type extends Pick<IParametrageNotification, 'idParamNotif'>>(
    parametrageNotificationCollection: Type[],
    ...parametrageNotificationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const parametrageNotifications: Type[] = parametrageNotificationsToCheck.filter(isPresent);
    if (parametrageNotifications.length > 0) {
      const parametrageNotificationCollectionIdentifiers = parametrageNotificationCollection.map(parametrageNotificationItem =>
        this.getParametrageNotificationIdentifier(parametrageNotificationItem),
      );
      const parametrageNotificationsToAdd = parametrageNotifications.filter(parametrageNotificationItem => {
        const parametrageNotificationIdentifier = this.getParametrageNotificationIdentifier(parametrageNotificationItem);
        if (parametrageNotificationCollectionIdentifiers.includes(parametrageNotificationIdentifier)) {
          return false;
        }
        parametrageNotificationCollectionIdentifiers.push(parametrageNotificationIdentifier);
        return true;
      });
      return [...parametrageNotificationsToAdd, ...parametrageNotificationCollection];
    }
    return parametrageNotificationCollection;
  }
}

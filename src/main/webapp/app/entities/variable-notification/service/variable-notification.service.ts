import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVariableNotification, NewVariableNotification } from '../variable-notification.model';

export type PartialUpdateVariableNotification = Partial<IVariableNotification> & Pick<IVariableNotification, 'idVarNotification'>;

export type EntityResponseType = HttpResponse<IVariableNotification>;
export type EntityArrayResponseType = HttpResponse<IVariableNotification[]>;

@Injectable({ providedIn: 'root' })
export class VariableNotificationService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/variable-notifications');

  create(variableNotification: NewVariableNotification): Observable<EntityResponseType> {
    return this.http.post<IVariableNotification>(this.resourceUrl, variableNotification, { observe: 'response' });
  }

  update(variableNotification: IVariableNotification): Observable<EntityResponseType> {
    return this.http.put<IVariableNotification>(
      `${this.resourceUrl}/${this.getVariableNotificationIdentifier(variableNotification)}`,
      variableNotification,
      { observe: 'response' },
    );
  }

  partialUpdate(variableNotification: PartialUpdateVariableNotification): Observable<EntityResponseType> {
    return this.http.patch<IVariableNotification>(
      `${this.resourceUrl}/${this.getVariableNotificationIdentifier(variableNotification)}`,
      variableNotification,
      { observe: 'response' },
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IVariableNotification>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVariableNotification[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVariableNotificationIdentifier(variableNotification: Pick<IVariableNotification, 'idVarNotification'>): string {
    return variableNotification.idVarNotification;
  }

  compareVariableNotification(
    o1: Pick<IVariableNotification, 'idVarNotification'> | null,
    o2: Pick<IVariableNotification, 'idVarNotification'> | null,
  ): boolean {
    return o1 && o2 ? this.getVariableNotificationIdentifier(o1) === this.getVariableNotificationIdentifier(o2) : o1 === o2;
  }

  addVariableNotificationToCollectionIfMissing<Type extends Pick<IVariableNotification, 'idVarNotification'>>(
    variableNotificationCollection: Type[],
    ...variableNotificationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const variableNotifications: Type[] = variableNotificationsToCheck.filter(isPresent);
    if (variableNotifications.length > 0) {
      const variableNotificationCollectionIdentifiers = variableNotificationCollection.map(variableNotificationItem =>
        this.getVariableNotificationIdentifier(variableNotificationItem),
      );
      const variableNotificationsToAdd = variableNotifications.filter(variableNotificationItem => {
        const variableNotificationIdentifier = this.getVariableNotificationIdentifier(variableNotificationItem);
        if (variableNotificationCollectionIdentifiers.includes(variableNotificationIdentifier)) {
          return false;
        }
        variableNotificationCollectionIdentifiers.push(variableNotificationIdentifier);
        return true;
      });
      return [...variableNotificationsToAdd, ...variableNotificationCollection];
    }
    return variableNotificationCollection;
  }
}

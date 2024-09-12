import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMessageErreur, NewMessageErreur } from '../message-erreur.model';

export type PartialUpdateMessageErreur = Partial<IMessageErreur> & Pick<IMessageErreur, 'idMessageErreur'>;

export type EntityResponseType = HttpResponse<IMessageErreur>;
export type EntityArrayResponseType = HttpResponse<IMessageErreur[]>;

@Injectable({ providedIn: 'root' })
export class MessageErreurService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/message-erreurs');

  create(messageErreur: NewMessageErreur): Observable<EntityResponseType> {
    return this.http.post<IMessageErreur>(this.resourceUrl, messageErreur, { observe: 'response' });
  }

  update(messageErreur: IMessageErreur): Observable<EntityResponseType> {
    return this.http.put<IMessageErreur>(`${this.resourceUrl}/${this.getMessageErreurIdentifier(messageErreur)}`, messageErreur, {
      observe: 'response',
    });
  }

  partialUpdate(messageErreur: PartialUpdateMessageErreur): Observable<EntityResponseType> {
    return this.http.patch<IMessageErreur>(`${this.resourceUrl}/${this.getMessageErreurIdentifier(messageErreur)}`, messageErreur, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IMessageErreur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMessageErreur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMessageErreurIdentifier(messageErreur: Pick<IMessageErreur, 'idMessageErreur'>): string {
    return messageErreur.idMessageErreur;
  }

  compareMessageErreur(o1: Pick<IMessageErreur, 'idMessageErreur'> | null, o2: Pick<IMessageErreur, 'idMessageErreur'> | null): boolean {
    return o1 && o2 ? this.getMessageErreurIdentifier(o1) === this.getMessageErreurIdentifier(o2) : o1 === o2;
  }

  addMessageErreurToCollectionIfMissing<Type extends Pick<IMessageErreur, 'idMessageErreur'>>(
    messageErreurCollection: Type[],
    ...messageErreursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const messageErreurs: Type[] = messageErreursToCheck.filter(isPresent);
    if (messageErreurs.length > 0) {
      const messageErreurCollectionIdentifiers = messageErreurCollection.map(messageErreurItem =>
        this.getMessageErreurIdentifier(messageErreurItem),
      );
      const messageErreursToAdd = messageErreurs.filter(messageErreurItem => {
        const messageErreurIdentifier = this.getMessageErreurIdentifier(messageErreurItem);
        if (messageErreurCollectionIdentifiers.includes(messageErreurIdentifier)) {
          return false;
        }
        messageErreurCollectionIdentifiers.push(messageErreurIdentifier);
        return true;
      });
      return [...messageErreursToAdd, ...messageErreurCollection];
    }
    return messageErreurCollection;
  }
}

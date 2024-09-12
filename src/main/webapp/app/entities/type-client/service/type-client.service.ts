import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITypeClient, NewTypeClient } from '../type-client.model';

export type PartialUpdateTypeClient = Partial<ITypeClient> & Pick<ITypeClient, 'idTypeClient'>;

export type EntityResponseType = HttpResponse<ITypeClient>;
export type EntityArrayResponseType = HttpResponse<ITypeClient[]>;

@Injectable({ providedIn: 'root' })
export class TypeClientService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/type-clients');

  create(typeClient: NewTypeClient): Observable<EntityResponseType> {
    return this.http.post<ITypeClient>(this.resourceUrl, typeClient, { observe: 'response' });
  }

  update(typeClient: ITypeClient): Observable<EntityResponseType> {
    return this.http.put<ITypeClient>(`${this.resourceUrl}/${this.getTypeClientIdentifier(typeClient)}`, typeClient, {
      observe: 'response',
    });
  }

  partialUpdate(typeClient: PartialUpdateTypeClient): Observable<EntityResponseType> {
    return this.http.patch<ITypeClient>(`${this.resourceUrl}/${this.getTypeClientIdentifier(typeClient)}`, typeClient, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ITypeClient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeClient[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTypeClientIdentifier(typeClient: Pick<ITypeClient, 'idTypeClient'>): string {
    return typeClient.idTypeClient;
  }

  compareTypeClient(o1: Pick<ITypeClient, 'idTypeClient'> | null, o2: Pick<ITypeClient, 'idTypeClient'> | null): boolean {
    return o1 && o2 ? this.getTypeClientIdentifier(o1) === this.getTypeClientIdentifier(o2) : o1 === o2;
  }

  addTypeClientToCollectionIfMissing<Type extends Pick<ITypeClient, 'idTypeClient'>>(
    typeClientCollection: Type[],
    ...typeClientsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const typeClients: Type[] = typeClientsToCheck.filter(isPresent);
    if (typeClients.length > 0) {
      const typeClientCollectionIdentifiers = typeClientCollection.map(typeClientItem => this.getTypeClientIdentifier(typeClientItem));
      const typeClientsToAdd = typeClients.filter(typeClientItem => {
        const typeClientIdentifier = this.getTypeClientIdentifier(typeClientItem);
        if (typeClientCollectionIdentifiers.includes(typeClientIdentifier)) {
          return false;
        }
        typeClientCollectionIdentifiers.push(typeClientIdentifier);
        return true;
      });
      return [...typeClientsToAdd, ...typeClientCollection];
    }
    return typeClientCollection;
  }
}

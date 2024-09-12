import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBanque, NewBanque } from '../banque.model';

export type PartialUpdateBanque = Partial<IBanque> & Pick<IBanque, 'idBanque'>;

export type EntityResponseType = HttpResponse<IBanque>;
export type EntityArrayResponseType = HttpResponse<IBanque[]>;

@Injectable({ providedIn: 'root' })
export class BanqueService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/banques');

  create(banque: NewBanque): Observable<EntityResponseType> {
    return this.http.post<IBanque>(this.resourceUrl, banque, { observe: 'response' });
  }

  update(banque: IBanque): Observable<EntityResponseType> {
    return this.http.put<IBanque>(`${this.resourceUrl}/${this.getBanqueIdentifier(banque)}`, banque, { observe: 'response' });
  }

  partialUpdate(banque: PartialUpdateBanque): Observable<EntityResponseType> {
    return this.http.patch<IBanque>(`${this.resourceUrl}/${this.getBanqueIdentifier(banque)}`, banque, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IBanque>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBanque[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBanqueIdentifier(banque: Pick<IBanque, 'idBanque'>): string {
    return banque.idBanque;
  }

  compareBanque(o1: Pick<IBanque, 'idBanque'> | null, o2: Pick<IBanque, 'idBanque'> | null): boolean {
    return o1 && o2 ? this.getBanqueIdentifier(o1) === this.getBanqueIdentifier(o2) : o1 === o2;
  }

  addBanqueToCollectionIfMissing<Type extends Pick<IBanque, 'idBanque'>>(
    banqueCollection: Type[],
    ...banquesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const banques: Type[] = banquesToCheck.filter(isPresent);
    if (banques.length > 0) {
      const banqueCollectionIdentifiers = banqueCollection.map(banqueItem => this.getBanqueIdentifier(banqueItem));
      const banquesToAdd = banques.filter(banqueItem => {
        const banqueIdentifier = this.getBanqueIdentifier(banqueItem);
        if (banqueCollectionIdentifiers.includes(banqueIdentifier)) {
          return false;
        }
        banqueCollectionIdentifiers.push(banqueIdentifier);
        return true;
      });
      return [...banquesToAdd, ...banqueCollection];
    }
    return banqueCollection;
  }
}

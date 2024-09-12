import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAbonne, NewAbonne } from '../abonne.model';

export type PartialUpdateAbonne = Partial<IAbonne> & Pick<IAbonne, 'idAbonne'>;

export type EntityResponseType = HttpResponse<IAbonne>;
export type EntityArrayResponseType = HttpResponse<IAbonne[]>;

@Injectable({ providedIn: 'root' })
export class AbonneService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/abonnes');

  create(abonne: NewAbonne): Observable<EntityResponseType> {
    return this.http.post<IAbonne>(this.resourceUrl, abonne, { observe: 'response' });
  }

  update(abonne: IAbonne): Observable<EntityResponseType> {
    return this.http.put<IAbonne>(`${this.resourceUrl}/${this.getAbonneIdentifier(abonne)}`, abonne, { observe: 'response' });
  }

  partialUpdate(abonne: PartialUpdateAbonne): Observable<EntityResponseType> {
    return this.http.patch<IAbonne>(`${this.resourceUrl}/${this.getAbonneIdentifier(abonne)}`, abonne, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IAbonne>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAbonne[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAbonneIdentifier(abonne: Pick<IAbonne, 'idAbonne'>): string {
    return abonne.idAbonne;
  }

  compareAbonne(o1: Pick<IAbonne, 'idAbonne'> | null, o2: Pick<IAbonne, 'idAbonne'> | null): boolean {
    return o1 && o2 ? this.getAbonneIdentifier(o1) === this.getAbonneIdentifier(o2) : o1 === o2;
  }

  addAbonneToCollectionIfMissing<Type extends Pick<IAbonne, 'idAbonne'>>(
    abonneCollection: Type[],
    ...abonnesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const abonnes: Type[] = abonnesToCheck.filter(isPresent);
    if (abonnes.length > 0) {
      const abonneCollectionIdentifiers = abonneCollection.map(abonneItem => this.getAbonneIdentifier(abonneItem));
      const abonnesToAdd = abonnes.filter(abonneItem => {
        const abonneIdentifier = this.getAbonneIdentifier(abonneItem);
        if (abonneCollectionIdentifiers.includes(abonneIdentifier)) {
          return false;
        }
        abonneCollectionIdentifiers.push(abonneIdentifier);
        return true;
      });
      return [...abonnesToAdd, ...abonneCollection];
    }
    return abonneCollection;
  }
}

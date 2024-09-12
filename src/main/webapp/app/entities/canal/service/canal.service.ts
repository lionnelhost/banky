import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICanal, NewCanal } from '../canal.model';

export type PartialUpdateCanal = Partial<ICanal> & Pick<ICanal, 'idCanal'>;

export type EntityResponseType = HttpResponse<ICanal>;
export type EntityArrayResponseType = HttpResponse<ICanal[]>;

@Injectable({ providedIn: 'root' })
export class CanalService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/canals');

  create(canal: NewCanal): Observable<EntityResponseType> {
    return this.http.post<ICanal>(this.resourceUrl, canal, { observe: 'response' });
  }

  update(canal: ICanal): Observable<EntityResponseType> {
    return this.http.put<ICanal>(`${this.resourceUrl}/${this.getCanalIdentifier(canal)}`, canal, { observe: 'response' });
  }

  partialUpdate(canal: PartialUpdateCanal): Observable<EntityResponseType> {
    return this.http.patch<ICanal>(`${this.resourceUrl}/${this.getCanalIdentifier(canal)}`, canal, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ICanal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICanal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCanalIdentifier(canal: Pick<ICanal, 'idCanal'>): string {
    return canal.idCanal;
  }

  compareCanal(o1: Pick<ICanal, 'idCanal'> | null, o2: Pick<ICanal, 'idCanal'> | null): boolean {
    return o1 && o2 ? this.getCanalIdentifier(o1) === this.getCanalIdentifier(o2) : o1 === o2;
  }

  addCanalToCollectionIfMissing<Type extends Pick<ICanal, 'idCanal'>>(
    canalCollection: Type[],
    ...canalsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const canals: Type[] = canalsToCheck.filter(isPresent);
    if (canals.length > 0) {
      const canalCollectionIdentifiers = canalCollection.map(canalItem => this.getCanalIdentifier(canalItem));
      const canalsToAdd = canals.filter(canalItem => {
        const canalIdentifier = this.getCanalIdentifier(canalItem);
        if (canalCollectionIdentifiers.includes(canalIdentifier)) {
          return false;
        }
        canalCollectionIdentifiers.push(canalIdentifier);
        return true;
      });
      return [...canalsToAdd, ...canalCollection];
    }
    return canalCollection;
  }
}

import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJourFerier, NewJourFerier } from '../jour-ferier.model';

export type PartialUpdateJourFerier = Partial<IJourFerier> & Pick<IJourFerier, 'idJourFerie'>;

export type EntityResponseType = HttpResponse<IJourFerier>;
export type EntityArrayResponseType = HttpResponse<IJourFerier[]>;

@Injectable({ providedIn: 'root' })
export class JourFerierService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/jour-feriers');

  create(jourFerier: NewJourFerier): Observable<EntityResponseType> {
    return this.http.post<IJourFerier>(this.resourceUrl, jourFerier, { observe: 'response' });
  }

  update(jourFerier: IJourFerier): Observable<EntityResponseType> {
    return this.http.put<IJourFerier>(`${this.resourceUrl}/${this.getJourFerierIdentifier(jourFerier)}`, jourFerier, {
      observe: 'response',
    });
  }

  partialUpdate(jourFerier: PartialUpdateJourFerier): Observable<EntityResponseType> {
    return this.http.patch<IJourFerier>(`${this.resourceUrl}/${this.getJourFerierIdentifier(jourFerier)}`, jourFerier, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IJourFerier>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJourFerier[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getJourFerierIdentifier(jourFerier: Pick<IJourFerier, 'idJourFerie'>): string {
    return jourFerier.idJourFerie;
  }

  compareJourFerier(o1: Pick<IJourFerier, 'idJourFerie'> | null, o2: Pick<IJourFerier, 'idJourFerie'> | null): boolean {
    return o1 && o2 ? this.getJourFerierIdentifier(o1) === this.getJourFerierIdentifier(o2) : o1 === o2;
  }

  addJourFerierToCollectionIfMissing<Type extends Pick<IJourFerier, 'idJourFerie'>>(
    jourFerierCollection: Type[],
    ...jourFeriersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const jourFeriers: Type[] = jourFeriersToCheck.filter(isPresent);
    if (jourFeriers.length > 0) {
      const jourFerierCollectionIdentifiers = jourFerierCollection.map(jourFerierItem => this.getJourFerierIdentifier(jourFerierItem));
      const jourFeriersToAdd = jourFeriers.filter(jourFerierItem => {
        const jourFerierIdentifier = this.getJourFerierIdentifier(jourFerierItem);
        if (jourFerierCollectionIdentifiers.includes(jourFerierIdentifier)) {
          return false;
        }
        jourFerierCollectionIdentifiers.push(jourFerierIdentifier);
        return true;
      });
      return [...jourFeriersToAdd, ...jourFerierCollection];
    }
    return jourFerierCollection;
  }
}

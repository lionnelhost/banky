import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDispositifSercurite, NewDispositifSercurite } from '../dispositif-sercurite.model';

export type PartialUpdateDispositifSercurite = Partial<IDispositifSercurite> & Pick<IDispositifSercurite, 'id'>;

export type EntityResponseType = HttpResponse<IDispositifSercurite>;
export type EntityArrayResponseType = HttpResponse<IDispositifSercurite[]>;

@Injectable({ providedIn: 'root' })
export class DispositifSercuriteService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dispositif-sercurites');

  create(dispositifSercurite: NewDispositifSercurite): Observable<EntityResponseType> {
    return this.http.post<IDispositifSercurite>(this.resourceUrl, dispositifSercurite, { observe: 'response' });
  }

  update(dispositifSercurite: IDispositifSercurite): Observable<EntityResponseType> {
    return this.http.put<IDispositifSercurite>(
      `${this.resourceUrl}/${this.getDispositifSercuriteIdentifier(dispositifSercurite)}`,
      dispositifSercurite,
      { observe: 'response' },
    );
  }

  partialUpdate(dispositifSercurite: PartialUpdateDispositifSercurite): Observable<EntityResponseType> {
    return this.http.patch<IDispositifSercurite>(
      `${this.resourceUrl}/${this.getDispositifSercuriteIdentifier(dispositifSercurite)}`,
      dispositifSercurite,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDispositifSercurite>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDispositifSercurite[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDispositifSercuriteIdentifier(dispositifSercurite: Pick<IDispositifSercurite, 'id'>): number {
    return dispositifSercurite.id;
  }

  compareDispositifSercurite(o1: Pick<IDispositifSercurite, 'id'> | null, o2: Pick<IDispositifSercurite, 'id'> | null): boolean {
    return o1 && o2 ? this.getDispositifSercuriteIdentifier(o1) === this.getDispositifSercuriteIdentifier(o2) : o1 === o2;
  }

  addDispositifSercuriteToCollectionIfMissing<Type extends Pick<IDispositifSercurite, 'id'>>(
    dispositifSercuriteCollection: Type[],
    ...dispositifSercuritesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dispositifSercurites: Type[] = dispositifSercuritesToCheck.filter(isPresent);
    if (dispositifSercurites.length > 0) {
      const dispositifSercuriteCollectionIdentifiers = dispositifSercuriteCollection.map(dispositifSercuriteItem =>
        this.getDispositifSercuriteIdentifier(dispositifSercuriteItem),
      );
      const dispositifSercuritesToAdd = dispositifSercurites.filter(dispositifSercuriteItem => {
        const dispositifSercuriteIdentifier = this.getDispositifSercuriteIdentifier(dispositifSercuriteItem);
        if (dispositifSercuriteCollectionIdentifiers.includes(dispositifSercuriteIdentifier)) {
          return false;
        }
        dispositifSercuriteCollectionIdentifiers.push(dispositifSercuriteIdentifier);
        return true;
      });
      return [...dispositifSercuritesToAdd, ...dispositifSercuriteCollection];
    }
    return dispositifSercuriteCollection;
  }
}

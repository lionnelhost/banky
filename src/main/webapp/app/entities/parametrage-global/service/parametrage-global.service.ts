import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParametrageGlobal, NewParametrageGlobal } from '../parametrage-global.model';

export type PartialUpdateParametrageGlobal = Partial<IParametrageGlobal> & Pick<IParametrageGlobal, 'idParamGlobal'>;

export type EntityResponseType = HttpResponse<IParametrageGlobal>;
export type EntityArrayResponseType = HttpResponse<IParametrageGlobal[]>;

@Injectable({ providedIn: 'root' })
export class ParametrageGlobalService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/parametrage-globals');

  create(parametrageGlobal: NewParametrageGlobal): Observable<EntityResponseType> {
    return this.http.post<IParametrageGlobal>(this.resourceUrl, parametrageGlobal, { observe: 'response' });
  }

  update(parametrageGlobal: IParametrageGlobal): Observable<EntityResponseType> {
    return this.http.put<IParametrageGlobal>(
      `${this.resourceUrl}/${this.getParametrageGlobalIdentifier(parametrageGlobal)}`,
      parametrageGlobal,
      { observe: 'response' },
    );
  }

  partialUpdate(parametrageGlobal: PartialUpdateParametrageGlobal): Observable<EntityResponseType> {
    return this.http.patch<IParametrageGlobal>(
      `${this.resourceUrl}/${this.getParametrageGlobalIdentifier(parametrageGlobal)}`,
      parametrageGlobal,
      { observe: 'response' },
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IParametrageGlobal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParametrageGlobal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getParametrageGlobalIdentifier(parametrageGlobal: Pick<IParametrageGlobal, 'idParamGlobal'>): string {
    return parametrageGlobal.idParamGlobal;
  }

  compareParametrageGlobal(
    o1: Pick<IParametrageGlobal, 'idParamGlobal'> | null,
    o2: Pick<IParametrageGlobal, 'idParamGlobal'> | null,
  ): boolean {
    return o1 && o2 ? this.getParametrageGlobalIdentifier(o1) === this.getParametrageGlobalIdentifier(o2) : o1 === o2;
  }

  addParametrageGlobalToCollectionIfMissing<Type extends Pick<IParametrageGlobal, 'idParamGlobal'>>(
    parametrageGlobalCollection: Type[],
    ...parametrageGlobalsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const parametrageGlobals: Type[] = parametrageGlobalsToCheck.filter(isPresent);
    if (parametrageGlobals.length > 0) {
      const parametrageGlobalCollectionIdentifiers = parametrageGlobalCollection.map(parametrageGlobalItem =>
        this.getParametrageGlobalIdentifier(parametrageGlobalItem),
      );
      const parametrageGlobalsToAdd = parametrageGlobals.filter(parametrageGlobalItem => {
        const parametrageGlobalIdentifier = this.getParametrageGlobalIdentifier(parametrageGlobalItem);
        if (parametrageGlobalCollectionIdentifiers.includes(parametrageGlobalIdentifier)) {
          return false;
        }
        parametrageGlobalCollectionIdentifiers.push(parametrageGlobalIdentifier);
        return true;
      });
      return [...parametrageGlobalsToAdd, ...parametrageGlobalCollection];
    }
    return parametrageGlobalCollection;
  }
}

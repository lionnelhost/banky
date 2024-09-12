import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContrat, NewContrat } from '../contrat.model';

export type PartialUpdateContrat = Partial<IContrat> & Pick<IContrat, 'idContrat'>;

type RestOf<T extends IContrat | NewContrat> = Omit<T, 'dateValidite'> & {
  dateValidite?: string | null;
};

export type RestContrat = RestOf<IContrat>;

export type NewRestContrat = RestOf<NewContrat>;

export type PartialUpdateRestContrat = RestOf<PartialUpdateContrat>;

export type EntityResponseType = HttpResponse<IContrat>;
export type EntityArrayResponseType = HttpResponse<IContrat[]>;

@Injectable({ providedIn: 'root' })
export class ContratService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contrats');

  create(contrat: NewContrat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contrat);
    return this.http
      .post<RestContrat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(contrat: IContrat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contrat);
    return this.http
      .put<RestContrat>(`${this.resourceUrl}/${this.getContratIdentifier(contrat)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(contrat: PartialUpdateContrat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contrat);
    return this.http
      .patch<RestContrat>(`${this.resourceUrl}/${this.getContratIdentifier(contrat)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestContrat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestContrat[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContratIdentifier(contrat: Pick<IContrat, 'idContrat'>): string {
    return contrat.idContrat;
  }

  compareContrat(o1: Pick<IContrat, 'idContrat'> | null, o2: Pick<IContrat, 'idContrat'> | null): boolean {
    return o1 && o2 ? this.getContratIdentifier(o1) === this.getContratIdentifier(o2) : o1 === o2;
  }

  addContratToCollectionIfMissing<Type extends Pick<IContrat, 'idContrat'>>(
    contratCollection: Type[],
    ...contratsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const contrats: Type[] = contratsToCheck.filter(isPresent);
    if (contrats.length > 0) {
      const contratCollectionIdentifiers = contratCollection.map(contratItem => this.getContratIdentifier(contratItem));
      const contratsToAdd = contrats.filter(contratItem => {
        const contratIdentifier = this.getContratIdentifier(contratItem);
        if (contratCollectionIdentifiers.includes(contratIdentifier)) {
          return false;
        }
        contratCollectionIdentifiers.push(contratIdentifier);
        return true;
      });
      return [...contratsToAdd, ...contratCollection];
    }
    return contratCollection;
  }

  protected convertDateFromClient<T extends IContrat | NewContrat | PartialUpdateContrat>(contrat: T): RestOf<T> {
    return {
      ...contrat,
      dateValidite: contrat.dateValidite?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restContrat: RestContrat): IContrat {
    return {
      ...restContrat,
      dateValidite: restContrat.dateValidite ? dayjs(restContrat.dateValidite) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestContrat>): HttpResponse<IContrat> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestContrat[]>): HttpResponse<IContrat[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

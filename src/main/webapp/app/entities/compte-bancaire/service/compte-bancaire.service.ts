import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICompteBancaire, NewCompteBancaire } from '../compte-bancaire.model';

export type PartialUpdateCompteBancaire = Partial<ICompteBancaire> & Pick<ICompteBancaire, 'idCompteBancaire'>;

export type EntityResponseType = HttpResponse<ICompteBancaire>;
export type EntityArrayResponseType = HttpResponse<ICompteBancaire[]>;

@Injectable({ providedIn: 'root' })
export class CompteBancaireService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/compte-bancaires');

  create(compteBancaire: NewCompteBancaire): Observable<EntityResponseType> {
    return this.http.post<ICompteBancaire>(this.resourceUrl, compteBancaire, { observe: 'response' });
  }

  update(compteBancaire: ICompteBancaire): Observable<EntityResponseType> {
    return this.http.put<ICompteBancaire>(`${this.resourceUrl}/${this.getCompteBancaireIdentifier(compteBancaire)}`, compteBancaire, {
      observe: 'response',
    });
  }

  partialUpdate(compteBancaire: PartialUpdateCompteBancaire): Observable<EntityResponseType> {
    return this.http.patch<ICompteBancaire>(`${this.resourceUrl}/${this.getCompteBancaireIdentifier(compteBancaire)}`, compteBancaire, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ICompteBancaire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompteBancaire[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCompteBancaireIdentifier(compteBancaire: Pick<ICompteBancaire, 'idCompteBancaire'>): string {
    return compteBancaire.idCompteBancaire;
  }

  compareCompteBancaire(
    o1: Pick<ICompteBancaire, 'idCompteBancaire'> | null,
    o2: Pick<ICompteBancaire, 'idCompteBancaire'> | null,
  ): boolean {
    return o1 && o2 ? this.getCompteBancaireIdentifier(o1) === this.getCompteBancaireIdentifier(o2) : o1 === o2;
  }

  addCompteBancaireToCollectionIfMissing<Type extends Pick<ICompteBancaire, 'idCompteBancaire'>>(
    compteBancaireCollection: Type[],
    ...compteBancairesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const compteBancaires: Type[] = compteBancairesToCheck.filter(isPresent);
    if (compteBancaires.length > 0) {
      const compteBancaireCollectionIdentifiers = compteBancaireCollection.map(compteBancaireItem =>
        this.getCompteBancaireIdentifier(compteBancaireItem),
      );
      const compteBancairesToAdd = compteBancaires.filter(compteBancaireItem => {
        const compteBancaireIdentifier = this.getCompteBancaireIdentifier(compteBancaireItem);
        if (compteBancaireCollectionIdentifiers.includes(compteBancaireIdentifier)) {
          return false;
        }
        compteBancaireCollectionIdentifiers.push(compteBancaireIdentifier);
        return true;
      });
      return [...compteBancairesToAdd, ...compteBancaireCollection];
    }
    return compteBancaireCollection;
  }
}

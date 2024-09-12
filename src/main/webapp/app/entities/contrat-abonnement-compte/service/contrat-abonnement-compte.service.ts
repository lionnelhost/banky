import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContratAbonnementCompte, NewContratAbonnementCompte } from '../contrat-abonnement-compte.model';

export type PartialUpdateContratAbonnementCompte = Partial<IContratAbonnementCompte> & Pick<IContratAbonnementCompte, 'id'>;

export type EntityResponseType = HttpResponse<IContratAbonnementCompte>;
export type EntityArrayResponseType = HttpResponse<IContratAbonnementCompte[]>;

@Injectable({ providedIn: 'root' })
export class ContratAbonnementCompteService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contrat-abonnement-comptes');

  create(contratAbonnementCompte: NewContratAbonnementCompte): Observable<EntityResponseType> {
    return this.http.post<IContratAbonnementCompte>(this.resourceUrl, contratAbonnementCompte, { observe: 'response' });
  }

  update(contratAbonnementCompte: IContratAbonnementCompte): Observable<EntityResponseType> {
    return this.http.put<IContratAbonnementCompte>(
      `${this.resourceUrl}/${this.getContratAbonnementCompteIdentifier(contratAbonnementCompte)}`,
      contratAbonnementCompte,
      { observe: 'response' },
    );
  }

  partialUpdate(contratAbonnementCompte: PartialUpdateContratAbonnementCompte): Observable<EntityResponseType> {
    return this.http.patch<IContratAbonnementCompte>(
      `${this.resourceUrl}/${this.getContratAbonnementCompteIdentifier(contratAbonnementCompte)}`,
      contratAbonnementCompte,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContratAbonnementCompte>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContratAbonnementCompte[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContratAbonnementCompteIdentifier(contratAbonnementCompte: Pick<IContratAbonnementCompte, 'id'>): number {
    return contratAbonnementCompte.id;
  }

  compareContratAbonnementCompte(
    o1: Pick<IContratAbonnementCompte, 'id'> | null,
    o2: Pick<IContratAbonnementCompte, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getContratAbonnementCompteIdentifier(o1) === this.getContratAbonnementCompteIdentifier(o2) : o1 === o2;
  }

  addContratAbonnementCompteToCollectionIfMissing<Type extends Pick<IContratAbonnementCompte, 'id'>>(
    contratAbonnementCompteCollection: Type[],
    ...contratAbonnementComptesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const contratAbonnementComptes: Type[] = contratAbonnementComptesToCheck.filter(isPresent);
    if (contratAbonnementComptes.length > 0) {
      const contratAbonnementCompteCollectionIdentifiers = contratAbonnementCompteCollection.map(contratAbonnementCompteItem =>
        this.getContratAbonnementCompteIdentifier(contratAbonnementCompteItem),
      );
      const contratAbonnementComptesToAdd = contratAbonnementComptes.filter(contratAbonnementCompteItem => {
        const contratAbonnementCompteIdentifier = this.getContratAbonnementCompteIdentifier(contratAbonnementCompteItem);
        if (contratAbonnementCompteCollectionIdentifiers.includes(contratAbonnementCompteIdentifier)) {
          return false;
        }
        contratAbonnementCompteCollectionIdentifiers.push(contratAbonnementCompteIdentifier);
        return true;
      });
      return [...contratAbonnementComptesToAdd, ...contratAbonnementCompteCollection];
    }
    return contratAbonnementCompteCollection;
  }
}

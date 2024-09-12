import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContratAbonnement, NewContratAbonnement } from '../contrat-abonnement.model';

export type PartialUpdateContratAbonnement = Partial<IContratAbonnement> & Pick<IContratAbonnement, 'id'>;

export type EntityResponseType = HttpResponse<IContratAbonnement>;
export type EntityArrayResponseType = HttpResponse<IContratAbonnement[]>;

@Injectable({ providedIn: 'root' })
export class ContratAbonnementService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contrat-abonnements');

  create(contratAbonnement: NewContratAbonnement): Observable<EntityResponseType> {
    return this.http.post<IContratAbonnement>(this.resourceUrl, contratAbonnement, { observe: 'response' });
  }

  update(contratAbonnement: IContratAbonnement): Observable<EntityResponseType> {
    return this.http.put<IContratAbonnement>(
      `${this.resourceUrl}/${this.getContratAbonnementIdentifier(contratAbonnement)}`,
      contratAbonnement,
      { observe: 'response' },
    );
  }

  partialUpdate(contratAbonnement: PartialUpdateContratAbonnement): Observable<EntityResponseType> {
    return this.http.patch<IContratAbonnement>(
      `${this.resourceUrl}/${this.getContratAbonnementIdentifier(contratAbonnement)}`,
      contratAbonnement,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContratAbonnement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContratAbonnement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContratAbonnementIdentifier(contratAbonnement: Pick<IContratAbonnement, 'id'>): number {
    return contratAbonnement.id;
  }

  compareContratAbonnement(o1: Pick<IContratAbonnement, 'id'> | null, o2: Pick<IContratAbonnement, 'id'> | null): boolean {
    return o1 && o2 ? this.getContratAbonnementIdentifier(o1) === this.getContratAbonnementIdentifier(o2) : o1 === o2;
  }

  addContratAbonnementToCollectionIfMissing<Type extends Pick<IContratAbonnement, 'id'>>(
    contratAbonnementCollection: Type[],
    ...contratAbonnementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const contratAbonnements: Type[] = contratAbonnementsToCheck.filter(isPresent);
    if (contratAbonnements.length > 0) {
      const contratAbonnementCollectionIdentifiers = contratAbonnementCollection.map(contratAbonnementItem =>
        this.getContratAbonnementIdentifier(contratAbonnementItem),
      );
      const contratAbonnementsToAdd = contratAbonnements.filter(contratAbonnementItem => {
        const contratAbonnementIdentifier = this.getContratAbonnementIdentifier(contratAbonnementItem);
        if (contratAbonnementCollectionIdentifiers.includes(contratAbonnementIdentifier)) {
          return false;
        }
        contratAbonnementCollectionIdentifiers.push(contratAbonnementIdentifier);
        return true;
      });
      return [...contratAbonnementsToAdd, ...contratAbonnementCollection];
    }
    return contratAbonnementCollection;
  }
}

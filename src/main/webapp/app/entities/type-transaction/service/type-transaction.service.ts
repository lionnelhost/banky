import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITypeTransaction, NewTypeTransaction } from '../type-transaction.model';

export type PartialUpdateTypeTransaction = Partial<ITypeTransaction> & Pick<ITypeTransaction, 'idTypeTransaction'>;

export type EntityResponseType = HttpResponse<ITypeTransaction>;
export type EntityArrayResponseType = HttpResponse<ITypeTransaction[]>;

@Injectable({ providedIn: 'root' })
export class TypeTransactionService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/type-transactions');

  create(typeTransaction: NewTypeTransaction): Observable<EntityResponseType> {
    return this.http.post<ITypeTransaction>(this.resourceUrl, typeTransaction, { observe: 'response' });
  }

  update(typeTransaction: ITypeTransaction): Observable<EntityResponseType> {
    return this.http.put<ITypeTransaction>(`${this.resourceUrl}/${this.getTypeTransactionIdentifier(typeTransaction)}`, typeTransaction, {
      observe: 'response',
    });
  }

  partialUpdate(typeTransaction: PartialUpdateTypeTransaction): Observable<EntityResponseType> {
    return this.http.patch<ITypeTransaction>(`${this.resourceUrl}/${this.getTypeTransactionIdentifier(typeTransaction)}`, typeTransaction, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ITypeTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeTransaction[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTypeTransactionIdentifier(typeTransaction: Pick<ITypeTransaction, 'idTypeTransaction'>): string {
    return typeTransaction.idTypeTransaction;
  }

  compareTypeTransaction(
    o1: Pick<ITypeTransaction, 'idTypeTransaction'> | null,
    o2: Pick<ITypeTransaction, 'idTypeTransaction'> | null,
  ): boolean {
    return o1 && o2 ? this.getTypeTransactionIdentifier(o1) === this.getTypeTransactionIdentifier(o2) : o1 === o2;
  }

  addTypeTransactionToCollectionIfMissing<Type extends Pick<ITypeTransaction, 'idTypeTransaction'>>(
    typeTransactionCollection: Type[],
    ...typeTransactionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const typeTransactions: Type[] = typeTransactionsToCheck.filter(isPresent);
    if (typeTransactions.length > 0) {
      const typeTransactionCollectionIdentifiers = typeTransactionCollection.map(typeTransactionItem =>
        this.getTypeTransactionIdentifier(typeTransactionItem),
      );
      const typeTransactionsToAdd = typeTransactions.filter(typeTransactionItem => {
        const typeTransactionIdentifier = this.getTypeTransactionIdentifier(typeTransactionItem);
        if (typeTransactionCollectionIdentifiers.includes(typeTransactionIdentifier)) {
          return false;
        }
        typeTransactionCollectionIdentifiers.push(typeTransactionIdentifier);
        return true;
      });
      return [...typeTransactionsToAdd, ...typeTransactionCollection];
    }
    return typeTransactionCollection;
  }
}

import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDispositifSignature, NewDispositifSignature } from '../dispositif-signature.model';

export type PartialUpdateDispositifSignature = Partial<IDispositifSignature> & Pick<IDispositifSignature, 'idDispositif'>;

export type EntityResponseType = HttpResponse<IDispositifSignature>;
export type EntityArrayResponseType = HttpResponse<IDispositifSignature[]>;

@Injectable({ providedIn: 'root' })
export class DispositifSignatureService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dispositif-signatures');

  create(dispositifSignature: NewDispositifSignature): Observable<EntityResponseType> {
    return this.http.post<IDispositifSignature>(this.resourceUrl, dispositifSignature, { observe: 'response' });
  }

  update(dispositifSignature: IDispositifSignature): Observable<EntityResponseType> {
    return this.http.put<IDispositifSignature>(
      `${this.resourceUrl}/${this.getDispositifSignatureIdentifier(dispositifSignature)}`,
      dispositifSignature,
      { observe: 'response' },
    );
  }

  partialUpdate(dispositifSignature: PartialUpdateDispositifSignature): Observable<EntityResponseType> {
    return this.http.patch<IDispositifSignature>(
      `${this.resourceUrl}/${this.getDispositifSignatureIdentifier(dispositifSignature)}`,
      dispositifSignature,
      { observe: 'response' },
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IDispositifSignature>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDispositifSignature[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDispositifSignatureIdentifier(dispositifSignature: Pick<IDispositifSignature, 'idDispositif'>): string {
    return dispositifSignature.idDispositif;
  }

  compareDispositifSignature(
    o1: Pick<IDispositifSignature, 'idDispositif'> | null,
    o2: Pick<IDispositifSignature, 'idDispositif'> | null,
  ): boolean {
    return o1 && o2 ? this.getDispositifSignatureIdentifier(o1) === this.getDispositifSignatureIdentifier(o2) : o1 === o2;
  }

  addDispositifSignatureToCollectionIfMissing<Type extends Pick<IDispositifSignature, 'idDispositif'>>(
    dispositifSignatureCollection: Type[],
    ...dispositifSignaturesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dispositifSignatures: Type[] = dispositifSignaturesToCheck.filter(isPresent);
    if (dispositifSignatures.length > 0) {
      const dispositifSignatureCollectionIdentifiers = dispositifSignatureCollection.map(dispositifSignatureItem =>
        this.getDispositifSignatureIdentifier(dispositifSignatureItem),
      );
      const dispositifSignaturesToAdd = dispositifSignatures.filter(dispositifSignatureItem => {
        const dispositifSignatureIdentifier = this.getDispositifSignatureIdentifier(dispositifSignatureItem);
        if (dispositifSignatureCollectionIdentifiers.includes(dispositifSignatureIdentifier)) {
          return false;
        }
        dispositifSignatureCollectionIdentifiers.push(dispositifSignatureIdentifier);
        return true;
      });
      return [...dispositifSignaturesToAdd, ...dispositifSignatureCollection];
    }
    return dispositifSignatureCollection;
  }
}

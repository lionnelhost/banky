import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IContratAbonnementCompte } from '../contrat-abonnement-compte.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../contrat-abonnement-compte.test-samples';

import { ContratAbonnementCompteService } from './contrat-abonnement-compte.service';

const requireRestSample: IContratAbonnementCompte = {
  ...sampleWithRequiredData,
};

describe('ContratAbonnementCompte Service', () => {
  let service: ContratAbonnementCompteService;
  let httpMock: HttpTestingController;
  let expectedResult: IContratAbonnementCompte | IContratAbonnementCompte[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ContratAbonnementCompteService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ContratAbonnementCompte', () => {
      const contratAbonnementCompte = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(contratAbonnementCompte).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ContratAbonnementCompte', () => {
      const contratAbonnementCompte = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(contratAbonnementCompte).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ContratAbonnementCompte', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ContratAbonnementCompte', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ContratAbonnementCompte', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContratAbonnementCompteToCollectionIfMissing', () => {
      it('should add a ContratAbonnementCompte to an empty array', () => {
        const contratAbonnementCompte: IContratAbonnementCompte = sampleWithRequiredData;
        expectedResult = service.addContratAbonnementCompteToCollectionIfMissing([], contratAbonnementCompte);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contratAbonnementCompte);
      });

      it('should not add a ContratAbonnementCompte to an array that contains it', () => {
        const contratAbonnementCompte: IContratAbonnementCompte = sampleWithRequiredData;
        const contratAbonnementCompteCollection: IContratAbonnementCompte[] = [
          {
            ...contratAbonnementCompte,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContratAbonnementCompteToCollectionIfMissing(
          contratAbonnementCompteCollection,
          contratAbonnementCompte,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ContratAbonnementCompte to an array that doesn't contain it", () => {
        const contratAbonnementCompte: IContratAbonnementCompte = sampleWithRequiredData;
        const contratAbonnementCompteCollection: IContratAbonnementCompte[] = [sampleWithPartialData];
        expectedResult = service.addContratAbonnementCompteToCollectionIfMissing(
          contratAbonnementCompteCollection,
          contratAbonnementCompte,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contratAbonnementCompte);
      });

      it('should add only unique ContratAbonnementCompte to an array', () => {
        const contratAbonnementCompteArray: IContratAbonnementCompte[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const contratAbonnementCompteCollection: IContratAbonnementCompte[] = [sampleWithRequiredData];
        expectedResult = service.addContratAbonnementCompteToCollectionIfMissing(
          contratAbonnementCompteCollection,
          ...contratAbonnementCompteArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contratAbonnementCompte: IContratAbonnementCompte = sampleWithRequiredData;
        const contratAbonnementCompte2: IContratAbonnementCompte = sampleWithPartialData;
        expectedResult = service.addContratAbonnementCompteToCollectionIfMissing([], contratAbonnementCompte, contratAbonnementCompte2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contratAbonnementCompte);
        expect(expectedResult).toContain(contratAbonnementCompte2);
      });

      it('should accept null and undefined values', () => {
        const contratAbonnementCompte: IContratAbonnementCompte = sampleWithRequiredData;
        expectedResult = service.addContratAbonnementCompteToCollectionIfMissing([], null, contratAbonnementCompte, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contratAbonnementCompte);
      });

      it('should return initial array if no ContratAbonnementCompte is added', () => {
        const contratAbonnementCompteCollection: IContratAbonnementCompte[] = [sampleWithRequiredData];
        expectedResult = service.addContratAbonnementCompteToCollectionIfMissing(contratAbonnementCompteCollection, undefined, null);
        expect(expectedResult).toEqual(contratAbonnementCompteCollection);
      });
    });

    describe('compareContratAbonnementCompte', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContratAbonnementCompte(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareContratAbonnementCompte(entity1, entity2);
        const compareResult2 = service.compareContratAbonnementCompte(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareContratAbonnementCompte(entity1, entity2);
        const compareResult2 = service.compareContratAbonnementCompte(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareContratAbonnementCompte(entity1, entity2);
        const compareResult2 = service.compareContratAbonnementCompte(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

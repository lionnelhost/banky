import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ITypeTransaction } from '../type-transaction.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../type-transaction.test-samples';

import { TypeTransactionService } from './type-transaction.service';

const requireRestSample: ITypeTransaction = {
  ...sampleWithRequiredData,
};

describe('TypeTransaction Service', () => {
  let service: TypeTransactionService;
  let httpMock: HttpTestingController;
  let expectedResult: ITypeTransaction | ITypeTransaction[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TypeTransactionService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a TypeTransaction', () => {
      const typeTransaction = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(typeTransaction).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TypeTransaction', () => {
      const typeTransaction = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(typeTransaction).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TypeTransaction', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TypeTransaction', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TypeTransaction', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTypeTransactionToCollectionIfMissing', () => {
      it('should add a TypeTransaction to an empty array', () => {
        const typeTransaction: ITypeTransaction = sampleWithRequiredData;
        expectedResult = service.addTypeTransactionToCollectionIfMissing([], typeTransaction);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typeTransaction);
      });

      it('should not add a TypeTransaction to an array that contains it', () => {
        const typeTransaction: ITypeTransaction = sampleWithRequiredData;
        const typeTransactionCollection: ITypeTransaction[] = [
          {
            ...typeTransaction,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTypeTransactionToCollectionIfMissing(typeTransactionCollection, typeTransaction);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TypeTransaction to an array that doesn't contain it", () => {
        const typeTransaction: ITypeTransaction = sampleWithRequiredData;
        const typeTransactionCollection: ITypeTransaction[] = [sampleWithPartialData];
        expectedResult = service.addTypeTransactionToCollectionIfMissing(typeTransactionCollection, typeTransaction);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typeTransaction);
      });

      it('should add only unique TypeTransaction to an array', () => {
        const typeTransactionArray: ITypeTransaction[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const typeTransactionCollection: ITypeTransaction[] = [sampleWithRequiredData];
        expectedResult = service.addTypeTransactionToCollectionIfMissing(typeTransactionCollection, ...typeTransactionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const typeTransaction: ITypeTransaction = sampleWithRequiredData;
        const typeTransaction2: ITypeTransaction = sampleWithPartialData;
        expectedResult = service.addTypeTransactionToCollectionIfMissing([], typeTransaction, typeTransaction2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typeTransaction);
        expect(expectedResult).toContain(typeTransaction2);
      });

      it('should accept null and undefined values', () => {
        const typeTransaction: ITypeTransaction = sampleWithRequiredData;
        expectedResult = service.addTypeTransactionToCollectionIfMissing([], null, typeTransaction, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typeTransaction);
      });

      it('should return initial array if no TypeTransaction is added', () => {
        const typeTransactionCollection: ITypeTransaction[] = [sampleWithRequiredData];
        expectedResult = service.addTypeTransactionToCollectionIfMissing(typeTransactionCollection, undefined, null);
        expect(expectedResult).toEqual(typeTransactionCollection);
      });
    });

    describe('compareTypeTransaction', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTypeTransaction(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { idTypeTransaction: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareTypeTransaction(entity1, entity2);
        const compareResult2 = service.compareTypeTransaction(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { idTypeTransaction: 'ABC' };
        const entity2 = { idTypeTransaction: 'CBA' };

        const compareResult1 = service.compareTypeTransaction(entity1, entity2);
        const compareResult2 = service.compareTypeTransaction(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { idTypeTransaction: 'ABC' };
        const entity2 = { idTypeTransaction: 'ABC' };

        const compareResult1 = service.compareTypeTransaction(entity1, entity2);
        const compareResult2 = service.compareTypeTransaction(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

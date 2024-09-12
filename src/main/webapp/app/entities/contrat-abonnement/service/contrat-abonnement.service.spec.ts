import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IContratAbonnement } from '../contrat-abonnement.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../contrat-abonnement.test-samples';

import { ContratAbonnementService } from './contrat-abonnement.service';

const requireRestSample: IContratAbonnement = {
  ...sampleWithRequiredData,
};

describe('ContratAbonnement Service', () => {
  let service: ContratAbonnementService;
  let httpMock: HttpTestingController;
  let expectedResult: IContratAbonnement | IContratAbonnement[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ContratAbonnementService);
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

    it('should create a ContratAbonnement', () => {
      const contratAbonnement = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(contratAbonnement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ContratAbonnement', () => {
      const contratAbonnement = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(contratAbonnement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ContratAbonnement', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ContratAbonnement', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ContratAbonnement', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContratAbonnementToCollectionIfMissing', () => {
      it('should add a ContratAbonnement to an empty array', () => {
        const contratAbonnement: IContratAbonnement = sampleWithRequiredData;
        expectedResult = service.addContratAbonnementToCollectionIfMissing([], contratAbonnement);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contratAbonnement);
      });

      it('should not add a ContratAbonnement to an array that contains it', () => {
        const contratAbonnement: IContratAbonnement = sampleWithRequiredData;
        const contratAbonnementCollection: IContratAbonnement[] = [
          {
            ...contratAbonnement,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContratAbonnementToCollectionIfMissing(contratAbonnementCollection, contratAbonnement);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ContratAbonnement to an array that doesn't contain it", () => {
        const contratAbonnement: IContratAbonnement = sampleWithRequiredData;
        const contratAbonnementCollection: IContratAbonnement[] = [sampleWithPartialData];
        expectedResult = service.addContratAbonnementToCollectionIfMissing(contratAbonnementCollection, contratAbonnement);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contratAbonnement);
      });

      it('should add only unique ContratAbonnement to an array', () => {
        const contratAbonnementArray: IContratAbonnement[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const contratAbonnementCollection: IContratAbonnement[] = [sampleWithRequiredData];
        expectedResult = service.addContratAbonnementToCollectionIfMissing(contratAbonnementCollection, ...contratAbonnementArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contratAbonnement: IContratAbonnement = sampleWithRequiredData;
        const contratAbonnement2: IContratAbonnement = sampleWithPartialData;
        expectedResult = service.addContratAbonnementToCollectionIfMissing([], contratAbonnement, contratAbonnement2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contratAbonnement);
        expect(expectedResult).toContain(contratAbonnement2);
      });

      it('should accept null and undefined values', () => {
        const contratAbonnement: IContratAbonnement = sampleWithRequiredData;
        expectedResult = service.addContratAbonnementToCollectionIfMissing([], null, contratAbonnement, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contratAbonnement);
      });

      it('should return initial array if no ContratAbonnement is added', () => {
        const contratAbonnementCollection: IContratAbonnement[] = [sampleWithRequiredData];
        expectedResult = service.addContratAbonnementToCollectionIfMissing(contratAbonnementCollection, undefined, null);
        expect(expectedResult).toEqual(contratAbonnementCollection);
      });
    });

    describe('compareContratAbonnement', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContratAbonnement(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareContratAbonnement(entity1, entity2);
        const compareResult2 = service.compareContratAbonnement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareContratAbonnement(entity1, entity2);
        const compareResult2 = service.compareContratAbonnement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareContratAbonnement(entity1, entity2);
        const compareResult2 = service.compareContratAbonnement(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

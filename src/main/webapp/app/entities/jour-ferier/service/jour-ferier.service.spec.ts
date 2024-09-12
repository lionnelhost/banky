import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IJourFerier } from '../jour-ferier.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../jour-ferier.test-samples';

import { JourFerierService } from './jour-ferier.service';

const requireRestSample: IJourFerier = {
  ...sampleWithRequiredData,
};

describe('JourFerier Service', () => {
  let service: JourFerierService;
  let httpMock: HttpTestingController;
  let expectedResult: IJourFerier | IJourFerier[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(JourFerierService);
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

    it('should create a JourFerier', () => {
      const jourFerier = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(jourFerier).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a JourFerier', () => {
      const jourFerier = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(jourFerier).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a JourFerier', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of JourFerier', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a JourFerier', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addJourFerierToCollectionIfMissing', () => {
      it('should add a JourFerier to an empty array', () => {
        const jourFerier: IJourFerier = sampleWithRequiredData;
        expectedResult = service.addJourFerierToCollectionIfMissing([], jourFerier);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(jourFerier);
      });

      it('should not add a JourFerier to an array that contains it', () => {
        const jourFerier: IJourFerier = sampleWithRequiredData;
        const jourFerierCollection: IJourFerier[] = [
          {
            ...jourFerier,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addJourFerierToCollectionIfMissing(jourFerierCollection, jourFerier);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a JourFerier to an array that doesn't contain it", () => {
        const jourFerier: IJourFerier = sampleWithRequiredData;
        const jourFerierCollection: IJourFerier[] = [sampleWithPartialData];
        expectedResult = service.addJourFerierToCollectionIfMissing(jourFerierCollection, jourFerier);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(jourFerier);
      });

      it('should add only unique JourFerier to an array', () => {
        const jourFerierArray: IJourFerier[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const jourFerierCollection: IJourFerier[] = [sampleWithRequiredData];
        expectedResult = service.addJourFerierToCollectionIfMissing(jourFerierCollection, ...jourFerierArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const jourFerier: IJourFerier = sampleWithRequiredData;
        const jourFerier2: IJourFerier = sampleWithPartialData;
        expectedResult = service.addJourFerierToCollectionIfMissing([], jourFerier, jourFerier2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(jourFerier);
        expect(expectedResult).toContain(jourFerier2);
      });

      it('should accept null and undefined values', () => {
        const jourFerier: IJourFerier = sampleWithRequiredData;
        expectedResult = service.addJourFerierToCollectionIfMissing([], null, jourFerier, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(jourFerier);
      });

      it('should return initial array if no JourFerier is added', () => {
        const jourFerierCollection: IJourFerier[] = [sampleWithRequiredData];
        expectedResult = service.addJourFerierToCollectionIfMissing(jourFerierCollection, undefined, null);
        expect(expectedResult).toEqual(jourFerierCollection);
      });
    });

    describe('compareJourFerier', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareJourFerier(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { idJourFerie: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareJourFerier(entity1, entity2);
        const compareResult2 = service.compareJourFerier(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { idJourFerie: 'ABC' };
        const entity2 = { idJourFerie: 'CBA' };

        const compareResult1 = service.compareJourFerier(entity1, entity2);
        const compareResult2 = service.compareJourFerier(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { idJourFerie: 'ABC' };
        const entity2 = { idJourFerie: 'ABC' };

        const compareResult1 = service.compareJourFerier(entity1, entity2);
        const compareResult2 = service.compareJourFerier(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

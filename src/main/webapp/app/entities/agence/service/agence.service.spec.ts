import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAgence } from '../agence.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../agence.test-samples';

import { AgenceService } from './agence.service';

const requireRestSample: IAgence = {
  ...sampleWithRequiredData,
};

describe('Agence Service', () => {
  let service: AgenceService;
  let httpMock: HttpTestingController;
  let expectedResult: IAgence | IAgence[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AgenceService);
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

    it('should create a Agence', () => {
      const agence = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(agence).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Agence', () => {
      const agence = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(agence).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Agence', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Agence', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Agence', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAgenceToCollectionIfMissing', () => {
      it('should add a Agence to an empty array', () => {
        const agence: IAgence = sampleWithRequiredData;
        expectedResult = service.addAgenceToCollectionIfMissing([], agence);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agence);
      });

      it('should not add a Agence to an array that contains it', () => {
        const agence: IAgence = sampleWithRequiredData;
        const agenceCollection: IAgence[] = [
          {
            ...agence,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAgenceToCollectionIfMissing(agenceCollection, agence);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Agence to an array that doesn't contain it", () => {
        const agence: IAgence = sampleWithRequiredData;
        const agenceCollection: IAgence[] = [sampleWithPartialData];
        expectedResult = service.addAgenceToCollectionIfMissing(agenceCollection, agence);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agence);
      });

      it('should add only unique Agence to an array', () => {
        const agenceArray: IAgence[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const agenceCollection: IAgence[] = [sampleWithRequiredData];
        expectedResult = service.addAgenceToCollectionIfMissing(agenceCollection, ...agenceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const agence: IAgence = sampleWithRequiredData;
        const agence2: IAgence = sampleWithPartialData;
        expectedResult = service.addAgenceToCollectionIfMissing([], agence, agence2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agence);
        expect(expectedResult).toContain(agence2);
      });

      it('should accept null and undefined values', () => {
        const agence: IAgence = sampleWithRequiredData;
        expectedResult = service.addAgenceToCollectionIfMissing([], null, agence, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agence);
      });

      it('should return initial array if no Agence is added', () => {
        const agenceCollection: IAgence[] = [sampleWithRequiredData];
        expectedResult = service.addAgenceToCollectionIfMissing(agenceCollection, undefined, null);
        expect(expectedResult).toEqual(agenceCollection);
      });
    });

    describe('compareAgence', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAgence(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { idAgence: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareAgence(entity1, entity2);
        const compareResult2 = service.compareAgence(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { idAgence: 'ABC' };
        const entity2 = { idAgence: 'CBA' };

        const compareResult1 = service.compareAgence(entity1, entity2);
        const compareResult2 = service.compareAgence(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { idAgence: 'ABC' };
        const entity2 = { idAgence: 'ABC' };

        const compareResult1 = service.compareAgence(entity1, entity2);
        const compareResult2 = service.compareAgence(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IParametrageGlobal } from '../parametrage-global.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../parametrage-global.test-samples';

import { ParametrageGlobalService } from './parametrage-global.service';

const requireRestSample: IParametrageGlobal = {
  ...sampleWithRequiredData,
};

describe('ParametrageGlobal Service', () => {
  let service: ParametrageGlobalService;
  let httpMock: HttpTestingController;
  let expectedResult: IParametrageGlobal | IParametrageGlobal[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ParametrageGlobalService);
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

    it('should create a ParametrageGlobal', () => {
      const parametrageGlobal = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(parametrageGlobal).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ParametrageGlobal', () => {
      const parametrageGlobal = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(parametrageGlobal).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ParametrageGlobal', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ParametrageGlobal', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ParametrageGlobal', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addParametrageGlobalToCollectionIfMissing', () => {
      it('should add a ParametrageGlobal to an empty array', () => {
        const parametrageGlobal: IParametrageGlobal = sampleWithRequiredData;
        expectedResult = service.addParametrageGlobalToCollectionIfMissing([], parametrageGlobal);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(parametrageGlobal);
      });

      it('should not add a ParametrageGlobal to an array that contains it', () => {
        const parametrageGlobal: IParametrageGlobal = sampleWithRequiredData;
        const parametrageGlobalCollection: IParametrageGlobal[] = [
          {
            ...parametrageGlobal,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addParametrageGlobalToCollectionIfMissing(parametrageGlobalCollection, parametrageGlobal);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ParametrageGlobal to an array that doesn't contain it", () => {
        const parametrageGlobal: IParametrageGlobal = sampleWithRequiredData;
        const parametrageGlobalCollection: IParametrageGlobal[] = [sampleWithPartialData];
        expectedResult = service.addParametrageGlobalToCollectionIfMissing(parametrageGlobalCollection, parametrageGlobal);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(parametrageGlobal);
      });

      it('should add only unique ParametrageGlobal to an array', () => {
        const parametrageGlobalArray: IParametrageGlobal[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const parametrageGlobalCollection: IParametrageGlobal[] = [sampleWithRequiredData];
        expectedResult = service.addParametrageGlobalToCollectionIfMissing(parametrageGlobalCollection, ...parametrageGlobalArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const parametrageGlobal: IParametrageGlobal = sampleWithRequiredData;
        const parametrageGlobal2: IParametrageGlobal = sampleWithPartialData;
        expectedResult = service.addParametrageGlobalToCollectionIfMissing([], parametrageGlobal, parametrageGlobal2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(parametrageGlobal);
        expect(expectedResult).toContain(parametrageGlobal2);
      });

      it('should accept null and undefined values', () => {
        const parametrageGlobal: IParametrageGlobal = sampleWithRequiredData;
        expectedResult = service.addParametrageGlobalToCollectionIfMissing([], null, parametrageGlobal, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(parametrageGlobal);
      });

      it('should return initial array if no ParametrageGlobal is added', () => {
        const parametrageGlobalCollection: IParametrageGlobal[] = [sampleWithRequiredData];
        expectedResult = service.addParametrageGlobalToCollectionIfMissing(parametrageGlobalCollection, undefined, null);
        expect(expectedResult).toEqual(parametrageGlobalCollection);
      });
    });

    describe('compareParametrageGlobal', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareParametrageGlobal(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { idParamGlobal: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareParametrageGlobal(entity1, entity2);
        const compareResult2 = service.compareParametrageGlobal(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { idParamGlobal: 'ABC' };
        const entity2 = { idParamGlobal: 'CBA' };

        const compareResult1 = service.compareParametrageGlobal(entity1, entity2);
        const compareResult2 = service.compareParametrageGlobal(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { idParamGlobal: 'ABC' };
        const entity2 = { idParamGlobal: 'ABC' };

        const compareResult1 = service.compareParametrageGlobal(entity1, entity2);
        const compareResult2 = service.compareParametrageGlobal(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

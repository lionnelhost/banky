import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IParametrageNotification } from '../parametrage-notification.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../parametrage-notification.test-samples';

import { ParametrageNotificationService } from './parametrage-notification.service';

const requireRestSample: IParametrageNotification = {
  ...sampleWithRequiredData,
};

describe('ParametrageNotification Service', () => {
  let service: ParametrageNotificationService;
  let httpMock: HttpTestingController;
  let expectedResult: IParametrageNotification | IParametrageNotification[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ParametrageNotificationService);
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

    it('should create a ParametrageNotification', () => {
      const parametrageNotification = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(parametrageNotification).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ParametrageNotification', () => {
      const parametrageNotification = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(parametrageNotification).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ParametrageNotification', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ParametrageNotification', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ParametrageNotification', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addParametrageNotificationToCollectionIfMissing', () => {
      it('should add a ParametrageNotification to an empty array', () => {
        const parametrageNotification: IParametrageNotification = sampleWithRequiredData;
        expectedResult = service.addParametrageNotificationToCollectionIfMissing([], parametrageNotification);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(parametrageNotification);
      });

      it('should not add a ParametrageNotification to an array that contains it', () => {
        const parametrageNotification: IParametrageNotification = sampleWithRequiredData;
        const parametrageNotificationCollection: IParametrageNotification[] = [
          {
            ...parametrageNotification,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addParametrageNotificationToCollectionIfMissing(
          parametrageNotificationCollection,
          parametrageNotification,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ParametrageNotification to an array that doesn't contain it", () => {
        const parametrageNotification: IParametrageNotification = sampleWithRequiredData;
        const parametrageNotificationCollection: IParametrageNotification[] = [sampleWithPartialData];
        expectedResult = service.addParametrageNotificationToCollectionIfMissing(
          parametrageNotificationCollection,
          parametrageNotification,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(parametrageNotification);
      });

      it('should add only unique ParametrageNotification to an array', () => {
        const parametrageNotificationArray: IParametrageNotification[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const parametrageNotificationCollection: IParametrageNotification[] = [sampleWithRequiredData];
        expectedResult = service.addParametrageNotificationToCollectionIfMissing(
          parametrageNotificationCollection,
          ...parametrageNotificationArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const parametrageNotification: IParametrageNotification = sampleWithRequiredData;
        const parametrageNotification2: IParametrageNotification = sampleWithPartialData;
        expectedResult = service.addParametrageNotificationToCollectionIfMissing([], parametrageNotification, parametrageNotification2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(parametrageNotification);
        expect(expectedResult).toContain(parametrageNotification2);
      });

      it('should accept null and undefined values', () => {
        const parametrageNotification: IParametrageNotification = sampleWithRequiredData;
        expectedResult = service.addParametrageNotificationToCollectionIfMissing([], null, parametrageNotification, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(parametrageNotification);
      });

      it('should return initial array if no ParametrageNotification is added', () => {
        const parametrageNotificationCollection: IParametrageNotification[] = [sampleWithRequiredData];
        expectedResult = service.addParametrageNotificationToCollectionIfMissing(parametrageNotificationCollection, undefined, null);
        expect(expectedResult).toEqual(parametrageNotificationCollection);
      });
    });

    describe('compareParametrageNotification', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareParametrageNotification(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { idParamNotif: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareParametrageNotification(entity1, entity2);
        const compareResult2 = service.compareParametrageNotification(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { idParamNotif: 'ABC' };
        const entity2 = { idParamNotif: 'CBA' };

        const compareResult1 = service.compareParametrageNotification(entity1, entity2);
        const compareResult2 = service.compareParametrageNotification(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { idParamNotif: 'ABC' };
        const entity2 = { idParamNotif: 'ABC' };

        const compareResult1 = service.compareParametrageNotification(entity1, entity2);
        const compareResult2 = service.compareParametrageNotification(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

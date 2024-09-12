import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IVariableNotification } from '../variable-notification.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../variable-notification.test-samples';

import { VariableNotificationService } from './variable-notification.service';

const requireRestSample: IVariableNotification = {
  ...sampleWithRequiredData,
};

describe('VariableNotification Service', () => {
  let service: VariableNotificationService;
  let httpMock: HttpTestingController;
  let expectedResult: IVariableNotification | IVariableNotification[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(VariableNotificationService);
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

    it('should create a VariableNotification', () => {
      const variableNotification = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(variableNotification).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a VariableNotification', () => {
      const variableNotification = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(variableNotification).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a VariableNotification', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of VariableNotification', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a VariableNotification', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addVariableNotificationToCollectionIfMissing', () => {
      it('should add a VariableNotification to an empty array', () => {
        const variableNotification: IVariableNotification = sampleWithRequiredData;
        expectedResult = service.addVariableNotificationToCollectionIfMissing([], variableNotification);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(variableNotification);
      });

      it('should not add a VariableNotification to an array that contains it', () => {
        const variableNotification: IVariableNotification = sampleWithRequiredData;
        const variableNotificationCollection: IVariableNotification[] = [
          {
            ...variableNotification,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addVariableNotificationToCollectionIfMissing(variableNotificationCollection, variableNotification);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a VariableNotification to an array that doesn't contain it", () => {
        const variableNotification: IVariableNotification = sampleWithRequiredData;
        const variableNotificationCollection: IVariableNotification[] = [sampleWithPartialData];
        expectedResult = service.addVariableNotificationToCollectionIfMissing(variableNotificationCollection, variableNotification);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(variableNotification);
      });

      it('should add only unique VariableNotification to an array', () => {
        const variableNotificationArray: IVariableNotification[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const variableNotificationCollection: IVariableNotification[] = [sampleWithRequiredData];
        expectedResult = service.addVariableNotificationToCollectionIfMissing(variableNotificationCollection, ...variableNotificationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const variableNotification: IVariableNotification = sampleWithRequiredData;
        const variableNotification2: IVariableNotification = sampleWithPartialData;
        expectedResult = service.addVariableNotificationToCollectionIfMissing([], variableNotification, variableNotification2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(variableNotification);
        expect(expectedResult).toContain(variableNotification2);
      });

      it('should accept null and undefined values', () => {
        const variableNotification: IVariableNotification = sampleWithRequiredData;
        expectedResult = service.addVariableNotificationToCollectionIfMissing([], null, variableNotification, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(variableNotification);
      });

      it('should return initial array if no VariableNotification is added', () => {
        const variableNotificationCollection: IVariableNotification[] = [sampleWithRequiredData];
        expectedResult = service.addVariableNotificationToCollectionIfMissing(variableNotificationCollection, undefined, null);
        expect(expectedResult).toEqual(variableNotificationCollection);
      });
    });

    describe('compareVariableNotification', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareVariableNotification(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { idVarNotification: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareVariableNotification(entity1, entity2);
        const compareResult2 = service.compareVariableNotification(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { idVarNotification: 'ABC' };
        const entity2 = { idVarNotification: 'CBA' };

        const compareResult1 = service.compareVariableNotification(entity1, entity2);
        const compareResult2 = service.compareVariableNotification(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { idVarNotification: 'ABC' };
        const entity2 = { idVarNotification: 'ABC' };

        const compareResult1 = service.compareVariableNotification(entity1, entity2);
        const compareResult2 = service.compareVariableNotification(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

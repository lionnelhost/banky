import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDispositifSignature } from '../dispositif-signature.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../dispositif-signature.test-samples';

import { DispositifSignatureService } from './dispositif-signature.service';

const requireRestSample: IDispositifSignature = {
  ...sampleWithRequiredData,
};

describe('DispositifSignature Service', () => {
  let service: DispositifSignatureService;
  let httpMock: HttpTestingController;
  let expectedResult: IDispositifSignature | IDispositifSignature[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DispositifSignatureService);
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

    it('should create a DispositifSignature', () => {
      const dispositifSignature = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(dispositifSignature).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DispositifSignature', () => {
      const dispositifSignature = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(dispositifSignature).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DispositifSignature', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DispositifSignature', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DispositifSignature', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDispositifSignatureToCollectionIfMissing', () => {
      it('should add a DispositifSignature to an empty array', () => {
        const dispositifSignature: IDispositifSignature = sampleWithRequiredData;
        expectedResult = service.addDispositifSignatureToCollectionIfMissing([], dispositifSignature);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dispositifSignature);
      });

      it('should not add a DispositifSignature to an array that contains it', () => {
        const dispositifSignature: IDispositifSignature = sampleWithRequiredData;
        const dispositifSignatureCollection: IDispositifSignature[] = [
          {
            ...dispositifSignature,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDispositifSignatureToCollectionIfMissing(dispositifSignatureCollection, dispositifSignature);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DispositifSignature to an array that doesn't contain it", () => {
        const dispositifSignature: IDispositifSignature = sampleWithRequiredData;
        const dispositifSignatureCollection: IDispositifSignature[] = [sampleWithPartialData];
        expectedResult = service.addDispositifSignatureToCollectionIfMissing(dispositifSignatureCollection, dispositifSignature);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dispositifSignature);
      });

      it('should add only unique DispositifSignature to an array', () => {
        const dispositifSignatureArray: IDispositifSignature[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dispositifSignatureCollection: IDispositifSignature[] = [sampleWithRequiredData];
        expectedResult = service.addDispositifSignatureToCollectionIfMissing(dispositifSignatureCollection, ...dispositifSignatureArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dispositifSignature: IDispositifSignature = sampleWithRequiredData;
        const dispositifSignature2: IDispositifSignature = sampleWithPartialData;
        expectedResult = service.addDispositifSignatureToCollectionIfMissing([], dispositifSignature, dispositifSignature2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dispositifSignature);
        expect(expectedResult).toContain(dispositifSignature2);
      });

      it('should accept null and undefined values', () => {
        const dispositifSignature: IDispositifSignature = sampleWithRequiredData;
        expectedResult = service.addDispositifSignatureToCollectionIfMissing([], null, dispositifSignature, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dispositifSignature);
      });

      it('should return initial array if no DispositifSignature is added', () => {
        const dispositifSignatureCollection: IDispositifSignature[] = [sampleWithRequiredData];
        expectedResult = service.addDispositifSignatureToCollectionIfMissing(dispositifSignatureCollection, undefined, null);
        expect(expectedResult).toEqual(dispositifSignatureCollection);
      });
    });

    describe('compareDispositifSignature', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDispositifSignature(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { idDispositif: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareDispositifSignature(entity1, entity2);
        const compareResult2 = service.compareDispositifSignature(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { idDispositif: 'ABC' };
        const entity2 = { idDispositif: 'CBA' };

        const compareResult1 = service.compareDispositifSignature(entity1, entity2);
        const compareResult2 = service.compareDispositifSignature(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { idDispositif: 'ABC' };
        const entity2 = { idDispositif: 'ABC' };

        const compareResult1 = service.compareDispositifSignature(entity1, entity2);
        const compareResult2 = service.compareDispositifSignature(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDispositifSercurite } from '../dispositif-sercurite.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../dispositif-sercurite.test-samples';

import { DispositifSercuriteService } from './dispositif-sercurite.service';

const requireRestSample: IDispositifSercurite = {
  ...sampleWithRequiredData,
};

describe('DispositifSercurite Service', () => {
  let service: DispositifSercuriteService;
  let httpMock: HttpTestingController;
  let expectedResult: IDispositifSercurite | IDispositifSercurite[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DispositifSercuriteService);
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

    it('should create a DispositifSercurite', () => {
      const dispositifSercurite = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(dispositifSercurite).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DispositifSercurite', () => {
      const dispositifSercurite = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(dispositifSercurite).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DispositifSercurite', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DispositifSercurite', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DispositifSercurite', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDispositifSercuriteToCollectionIfMissing', () => {
      it('should add a DispositifSercurite to an empty array', () => {
        const dispositifSercurite: IDispositifSercurite = sampleWithRequiredData;
        expectedResult = service.addDispositifSercuriteToCollectionIfMissing([], dispositifSercurite);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dispositifSercurite);
      });

      it('should not add a DispositifSercurite to an array that contains it', () => {
        const dispositifSercurite: IDispositifSercurite = sampleWithRequiredData;
        const dispositifSercuriteCollection: IDispositifSercurite[] = [
          {
            ...dispositifSercurite,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDispositifSercuriteToCollectionIfMissing(dispositifSercuriteCollection, dispositifSercurite);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DispositifSercurite to an array that doesn't contain it", () => {
        const dispositifSercurite: IDispositifSercurite = sampleWithRequiredData;
        const dispositifSercuriteCollection: IDispositifSercurite[] = [sampleWithPartialData];
        expectedResult = service.addDispositifSercuriteToCollectionIfMissing(dispositifSercuriteCollection, dispositifSercurite);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dispositifSercurite);
      });

      it('should add only unique DispositifSercurite to an array', () => {
        const dispositifSercuriteArray: IDispositifSercurite[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dispositifSercuriteCollection: IDispositifSercurite[] = [sampleWithRequiredData];
        expectedResult = service.addDispositifSercuriteToCollectionIfMissing(dispositifSercuriteCollection, ...dispositifSercuriteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dispositifSercurite: IDispositifSercurite = sampleWithRequiredData;
        const dispositifSercurite2: IDispositifSercurite = sampleWithPartialData;
        expectedResult = service.addDispositifSercuriteToCollectionIfMissing([], dispositifSercurite, dispositifSercurite2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dispositifSercurite);
        expect(expectedResult).toContain(dispositifSercurite2);
      });

      it('should accept null and undefined values', () => {
        const dispositifSercurite: IDispositifSercurite = sampleWithRequiredData;
        expectedResult = service.addDispositifSercuriteToCollectionIfMissing([], null, dispositifSercurite, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dispositifSercurite);
      });

      it('should return initial array if no DispositifSercurite is added', () => {
        const dispositifSercuriteCollection: IDispositifSercurite[] = [sampleWithRequiredData];
        expectedResult = service.addDispositifSercuriteToCollectionIfMissing(dispositifSercuriteCollection, undefined, null);
        expect(expectedResult).toEqual(dispositifSercuriteCollection);
      });
    });

    describe('compareDispositifSercurite', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDispositifSercurite(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDispositifSercurite(entity1, entity2);
        const compareResult2 = service.compareDispositifSercurite(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDispositifSercurite(entity1, entity2);
        const compareResult2 = service.compareDispositifSercurite(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDispositifSercurite(entity1, entity2);
        const compareResult2 = service.compareDispositifSercurite(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IBanque } from '../banque.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../banque.test-samples';

import { BanqueService } from './banque.service';

const requireRestSample: IBanque = {
  ...sampleWithRequiredData,
};

describe('Banque Service', () => {
  let service: BanqueService;
  let httpMock: HttpTestingController;
  let expectedResult: IBanque | IBanque[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(BanqueService);
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

    it('should create a Banque', () => {
      const banque = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(banque).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Banque', () => {
      const banque = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(banque).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Banque', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Banque', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Banque', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBanqueToCollectionIfMissing', () => {
      it('should add a Banque to an empty array', () => {
        const banque: IBanque = sampleWithRequiredData;
        expectedResult = service.addBanqueToCollectionIfMissing([], banque);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(banque);
      });

      it('should not add a Banque to an array that contains it', () => {
        const banque: IBanque = sampleWithRequiredData;
        const banqueCollection: IBanque[] = [
          {
            ...banque,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBanqueToCollectionIfMissing(banqueCollection, banque);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Banque to an array that doesn't contain it", () => {
        const banque: IBanque = sampleWithRequiredData;
        const banqueCollection: IBanque[] = [sampleWithPartialData];
        expectedResult = service.addBanqueToCollectionIfMissing(banqueCollection, banque);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(banque);
      });

      it('should add only unique Banque to an array', () => {
        const banqueArray: IBanque[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const banqueCollection: IBanque[] = [sampleWithRequiredData];
        expectedResult = service.addBanqueToCollectionIfMissing(banqueCollection, ...banqueArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const banque: IBanque = sampleWithRequiredData;
        const banque2: IBanque = sampleWithPartialData;
        expectedResult = service.addBanqueToCollectionIfMissing([], banque, banque2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(banque);
        expect(expectedResult).toContain(banque2);
      });

      it('should accept null and undefined values', () => {
        const banque: IBanque = sampleWithRequiredData;
        expectedResult = service.addBanqueToCollectionIfMissing([], null, banque, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(banque);
      });

      it('should return initial array if no Banque is added', () => {
        const banqueCollection: IBanque[] = [sampleWithRequiredData];
        expectedResult = service.addBanqueToCollectionIfMissing(banqueCollection, undefined, null);
        expect(expectedResult).toEqual(banqueCollection);
      });
    });

    describe('compareBanque', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBanque(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { idBanque: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareBanque(entity1, entity2);
        const compareResult2 = service.compareBanque(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { idBanque: 'ABC' };
        const entity2 = { idBanque: 'CBA' };

        const compareResult1 = service.compareBanque(entity1, entity2);
        const compareResult2 = service.compareBanque(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { idBanque: 'ABC' };
        const entity2 = { idBanque: 'ABC' };

        const compareResult1 = service.compareBanque(entity1, entity2);
        const compareResult2 = service.compareBanque(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

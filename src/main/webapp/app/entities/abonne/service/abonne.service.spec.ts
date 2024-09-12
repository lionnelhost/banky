import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAbonne } from '../abonne.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../abonne.test-samples';

import { AbonneService } from './abonne.service';

const requireRestSample: IAbonne = {
  ...sampleWithRequiredData,
};

describe('Abonne Service', () => {
  let service: AbonneService;
  let httpMock: HttpTestingController;
  let expectedResult: IAbonne | IAbonne[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AbonneService);
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

    it('should create a Abonne', () => {
      const abonne = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(abonne).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Abonne', () => {
      const abonne = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(abonne).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Abonne', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Abonne', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Abonne', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAbonneToCollectionIfMissing', () => {
      it('should add a Abonne to an empty array', () => {
        const abonne: IAbonne = sampleWithRequiredData;
        expectedResult = service.addAbonneToCollectionIfMissing([], abonne);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(abonne);
      });

      it('should not add a Abonne to an array that contains it', () => {
        const abonne: IAbonne = sampleWithRequiredData;
        const abonneCollection: IAbonne[] = [
          {
            ...abonne,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAbonneToCollectionIfMissing(abonneCollection, abonne);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Abonne to an array that doesn't contain it", () => {
        const abonne: IAbonne = sampleWithRequiredData;
        const abonneCollection: IAbonne[] = [sampleWithPartialData];
        expectedResult = service.addAbonneToCollectionIfMissing(abonneCollection, abonne);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(abonne);
      });

      it('should add only unique Abonne to an array', () => {
        const abonneArray: IAbonne[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const abonneCollection: IAbonne[] = [sampleWithRequiredData];
        expectedResult = service.addAbonneToCollectionIfMissing(abonneCollection, ...abonneArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const abonne: IAbonne = sampleWithRequiredData;
        const abonne2: IAbonne = sampleWithPartialData;
        expectedResult = service.addAbonneToCollectionIfMissing([], abonne, abonne2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(abonne);
        expect(expectedResult).toContain(abonne2);
      });

      it('should accept null and undefined values', () => {
        const abonne: IAbonne = sampleWithRequiredData;
        expectedResult = service.addAbonneToCollectionIfMissing([], null, abonne, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(abonne);
      });

      it('should return initial array if no Abonne is added', () => {
        const abonneCollection: IAbonne[] = [sampleWithRequiredData];
        expectedResult = service.addAbonneToCollectionIfMissing(abonneCollection, undefined, null);
        expect(expectedResult).toEqual(abonneCollection);
      });
    });

    describe('compareAbonne', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAbonne(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { idAbonne: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareAbonne(entity1, entity2);
        const compareResult2 = service.compareAbonne(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { idAbonne: 'ABC' };
        const entity2 = { idAbonne: 'CBA' };

        const compareResult1 = service.compareAbonne(entity1, entity2);
        const compareResult2 = service.compareAbonne(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { idAbonne: 'ABC' };
        const entity2 = { idAbonne: 'ABC' };

        const compareResult1 = service.compareAbonne(entity1, entity2);
        const compareResult2 = service.compareAbonne(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

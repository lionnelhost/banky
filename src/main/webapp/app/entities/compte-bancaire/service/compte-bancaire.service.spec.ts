import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICompteBancaire } from '../compte-bancaire.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../compte-bancaire.test-samples';

import { CompteBancaireService } from './compte-bancaire.service';

const requireRestSample: ICompteBancaire = {
  ...sampleWithRequiredData,
};

describe('CompteBancaire Service', () => {
  let service: CompteBancaireService;
  let httpMock: HttpTestingController;
  let expectedResult: ICompteBancaire | ICompteBancaire[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CompteBancaireService);
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

    it('should create a CompteBancaire', () => {
      const compteBancaire = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(compteBancaire).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CompteBancaire', () => {
      const compteBancaire = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(compteBancaire).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CompteBancaire', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CompteBancaire', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CompteBancaire', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCompteBancaireToCollectionIfMissing', () => {
      it('should add a CompteBancaire to an empty array', () => {
        const compteBancaire: ICompteBancaire = sampleWithRequiredData;
        expectedResult = service.addCompteBancaireToCollectionIfMissing([], compteBancaire);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(compteBancaire);
      });

      it('should not add a CompteBancaire to an array that contains it', () => {
        const compteBancaire: ICompteBancaire = sampleWithRequiredData;
        const compteBancaireCollection: ICompteBancaire[] = [
          {
            ...compteBancaire,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCompteBancaireToCollectionIfMissing(compteBancaireCollection, compteBancaire);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CompteBancaire to an array that doesn't contain it", () => {
        const compteBancaire: ICompteBancaire = sampleWithRequiredData;
        const compteBancaireCollection: ICompteBancaire[] = [sampleWithPartialData];
        expectedResult = service.addCompteBancaireToCollectionIfMissing(compteBancaireCollection, compteBancaire);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(compteBancaire);
      });

      it('should add only unique CompteBancaire to an array', () => {
        const compteBancaireArray: ICompteBancaire[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const compteBancaireCollection: ICompteBancaire[] = [sampleWithRequiredData];
        expectedResult = service.addCompteBancaireToCollectionIfMissing(compteBancaireCollection, ...compteBancaireArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const compteBancaire: ICompteBancaire = sampleWithRequiredData;
        const compteBancaire2: ICompteBancaire = sampleWithPartialData;
        expectedResult = service.addCompteBancaireToCollectionIfMissing([], compteBancaire, compteBancaire2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(compteBancaire);
        expect(expectedResult).toContain(compteBancaire2);
      });

      it('should accept null and undefined values', () => {
        const compteBancaire: ICompteBancaire = sampleWithRequiredData;
        expectedResult = service.addCompteBancaireToCollectionIfMissing([], null, compteBancaire, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(compteBancaire);
      });

      it('should return initial array if no CompteBancaire is added', () => {
        const compteBancaireCollection: ICompteBancaire[] = [sampleWithRequiredData];
        expectedResult = service.addCompteBancaireToCollectionIfMissing(compteBancaireCollection, undefined, null);
        expect(expectedResult).toEqual(compteBancaireCollection);
      });
    });

    describe('compareCompteBancaire', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCompteBancaire(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { idCompteBancaire: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareCompteBancaire(entity1, entity2);
        const compareResult2 = service.compareCompteBancaire(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { idCompteBancaire: 'ABC' };
        const entity2 = { idCompteBancaire: 'CBA' };

        const compareResult1 = service.compareCompteBancaire(entity1, entity2);
        const compareResult2 = service.compareCompteBancaire(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { idCompteBancaire: 'ABC' };
        const entity2 = { idCompteBancaire: 'ABC' };

        const compareResult1 = service.compareCompteBancaire(entity1, entity2);
        const compareResult2 = service.compareCompteBancaire(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

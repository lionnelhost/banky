import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IMessageErreur } from '../message-erreur.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../message-erreur.test-samples';

import { MessageErreurService } from './message-erreur.service';

const requireRestSample: IMessageErreur = {
  ...sampleWithRequiredData,
};

describe('MessageErreur Service', () => {
  let service: MessageErreurService;
  let httpMock: HttpTestingController;
  let expectedResult: IMessageErreur | IMessageErreur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(MessageErreurService);
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

    it('should create a MessageErreur', () => {
      const messageErreur = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(messageErreur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MessageErreur', () => {
      const messageErreur = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(messageErreur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MessageErreur', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MessageErreur', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MessageErreur', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMessageErreurToCollectionIfMissing', () => {
      it('should add a MessageErreur to an empty array', () => {
        const messageErreur: IMessageErreur = sampleWithRequiredData;
        expectedResult = service.addMessageErreurToCollectionIfMissing([], messageErreur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(messageErreur);
      });

      it('should not add a MessageErreur to an array that contains it', () => {
        const messageErreur: IMessageErreur = sampleWithRequiredData;
        const messageErreurCollection: IMessageErreur[] = [
          {
            ...messageErreur,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMessageErreurToCollectionIfMissing(messageErreurCollection, messageErreur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MessageErreur to an array that doesn't contain it", () => {
        const messageErreur: IMessageErreur = sampleWithRequiredData;
        const messageErreurCollection: IMessageErreur[] = [sampleWithPartialData];
        expectedResult = service.addMessageErreurToCollectionIfMissing(messageErreurCollection, messageErreur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(messageErreur);
      });

      it('should add only unique MessageErreur to an array', () => {
        const messageErreurArray: IMessageErreur[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const messageErreurCollection: IMessageErreur[] = [sampleWithRequiredData];
        expectedResult = service.addMessageErreurToCollectionIfMissing(messageErreurCollection, ...messageErreurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const messageErreur: IMessageErreur = sampleWithRequiredData;
        const messageErreur2: IMessageErreur = sampleWithPartialData;
        expectedResult = service.addMessageErreurToCollectionIfMissing([], messageErreur, messageErreur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(messageErreur);
        expect(expectedResult).toContain(messageErreur2);
      });

      it('should accept null and undefined values', () => {
        const messageErreur: IMessageErreur = sampleWithRequiredData;
        expectedResult = service.addMessageErreurToCollectionIfMissing([], null, messageErreur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(messageErreur);
      });

      it('should return initial array if no MessageErreur is added', () => {
        const messageErreurCollection: IMessageErreur[] = [sampleWithRequiredData];
        expectedResult = service.addMessageErreurToCollectionIfMissing(messageErreurCollection, undefined, null);
        expect(expectedResult).toEqual(messageErreurCollection);
      });
    });

    describe('compareMessageErreur', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMessageErreur(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { idMessageErreur: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareMessageErreur(entity1, entity2);
        const compareResult2 = service.compareMessageErreur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { idMessageErreur: 'ABC' };
        const entity2 = { idMessageErreur: 'CBA' };

        const compareResult1 = service.compareMessageErreur(entity1, entity2);
        const compareResult2 = service.compareMessageErreur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { idMessageErreur: 'ABC' };
        const entity2 = { idMessageErreur: 'ABC' };

        const compareResult1 = service.compareMessageErreur(entity1, entity2);
        const compareResult2 = service.compareMessageErreur(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

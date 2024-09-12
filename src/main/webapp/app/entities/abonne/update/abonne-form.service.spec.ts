import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../abonne.test-samples';

import { AbonneFormService } from './abonne-form.service';

describe('Abonne Form Service', () => {
  let service: AbonneFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AbonneFormService);
  });

  describe('Service methods', () => {
    describe('createAbonneFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAbonneFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idAbonne: expect.any(Object),
            indiceClient: expect.any(Object),
            nomAbonne: expect.any(Object),
            prenomAbonne: expect.any(Object),
            telephone: expect.any(Object),
            email: expect.any(Object),
            typePieceIdentite: expect.any(Object),
            numPieceIdentite: expect.any(Object),
            statut: expect.any(Object),
            identifiant: expect.any(Object),
          }),
        );
      });

      it('passing IAbonne should create a new form with FormGroup', () => {
        const formGroup = service.createAbonneFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idAbonne: expect.any(Object),
            indiceClient: expect.any(Object),
            nomAbonne: expect.any(Object),
            prenomAbonne: expect.any(Object),
            telephone: expect.any(Object),
            email: expect.any(Object),
            typePieceIdentite: expect.any(Object),
            numPieceIdentite: expect.any(Object),
            statut: expect.any(Object),
            identifiant: expect.any(Object),
          }),
        );
      });
    });

    describe('getAbonne', () => {
      it('should return NewAbonne for default Abonne initial value', () => {
        const formGroup = service.createAbonneFormGroup(sampleWithNewData);

        const abonne = service.getAbonne(formGroup) as any;

        expect(abonne).toMatchObject(sampleWithNewData);
      });

      it('should return NewAbonne for empty Abonne initial value', () => {
        const formGroup = service.createAbonneFormGroup();

        const abonne = service.getAbonne(formGroup) as any;

        expect(abonne).toMatchObject({});
      });

      it('should return IAbonne', () => {
        const formGroup = service.createAbonneFormGroup(sampleWithRequiredData);

        const abonne = service.getAbonne(formGroup) as any;

        expect(abonne).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

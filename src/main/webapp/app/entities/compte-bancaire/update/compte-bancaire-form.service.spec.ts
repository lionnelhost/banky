import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../compte-bancaire.test-samples';

import { CompteBancaireFormService } from './compte-bancaire-form.service';

describe('CompteBancaire Form Service', () => {
  let service: CompteBancaireFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompteBancaireFormService);
  });

  describe('Service methods', () => {
    describe('createCompteBancaireFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCompteBancaireFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idCompteBancaire: expect.any(Object),
            age: expect.any(Object),
            ncp: expect.any(Object),
            sde: expect.any(Object),
            sin: expect.any(Object),
            soldeDisponible: expect.any(Object),
            rib: expect.any(Object),
            status: expect.any(Object),
            contrat: expect.any(Object),
          }),
        );
      });

      it('passing ICompteBancaire should create a new form with FormGroup', () => {
        const formGroup = service.createCompteBancaireFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idCompteBancaire: expect.any(Object),
            age: expect.any(Object),
            ncp: expect.any(Object),
            sde: expect.any(Object),
            sin: expect.any(Object),
            soldeDisponible: expect.any(Object),
            rib: expect.any(Object),
            status: expect.any(Object),
            contrat: expect.any(Object),
          }),
        );
      });
    });

    describe('getCompteBancaire', () => {
      it('should return NewCompteBancaire for default CompteBancaire initial value', () => {
        const formGroup = service.createCompteBancaireFormGroup(sampleWithNewData);

        const compteBancaire = service.getCompteBancaire(formGroup) as any;

        expect(compteBancaire).toMatchObject(sampleWithNewData);
      });

      it('should return NewCompteBancaire for empty CompteBancaire initial value', () => {
        const formGroup = service.createCompteBancaireFormGroup();

        const compteBancaire = service.getCompteBancaire(formGroup) as any;

        expect(compteBancaire).toMatchObject({});
      });

      it('should return ICompteBancaire', () => {
        const formGroup = service.createCompteBancaireFormGroup(sampleWithRequiredData);

        const compteBancaire = service.getCompteBancaire(formGroup) as any;

        expect(compteBancaire).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

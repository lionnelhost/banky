import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../contrat-abonnement-compte.test-samples';

import { ContratAbonnementCompteFormService } from './contrat-abonnement-compte-form.service';

describe('ContratAbonnementCompte Form Service', () => {
  let service: ContratAbonnementCompteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContratAbonnementCompteFormService);
  });

  describe('Service methods', () => {
    describe('createContratAbonnementCompteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContratAbonnementCompteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idContrat: expect.any(Object),
            idAbonne: expect.any(Object),
            idCompteBancaire: expect.any(Object),
            contrat: expect.any(Object),
            abonne: expect.any(Object),
            compteBancaire: expect.any(Object),
          }),
        );
      });

      it('passing IContratAbonnementCompte should create a new form with FormGroup', () => {
        const formGroup = service.createContratAbonnementCompteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idContrat: expect.any(Object),
            idAbonne: expect.any(Object),
            idCompteBancaire: expect.any(Object),
            contrat: expect.any(Object),
            abonne: expect.any(Object),
            compteBancaire: expect.any(Object),
          }),
        );
      });
    });

    describe('getContratAbonnementCompte', () => {
      it('should return NewContratAbonnementCompte for default ContratAbonnementCompte initial value', () => {
        const formGroup = service.createContratAbonnementCompteFormGroup(sampleWithNewData);

        const contratAbonnementCompte = service.getContratAbonnementCompte(formGroup) as any;

        expect(contratAbonnementCompte).toMatchObject(sampleWithNewData);
      });

      it('should return NewContratAbonnementCompte for empty ContratAbonnementCompte initial value', () => {
        const formGroup = service.createContratAbonnementCompteFormGroup();

        const contratAbonnementCompte = service.getContratAbonnementCompte(formGroup) as any;

        expect(contratAbonnementCompte).toMatchObject({});
      });

      it('should return IContratAbonnementCompte', () => {
        const formGroup = service.createContratAbonnementCompteFormGroup(sampleWithRequiredData);

        const contratAbonnementCompte = service.getContratAbonnementCompte(formGroup) as any;

        expect(contratAbonnementCompte).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IContratAbonnementCompte should not enable id FormControl', () => {
        const formGroup = service.createContratAbonnementCompteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewContratAbonnementCompte should disable id FormControl', () => {
        const formGroup = service.createContratAbonnementCompteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

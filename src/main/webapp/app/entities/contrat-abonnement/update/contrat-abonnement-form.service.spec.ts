import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../contrat-abonnement.test-samples';

import { ContratAbonnementFormService } from './contrat-abonnement-form.service';

describe('ContratAbonnement Form Service', () => {
  let service: ContratAbonnementFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContratAbonnementFormService);
  });

  describe('Service methods', () => {
    describe('createContratAbonnementFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContratAbonnementFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idContrat: expect.any(Object),
            idAbonne: expect.any(Object),
            contrat: expect.any(Object),
            abonne: expect.any(Object),
          }),
        );
      });

      it('passing IContratAbonnement should create a new form with FormGroup', () => {
        const formGroup = service.createContratAbonnementFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idContrat: expect.any(Object),
            idAbonne: expect.any(Object),
            contrat: expect.any(Object),
            abonne: expect.any(Object),
          }),
        );
      });
    });

    describe('getContratAbonnement', () => {
      it('should return NewContratAbonnement for default ContratAbonnement initial value', () => {
        const formGroup = service.createContratAbonnementFormGroup(sampleWithNewData);

        const contratAbonnement = service.getContratAbonnement(formGroup) as any;

        expect(contratAbonnement).toMatchObject(sampleWithNewData);
      });

      it('should return NewContratAbonnement for empty ContratAbonnement initial value', () => {
        const formGroup = service.createContratAbonnementFormGroup();

        const contratAbonnement = service.getContratAbonnement(formGroup) as any;

        expect(contratAbonnement).toMatchObject({});
      });

      it('should return IContratAbonnement', () => {
        const formGroup = service.createContratAbonnementFormGroup(sampleWithRequiredData);

        const contratAbonnement = service.getContratAbonnement(formGroup) as any;

        expect(contratAbonnement).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IContratAbonnement should not enable id FormControl', () => {
        const formGroup = service.createContratAbonnementFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewContratAbonnement should disable id FormControl', () => {
        const formGroup = service.createContratAbonnementFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

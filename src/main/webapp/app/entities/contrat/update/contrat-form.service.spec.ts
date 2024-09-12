import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../contrat.test-samples';

import { ContratFormService } from './contrat-form.service';

describe('Contrat Form Service', () => {
  let service: ContratFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContratFormService);
  });

  describe('Service methods', () => {
    describe('createContratFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContratFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idContrat: expect.any(Object),
            dateValidite: expect.any(Object),
            typeContrat: expect.any(Object),
          }),
        );
      });

      it('passing IContrat should create a new form with FormGroup', () => {
        const formGroup = service.createContratFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idContrat: expect.any(Object),
            dateValidite: expect.any(Object),
            typeContrat: expect.any(Object),
          }),
        );
      });
    });

    describe('getContrat', () => {
      it('should return NewContrat for default Contrat initial value', () => {
        const formGroup = service.createContratFormGroup(sampleWithNewData);

        const contrat = service.getContrat(formGroup) as any;

        expect(contrat).toMatchObject(sampleWithNewData);
      });

      it('should return NewContrat for empty Contrat initial value', () => {
        const formGroup = service.createContratFormGroup();

        const contrat = service.getContrat(formGroup) as any;

        expect(contrat).toMatchObject({});
      });

      it('should return IContrat', () => {
        const formGroup = service.createContratFormGroup(sampleWithRequiredData);

        const contrat = service.getContrat(formGroup) as any;

        expect(contrat).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

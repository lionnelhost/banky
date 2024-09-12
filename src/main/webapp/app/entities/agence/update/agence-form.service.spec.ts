import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../agence.test-samples';

import { AgenceFormService } from './agence-form.service';

describe('Agence Form Service', () => {
  let service: AgenceFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AgenceFormService);
  });

  describe('Service methods', () => {
    describe('createAgenceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAgenceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idAgence: expect.any(Object),
            codeAgence: expect.any(Object),
            nomAgence: expect.any(Object),
            banque: expect.any(Object),
          }),
        );
      });

      it('passing IAgence should create a new form with FormGroup', () => {
        const formGroup = service.createAgenceFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idAgence: expect.any(Object),
            codeAgence: expect.any(Object),
            nomAgence: expect.any(Object),
            banque: expect.any(Object),
          }),
        );
      });
    });

    describe('getAgence', () => {
      it('should return NewAgence for default Agence initial value', () => {
        const formGroup = service.createAgenceFormGroup(sampleWithNewData);

        const agence = service.getAgence(formGroup) as any;

        expect(agence).toMatchObject(sampleWithNewData);
      });

      it('should return NewAgence for empty Agence initial value', () => {
        const formGroup = service.createAgenceFormGroup();

        const agence = service.getAgence(formGroup) as any;

        expect(agence).toMatchObject({});
      });

      it('should return IAgence', () => {
        const formGroup = service.createAgenceFormGroup(sampleWithRequiredData);

        const agence = service.getAgence(formGroup) as any;

        expect(agence).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

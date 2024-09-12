import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../dispositif-signature.test-samples';

import { DispositifSignatureFormService } from './dispositif-signature-form.service';

describe('DispositifSignature Form Service', () => {
  let service: DispositifSignatureFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DispositifSignatureFormService);
  });

  describe('Service methods', () => {
    describe('createDispositifSignatureFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDispositifSignatureFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idDispositif: expect.any(Object),
            libelle: expect.any(Object),
          }),
        );
      });

      it('passing IDispositifSignature should create a new form with FormGroup', () => {
        const formGroup = service.createDispositifSignatureFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idDispositif: expect.any(Object),
            libelle: expect.any(Object),
          }),
        );
      });
    });

    describe('getDispositifSignature', () => {
      it('should return NewDispositifSignature for default DispositifSignature initial value', () => {
        const formGroup = service.createDispositifSignatureFormGroup(sampleWithNewData);

        const dispositifSignature = service.getDispositifSignature(formGroup) as any;

        expect(dispositifSignature).toMatchObject(sampleWithNewData);
      });

      it('should return NewDispositifSignature for empty DispositifSignature initial value', () => {
        const formGroup = service.createDispositifSignatureFormGroup();

        const dispositifSignature = service.getDispositifSignature(formGroup) as any;

        expect(dispositifSignature).toMatchObject({});
      });

      it('should return IDispositifSignature', () => {
        const formGroup = service.createDispositifSignatureFormGroup(sampleWithRequiredData);

        const dispositifSignature = service.getDispositifSignature(formGroup) as any;

        expect(dispositifSignature).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

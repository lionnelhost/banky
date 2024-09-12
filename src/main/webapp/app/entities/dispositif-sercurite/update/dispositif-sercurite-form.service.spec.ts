import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../dispositif-sercurite.test-samples';

import { DispositifSercuriteFormService } from './dispositif-sercurite-form.service';

describe('DispositifSercurite Form Service', () => {
  let service: DispositifSercuriteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DispositifSercuriteFormService);
  });

  describe('Service methods', () => {
    describe('createDispositifSercuriteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDispositifSercuriteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idCanal: expect.any(Object),
            idTypeTransaction: expect.any(Object),
            idDispositif: expect.any(Object),
            canal: expect.any(Object),
            typeTransaction: expect.any(Object),
            dispositifSignature: expect.any(Object),
          }),
        );
      });

      it('passing IDispositifSercurite should create a new form with FormGroup', () => {
        const formGroup = service.createDispositifSercuriteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idCanal: expect.any(Object),
            idTypeTransaction: expect.any(Object),
            idDispositif: expect.any(Object),
            canal: expect.any(Object),
            typeTransaction: expect.any(Object),
            dispositifSignature: expect.any(Object),
          }),
        );
      });
    });

    describe('getDispositifSercurite', () => {
      it('should return NewDispositifSercurite for default DispositifSercurite initial value', () => {
        const formGroup = service.createDispositifSercuriteFormGroup(sampleWithNewData);

        const dispositifSercurite = service.getDispositifSercurite(formGroup) as any;

        expect(dispositifSercurite).toMatchObject(sampleWithNewData);
      });

      it('should return NewDispositifSercurite for empty DispositifSercurite initial value', () => {
        const formGroup = service.createDispositifSercuriteFormGroup();

        const dispositifSercurite = service.getDispositifSercurite(formGroup) as any;

        expect(dispositifSercurite).toMatchObject({});
      });

      it('should return IDispositifSercurite', () => {
        const formGroup = service.createDispositifSercuriteFormGroup(sampleWithRequiredData);

        const dispositifSercurite = service.getDispositifSercurite(formGroup) as any;

        expect(dispositifSercurite).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDispositifSercurite should not enable id FormControl', () => {
        const formGroup = service.createDispositifSercuriteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDispositifSercurite should disable id FormControl', () => {
        const formGroup = service.createDispositifSercuriteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

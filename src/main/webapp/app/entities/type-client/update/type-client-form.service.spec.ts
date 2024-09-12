import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../type-client.test-samples';

import { TypeClientFormService } from './type-client-form.service';

describe('TypeClient Form Service', () => {
  let service: TypeClientFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TypeClientFormService);
  });

  describe('Service methods', () => {
    describe('createTypeClientFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTypeClientFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idTypeClient: expect.any(Object),
            libelle: expect.any(Object),
          }),
        );
      });

      it('passing ITypeClient should create a new form with FormGroup', () => {
        const formGroup = service.createTypeClientFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idTypeClient: expect.any(Object),
            libelle: expect.any(Object),
          }),
        );
      });
    });

    describe('getTypeClient', () => {
      it('should return NewTypeClient for default TypeClient initial value', () => {
        const formGroup = service.createTypeClientFormGroup(sampleWithNewData);

        const typeClient = service.getTypeClient(formGroup) as any;

        expect(typeClient).toMatchObject(sampleWithNewData);
      });

      it('should return NewTypeClient for empty TypeClient initial value', () => {
        const formGroup = service.createTypeClientFormGroup();

        const typeClient = service.getTypeClient(formGroup) as any;

        expect(typeClient).toMatchObject({});
      });

      it('should return ITypeClient', () => {
        const formGroup = service.createTypeClientFormGroup(sampleWithRequiredData);

        const typeClient = service.getTypeClient(formGroup) as any;

        expect(typeClient).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

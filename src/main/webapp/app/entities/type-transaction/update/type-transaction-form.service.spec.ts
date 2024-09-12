import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../type-transaction.test-samples';

import { TypeTransactionFormService } from './type-transaction-form.service';

describe('TypeTransaction Form Service', () => {
  let service: TypeTransactionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TypeTransactionFormService);
  });

  describe('Service methods', () => {
    describe('createTypeTransactionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTypeTransactionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idTypeTransaction: expect.any(Object),
            libelle: expect.any(Object),
          }),
        );
      });

      it('passing ITypeTransaction should create a new form with FormGroup', () => {
        const formGroup = service.createTypeTransactionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idTypeTransaction: expect.any(Object),
            libelle: expect.any(Object),
          }),
        );
      });
    });

    describe('getTypeTransaction', () => {
      it('should return NewTypeTransaction for default TypeTransaction initial value', () => {
        const formGroup = service.createTypeTransactionFormGroup(sampleWithNewData);

        const typeTransaction = service.getTypeTransaction(formGroup) as any;

        expect(typeTransaction).toMatchObject(sampleWithNewData);
      });

      it('should return NewTypeTransaction for empty TypeTransaction initial value', () => {
        const formGroup = service.createTypeTransactionFormGroup();

        const typeTransaction = service.getTypeTransaction(formGroup) as any;

        expect(typeTransaction).toMatchObject({});
      });

      it('should return ITypeTransaction', () => {
        const formGroup = service.createTypeTransactionFormGroup(sampleWithRequiredData);

        const typeTransaction = service.getTypeTransaction(formGroup) as any;

        expect(typeTransaction).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

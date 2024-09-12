import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../parametrage-global.test-samples';

import { ParametrageGlobalFormService } from './parametrage-global-form.service';

describe('ParametrageGlobal Form Service', () => {
  let service: ParametrageGlobalFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParametrageGlobalFormService);
  });

  describe('Service methods', () => {
    describe('createParametrageGlobalFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createParametrageGlobalFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idParamGlobal: expect.any(Object),
            codeParam: expect.any(Object),
            typeParam: expect.any(Object),
            valeur: expect.any(Object),
          }),
        );
      });

      it('passing IParametrageGlobal should create a new form with FormGroup', () => {
        const formGroup = service.createParametrageGlobalFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idParamGlobal: expect.any(Object),
            codeParam: expect.any(Object),
            typeParam: expect.any(Object),
            valeur: expect.any(Object),
          }),
        );
      });
    });

    describe('getParametrageGlobal', () => {
      it('should return NewParametrageGlobal for default ParametrageGlobal initial value', () => {
        const formGroup = service.createParametrageGlobalFormGroup(sampleWithNewData);

        const parametrageGlobal = service.getParametrageGlobal(formGroup) as any;

        expect(parametrageGlobal).toMatchObject(sampleWithNewData);
      });

      it('should return NewParametrageGlobal for empty ParametrageGlobal initial value', () => {
        const formGroup = service.createParametrageGlobalFormGroup();

        const parametrageGlobal = service.getParametrageGlobal(formGroup) as any;

        expect(parametrageGlobal).toMatchObject({});
      });

      it('should return IParametrageGlobal', () => {
        const formGroup = service.createParametrageGlobalFormGroup(sampleWithRequiredData);

        const parametrageGlobal = service.getParametrageGlobal(formGroup) as any;

        expect(parametrageGlobal).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

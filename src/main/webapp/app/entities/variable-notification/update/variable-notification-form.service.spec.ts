import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../variable-notification.test-samples';

import { VariableNotificationFormService } from './variable-notification-form.service';

describe('VariableNotification Form Service', () => {
  let service: VariableNotificationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VariableNotificationFormService);
  });

  describe('Service methods', () => {
    describe('createVariableNotificationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createVariableNotificationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idVarNotification: expect.any(Object),
            codeVariable: expect.any(Object),
            description: expect.any(Object),
          }),
        );
      });

      it('passing IVariableNotification should create a new form with FormGroup', () => {
        const formGroup = service.createVariableNotificationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idVarNotification: expect.any(Object),
            codeVariable: expect.any(Object),
            description: expect.any(Object),
          }),
        );
      });
    });

    describe('getVariableNotification', () => {
      it('should return NewVariableNotification for default VariableNotification initial value', () => {
        const formGroup = service.createVariableNotificationFormGroup(sampleWithNewData);

        const variableNotification = service.getVariableNotification(formGroup) as any;

        expect(variableNotification).toMatchObject(sampleWithNewData);
      });

      it('should return NewVariableNotification for empty VariableNotification initial value', () => {
        const formGroup = service.createVariableNotificationFormGroup();

        const variableNotification = service.getVariableNotification(formGroup) as any;

        expect(variableNotification).toMatchObject({});
      });

      it('should return IVariableNotification', () => {
        const formGroup = service.createVariableNotificationFormGroup(sampleWithRequiredData);

        const variableNotification = service.getVariableNotification(formGroup) as any;

        expect(variableNotification).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

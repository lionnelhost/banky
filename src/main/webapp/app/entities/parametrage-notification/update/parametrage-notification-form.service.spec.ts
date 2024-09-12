import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../parametrage-notification.test-samples';

import { ParametrageNotificationFormService } from './parametrage-notification-form.service';

describe('ParametrageNotification Form Service', () => {
  let service: ParametrageNotificationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParametrageNotificationFormService);
  });

  describe('Service methods', () => {
    describe('createParametrageNotificationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createParametrageNotificationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idParamNotif: expect.any(Object),
            objetNotif: expect.any(Object),
            typeNotif: expect.any(Object),
            contenu: expect.any(Object),
          }),
        );
      });

      it('passing IParametrageNotification should create a new form with FormGroup', () => {
        const formGroup = service.createParametrageNotificationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idParamNotif: expect.any(Object),
            objetNotif: expect.any(Object),
            typeNotif: expect.any(Object),
            contenu: expect.any(Object),
          }),
        );
      });
    });

    describe('getParametrageNotification', () => {
      it('should return NewParametrageNotification for default ParametrageNotification initial value', () => {
        const formGroup = service.createParametrageNotificationFormGroup(sampleWithNewData);

        const parametrageNotification = service.getParametrageNotification(formGroup) as any;

        expect(parametrageNotification).toMatchObject(sampleWithNewData);
      });

      it('should return NewParametrageNotification for empty ParametrageNotification initial value', () => {
        const formGroup = service.createParametrageNotificationFormGroup();

        const parametrageNotification = service.getParametrageNotification(formGroup) as any;

        expect(parametrageNotification).toMatchObject({});
      });

      it('should return IParametrageNotification', () => {
        const formGroup = service.createParametrageNotificationFormGroup(sampleWithRequiredData);

        const parametrageNotification = service.getParametrageNotification(formGroup) as any;

        expect(parametrageNotification).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

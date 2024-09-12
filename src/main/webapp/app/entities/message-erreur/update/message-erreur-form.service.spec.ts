import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../message-erreur.test-samples';

import { MessageErreurFormService } from './message-erreur-form.service';

describe('MessageErreur Form Service', () => {
  let service: MessageErreurFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MessageErreurFormService);
  });

  describe('Service methods', () => {
    describe('createMessageErreurFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMessageErreurFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idMessageErreur: expect.any(Object),
            codeErreur: expect.any(Object),
            description: expect.any(Object),
          }),
        );
      });

      it('passing IMessageErreur should create a new form with FormGroup', () => {
        const formGroup = service.createMessageErreurFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idMessageErreur: expect.any(Object),
            codeErreur: expect.any(Object),
            description: expect.any(Object),
          }),
        );
      });
    });

    describe('getMessageErreur', () => {
      it('should return NewMessageErreur for default MessageErreur initial value', () => {
        const formGroup = service.createMessageErreurFormGroup(sampleWithNewData);

        const messageErreur = service.getMessageErreur(formGroup) as any;

        expect(messageErreur).toMatchObject(sampleWithNewData);
      });

      it('should return NewMessageErreur for empty MessageErreur initial value', () => {
        const formGroup = service.createMessageErreurFormGroup();

        const messageErreur = service.getMessageErreur(formGroup) as any;

        expect(messageErreur).toMatchObject({});
      });

      it('should return IMessageErreur', () => {
        const formGroup = service.createMessageErreurFormGroup(sampleWithRequiredData);

        const messageErreur = service.getMessageErreur(formGroup) as any;

        expect(messageErreur).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

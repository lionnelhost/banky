import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../participant.test-samples';

import { ParticipantFormService } from './participant-form.service';

describe('Participant Form Service', () => {
  let service: ParticipantFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParticipantFormService);
  });

  describe('Service methods', () => {
    describe('createParticipantFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createParticipantFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idParticipant: expect.any(Object),
            codeParticipant: expect.any(Object),
            codeBanque: expect.any(Object),
            nomBanque: expect.any(Object),
            libelle: expect.any(Object),
            pays: expect.any(Object),
            isActif: expect.any(Object),
          }),
        );
      });

      it('passing IParticipant should create a new form with FormGroup', () => {
        const formGroup = service.createParticipantFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idParticipant: expect.any(Object),
            codeParticipant: expect.any(Object),
            codeBanque: expect.any(Object),
            nomBanque: expect.any(Object),
            libelle: expect.any(Object),
            pays: expect.any(Object),
            isActif: expect.any(Object),
          }),
        );
      });
    });

    describe('getParticipant', () => {
      it('should return NewParticipant for default Participant initial value', () => {
        const formGroup = service.createParticipantFormGroup(sampleWithNewData);

        const participant = service.getParticipant(formGroup) as any;

        expect(participant).toMatchObject(sampleWithNewData);
      });

      it('should return NewParticipant for empty Participant initial value', () => {
        const formGroup = service.createParticipantFormGroup();

        const participant = service.getParticipant(formGroup) as any;

        expect(participant).toMatchObject({});
      });

      it('should return IParticipant', () => {
        const formGroup = service.createParticipantFormGroup(sampleWithRequiredData);

        const participant = service.getParticipant(formGroup) as any;

        expect(participant).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

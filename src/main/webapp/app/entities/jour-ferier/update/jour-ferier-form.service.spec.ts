import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../jour-ferier.test-samples';

import { JourFerierFormService } from './jour-ferier-form.service';

describe('JourFerier Form Service', () => {
  let service: JourFerierFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JourFerierFormService);
  });

  describe('Service methods', () => {
    describe('createJourFerierFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createJourFerierFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idJourFerie: expect.any(Object),
            libelle: expect.any(Object),
          }),
        );
      });

      it('passing IJourFerier should create a new form with FormGroup', () => {
        const formGroup = service.createJourFerierFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idJourFerie: expect.any(Object),
            libelle: expect.any(Object),
          }),
        );
      });
    });

    describe('getJourFerier', () => {
      it('should return NewJourFerier for default JourFerier initial value', () => {
        const formGroup = service.createJourFerierFormGroup(sampleWithNewData);

        const jourFerier = service.getJourFerier(formGroup) as any;

        expect(jourFerier).toMatchObject(sampleWithNewData);
      });

      it('should return NewJourFerier for empty JourFerier initial value', () => {
        const formGroup = service.createJourFerierFormGroup();

        const jourFerier = service.getJourFerier(formGroup) as any;

        expect(jourFerier).toMatchObject({});
      });

      it('should return IJourFerier', () => {
        const formGroup = service.createJourFerierFormGroup(sampleWithRequiredData);

        const jourFerier = service.getJourFerier(formGroup) as any;

        expect(jourFerier).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

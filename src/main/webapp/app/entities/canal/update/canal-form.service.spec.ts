import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../canal.test-samples';

import { CanalFormService } from './canal-form.service';

describe('Canal Form Service', () => {
  let service: CanalFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CanalFormService);
  });

  describe('Service methods', () => {
    describe('createCanalFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCanalFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idCanal: expect.any(Object),
            libelle: expect.any(Object),
          }),
        );
      });

      it('passing ICanal should create a new form with FormGroup', () => {
        const formGroup = service.createCanalFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idCanal: expect.any(Object),
            libelle: expect.any(Object),
          }),
        );
      });
    });

    describe('getCanal', () => {
      it('should return NewCanal for default Canal initial value', () => {
        const formGroup = service.createCanalFormGroup(sampleWithNewData);

        const canal = service.getCanal(formGroup) as any;

        expect(canal).toMatchObject(sampleWithNewData);
      });

      it('should return NewCanal for empty Canal initial value', () => {
        const formGroup = service.createCanalFormGroup();

        const canal = service.getCanal(formGroup) as any;

        expect(canal).toMatchObject({});
      });

      it('should return ICanal', () => {
        const formGroup = service.createCanalFormGroup(sampleWithRequiredData);

        const canal = service.getCanal(formGroup) as any;

        expect(canal).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../banque.test-samples';

import { BanqueFormService } from './banque-form.service';

describe('Banque Form Service', () => {
  let service: BanqueFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BanqueFormService);
  });

  describe('Service methods', () => {
    describe('createBanqueFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBanqueFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idBanque: expect.any(Object),
            code: expect.any(Object),
            devise: expect.any(Object),
            langue: expect.any(Object),
            logo: expect.any(Object),
            codeSwift: expect.any(Object),
            codeIban: expect.any(Object),
            codePays: expect.any(Object),
            fuseauHoraire: expect.any(Object),
            cutOffTime: expect.any(Object),
            codeParticipant: expect.any(Object),
            libelleParticipant: expect.any(Object),
          }),
        );
      });

      it('passing IBanque should create a new form with FormGroup', () => {
        const formGroup = service.createBanqueFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idBanque: expect.any(Object),
            code: expect.any(Object),
            devise: expect.any(Object),
            langue: expect.any(Object),
            logo: expect.any(Object),
            codeSwift: expect.any(Object),
            codeIban: expect.any(Object),
            codePays: expect.any(Object),
            fuseauHoraire: expect.any(Object),
            cutOffTime: expect.any(Object),
            codeParticipant: expect.any(Object),
            libelleParticipant: expect.any(Object),
          }),
        );
      });
    });

    describe('getBanque', () => {
      it('should return NewBanque for default Banque initial value', () => {
        const formGroup = service.createBanqueFormGroup(sampleWithNewData);

        const banque = service.getBanque(formGroup) as any;

        expect(banque).toMatchObject(sampleWithNewData);
      });

      it('should return NewBanque for empty Banque initial value', () => {
        const formGroup = service.createBanqueFormGroup();

        const banque = service.getBanque(formGroup) as any;

        expect(banque).toMatchObject({});
      });

      it('should return IBanque', () => {
        const formGroup = service.createBanqueFormGroup(sampleWithRequiredData);

        const banque = service.getBanque(formGroup) as any;

        expect(banque).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

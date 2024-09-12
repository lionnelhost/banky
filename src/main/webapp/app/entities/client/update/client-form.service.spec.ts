import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../client.test-samples';

import { ClientFormService } from './client-form.service';

describe('Client Form Service', () => {
  let service: ClientFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClientFormService);
  });

  describe('Service methods', () => {
    describe('createClientFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createClientFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idClient: expect.any(Object),
            indiceClient: expect.any(Object),
            nomClient: expect.any(Object),
            prenomClient: expect.any(Object),
            raisonSociale: expect.any(Object),
            telephone: expect.any(Object),
            email: expect.any(Object),
            contrat: expect.any(Object),
            typeClient: expect.any(Object),
          }),
        );
      });

      it('passing IClient should create a new form with FormGroup', () => {
        const formGroup = service.createClientFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idClient: expect.any(Object),
            indiceClient: expect.any(Object),
            nomClient: expect.any(Object),
            prenomClient: expect.any(Object),
            raisonSociale: expect.any(Object),
            telephone: expect.any(Object),
            email: expect.any(Object),
            contrat: expect.any(Object),
            typeClient: expect.any(Object),
          }),
        );
      });
    });

    describe('getClient', () => {
      it('should return NewClient for default Client initial value', () => {
        const formGroup = service.createClientFormGroup(sampleWithNewData);

        const client = service.getClient(formGroup) as any;

        expect(client).toMatchObject(sampleWithNewData);
      });

      it('should return NewClient for empty Client initial value', () => {
        const formGroup = service.createClientFormGroup();

        const client = service.getClient(formGroup) as any;

        expect(client).toMatchObject({});
      });

      it('should return IClient', () => {
        const formGroup = service.createClientFormGroup(sampleWithRequiredData);

        const client = service.getClient(formGroup) as any;

        expect(client).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

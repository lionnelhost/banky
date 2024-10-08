import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../type-contrat.test-samples';

import { TypeContratFormService } from './type-contrat-form.service';

describe('TypeContrat Form Service', () => {
  let service: TypeContratFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TypeContratFormService);
  });

  describe('Service methods', () => {
    describe('createTypeContratFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTypeContratFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idTypeContrat: expect.any(Object),
            libelle: expect.any(Object),
          }),
        );
      });

      it('passing ITypeContrat should create a new form with FormGroup', () => {
        const formGroup = service.createTypeContratFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            idTypeContrat: expect.any(Object),
            libelle: expect.any(Object),
          }),
        );
      });
    });

    describe('getTypeContrat', () => {
      it('should return NewTypeContrat for default TypeContrat initial value', () => {
        const formGroup = service.createTypeContratFormGroup(sampleWithNewData);

        const typeContrat = service.getTypeContrat(formGroup) as any;

        expect(typeContrat).toMatchObject(sampleWithNewData);
      });

      it('should return NewTypeContrat for empty TypeContrat initial value', () => {
        const formGroup = service.createTypeContratFormGroup();

        const typeContrat = service.getTypeContrat(formGroup) as any;

        expect(typeContrat).toMatchObject({});
      });

      it('should return ITypeContrat', () => {
        const formGroup = service.createTypeContratFormGroup(sampleWithRequiredData);

        const typeContrat = service.getTypeContrat(formGroup) as any;

        expect(typeContrat).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});

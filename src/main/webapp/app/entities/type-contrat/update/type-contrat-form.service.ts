import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ITypeContrat, NewTypeContrat } from '../type-contrat.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idTypeContrat: unknown }> = Partial<Omit<T, 'idTypeContrat'>> & {
  idTypeContrat: T['idTypeContrat'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITypeContrat for edit and NewTypeContratFormGroupInput for create.
 */
type TypeContratFormGroupInput = ITypeContrat | PartialWithRequiredKeyOf<NewTypeContrat>;

type TypeContratFormDefaults = Pick<NewTypeContrat, 'idTypeContrat'>;

type TypeContratFormGroupContent = {
  idTypeContrat: FormControl<ITypeContrat['idTypeContrat'] | NewTypeContrat['idTypeContrat']>;
  libelle: FormControl<ITypeContrat['libelle']>;
};

export type TypeContratFormGroup = FormGroup<TypeContratFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TypeContratFormService {
  createTypeContratFormGroup(typeContrat: TypeContratFormGroupInput = { idTypeContrat: null }): TypeContratFormGroup {
    const typeContratRawValue = {
      ...this.getFormDefaults(),
      ...typeContrat,
    };
    return new FormGroup<TypeContratFormGroupContent>({
      idTypeContrat: new FormControl(
        { value: typeContratRawValue.idTypeContrat, disabled: typeContratRawValue.idTypeContrat !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      libelle: new FormControl(typeContratRawValue.libelle),
    });
  }

  getTypeContrat(form: TypeContratFormGroup): ITypeContrat | NewTypeContrat {
    return form.getRawValue() as ITypeContrat | NewTypeContrat;
  }

  resetForm(form: TypeContratFormGroup, typeContrat: TypeContratFormGroupInput): void {
    const typeContratRawValue = { ...this.getFormDefaults(), ...typeContrat };
    form.reset(
      {
        ...typeContratRawValue,
        idTypeContrat: { value: typeContratRawValue.idTypeContrat, disabled: typeContratRawValue.idTypeContrat !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TypeContratFormDefaults {
    return {
      idTypeContrat: null,
    };
  }
}

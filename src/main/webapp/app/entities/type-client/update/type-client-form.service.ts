import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ITypeClient, NewTypeClient } from '../type-client.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idTypeClient: unknown }> = Partial<Omit<T, 'idTypeClient'>> & { idTypeClient: T['idTypeClient'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITypeClient for edit and NewTypeClientFormGroupInput for create.
 */
type TypeClientFormGroupInput = ITypeClient | PartialWithRequiredKeyOf<NewTypeClient>;

type TypeClientFormDefaults = Pick<NewTypeClient, 'idTypeClient'>;

type TypeClientFormGroupContent = {
  idTypeClient: FormControl<ITypeClient['idTypeClient'] | NewTypeClient['idTypeClient']>;
  libelle: FormControl<ITypeClient['libelle']>;
};

export type TypeClientFormGroup = FormGroup<TypeClientFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TypeClientFormService {
  createTypeClientFormGroup(typeClient: TypeClientFormGroupInput = { idTypeClient: null }): TypeClientFormGroup {
    const typeClientRawValue = {
      ...this.getFormDefaults(),
      ...typeClient,
    };
    return new FormGroup<TypeClientFormGroupContent>({
      idTypeClient: new FormControl(
        { value: typeClientRawValue.idTypeClient, disabled: typeClientRawValue.idTypeClient !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      libelle: new FormControl(typeClientRawValue.libelle),
    });
  }

  getTypeClient(form: TypeClientFormGroup): ITypeClient | NewTypeClient {
    return form.getRawValue() as ITypeClient | NewTypeClient;
  }

  resetForm(form: TypeClientFormGroup, typeClient: TypeClientFormGroupInput): void {
    const typeClientRawValue = { ...this.getFormDefaults(), ...typeClient };
    form.reset(
      {
        ...typeClientRawValue,
        idTypeClient: { value: typeClientRawValue.idTypeClient, disabled: typeClientRawValue.idTypeClient !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TypeClientFormDefaults {
    return {
      idTypeClient: null,
    };
  }
}

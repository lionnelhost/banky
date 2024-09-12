import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ITypeTransaction, NewTypeTransaction } from '../type-transaction.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idTypeTransaction: unknown }> = Partial<Omit<T, 'idTypeTransaction'>> & {
  idTypeTransaction: T['idTypeTransaction'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITypeTransaction for edit and NewTypeTransactionFormGroupInput for create.
 */
type TypeTransactionFormGroupInput = ITypeTransaction | PartialWithRequiredKeyOf<NewTypeTransaction>;

type TypeTransactionFormDefaults = Pick<NewTypeTransaction, 'idTypeTransaction'>;

type TypeTransactionFormGroupContent = {
  idTypeTransaction: FormControl<ITypeTransaction['idTypeTransaction'] | NewTypeTransaction['idTypeTransaction']>;
  libelle: FormControl<ITypeTransaction['libelle']>;
};

export type TypeTransactionFormGroup = FormGroup<TypeTransactionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TypeTransactionFormService {
  createTypeTransactionFormGroup(typeTransaction: TypeTransactionFormGroupInput = { idTypeTransaction: null }): TypeTransactionFormGroup {
    const typeTransactionRawValue = {
      ...this.getFormDefaults(),
      ...typeTransaction,
    };
    return new FormGroup<TypeTransactionFormGroupContent>({
      idTypeTransaction: new FormControl(
        { value: typeTransactionRawValue.idTypeTransaction, disabled: typeTransactionRawValue.idTypeTransaction !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      libelle: new FormControl(typeTransactionRawValue.libelle),
    });
  }

  getTypeTransaction(form: TypeTransactionFormGroup): ITypeTransaction | NewTypeTransaction {
    return form.getRawValue() as ITypeTransaction | NewTypeTransaction;
  }

  resetForm(form: TypeTransactionFormGroup, typeTransaction: TypeTransactionFormGroupInput): void {
    const typeTransactionRawValue = { ...this.getFormDefaults(), ...typeTransaction };
    form.reset(
      {
        ...typeTransactionRawValue,
        idTypeTransaction: {
          value: typeTransactionRawValue.idTypeTransaction,
          disabled: typeTransactionRawValue.idTypeTransaction !== null,
        },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TypeTransactionFormDefaults {
    return {
      idTypeTransaction: null,
    };
  }
}

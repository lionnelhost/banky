import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IParametrageGlobal, NewParametrageGlobal } from '../parametrage-global.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idParamGlobal: unknown }> = Partial<Omit<T, 'idParamGlobal'>> & {
  idParamGlobal: T['idParamGlobal'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IParametrageGlobal for edit and NewParametrageGlobalFormGroupInput for create.
 */
type ParametrageGlobalFormGroupInput = IParametrageGlobal | PartialWithRequiredKeyOf<NewParametrageGlobal>;

type ParametrageGlobalFormDefaults = Pick<NewParametrageGlobal, 'idParamGlobal'>;

type ParametrageGlobalFormGroupContent = {
  idParamGlobal: FormControl<IParametrageGlobal['idParamGlobal'] | NewParametrageGlobal['idParamGlobal']>;
  codeParam: FormControl<IParametrageGlobal['codeParam']>;
  typeParam: FormControl<IParametrageGlobal['typeParam']>;
  valeur: FormControl<IParametrageGlobal['valeur']>;
};

export type ParametrageGlobalFormGroup = FormGroup<ParametrageGlobalFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ParametrageGlobalFormService {
  createParametrageGlobalFormGroup(
    parametrageGlobal: ParametrageGlobalFormGroupInput = { idParamGlobal: null },
  ): ParametrageGlobalFormGroup {
    const parametrageGlobalRawValue = {
      ...this.getFormDefaults(),
      ...parametrageGlobal,
    };
    return new FormGroup<ParametrageGlobalFormGroupContent>({
      idParamGlobal: new FormControl(
        { value: parametrageGlobalRawValue.idParamGlobal, disabled: parametrageGlobalRawValue.idParamGlobal !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codeParam: new FormControl(parametrageGlobalRawValue.codeParam),
      typeParam: new FormControl(parametrageGlobalRawValue.typeParam),
      valeur: new FormControl(parametrageGlobalRawValue.valeur),
    });
  }

  getParametrageGlobal(form: ParametrageGlobalFormGroup): IParametrageGlobal | NewParametrageGlobal {
    return form.getRawValue() as IParametrageGlobal | NewParametrageGlobal;
  }

  resetForm(form: ParametrageGlobalFormGroup, parametrageGlobal: ParametrageGlobalFormGroupInput): void {
    const parametrageGlobalRawValue = { ...this.getFormDefaults(), ...parametrageGlobal };
    form.reset(
      {
        ...parametrageGlobalRawValue,
        idParamGlobal: { value: parametrageGlobalRawValue.idParamGlobal, disabled: parametrageGlobalRawValue.idParamGlobal !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ParametrageGlobalFormDefaults {
    return {
      idParamGlobal: null,
    };
  }
}

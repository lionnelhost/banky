import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IVariableNotification, NewVariableNotification } from '../variable-notification.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idVarNotification: unknown }> = Partial<Omit<T, 'idVarNotification'>> & {
  idVarNotification: T['idVarNotification'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVariableNotification for edit and NewVariableNotificationFormGroupInput for create.
 */
type VariableNotificationFormGroupInput = IVariableNotification | PartialWithRequiredKeyOf<NewVariableNotification>;

type VariableNotificationFormDefaults = Pick<NewVariableNotification, 'idVarNotification'>;

type VariableNotificationFormGroupContent = {
  idVarNotification: FormControl<IVariableNotification['idVarNotification'] | NewVariableNotification['idVarNotification']>;
  codeVariable: FormControl<IVariableNotification['codeVariable']>;
  description: FormControl<IVariableNotification['description']>;
};

export type VariableNotificationFormGroup = FormGroup<VariableNotificationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VariableNotificationFormService {
  createVariableNotificationFormGroup(
    variableNotification: VariableNotificationFormGroupInput = { idVarNotification: null },
  ): VariableNotificationFormGroup {
    const variableNotificationRawValue = {
      ...this.getFormDefaults(),
      ...variableNotification,
    };
    return new FormGroup<VariableNotificationFormGroupContent>({
      idVarNotification: new FormControl(
        { value: variableNotificationRawValue.idVarNotification, disabled: variableNotificationRawValue.idVarNotification !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codeVariable: new FormControl(variableNotificationRawValue.codeVariable),
      description: new FormControl(variableNotificationRawValue.description),
    });
  }

  getVariableNotification(form: VariableNotificationFormGroup): IVariableNotification | NewVariableNotification {
    return form.getRawValue() as IVariableNotification | NewVariableNotification;
  }

  resetForm(form: VariableNotificationFormGroup, variableNotification: VariableNotificationFormGroupInput): void {
    const variableNotificationRawValue = { ...this.getFormDefaults(), ...variableNotification };
    form.reset(
      {
        ...variableNotificationRawValue,
        idVarNotification: {
          value: variableNotificationRawValue.idVarNotification,
          disabled: variableNotificationRawValue.idVarNotification !== null,
        },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): VariableNotificationFormDefaults {
    return {
      idVarNotification: null,
    };
  }
}

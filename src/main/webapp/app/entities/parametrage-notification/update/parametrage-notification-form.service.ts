import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IParametrageNotification, NewParametrageNotification } from '../parametrage-notification.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idParamNotif: unknown }> = Partial<Omit<T, 'idParamNotif'>> & { idParamNotif: T['idParamNotif'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IParametrageNotification for edit and NewParametrageNotificationFormGroupInput for create.
 */
type ParametrageNotificationFormGroupInput = IParametrageNotification | PartialWithRequiredKeyOf<NewParametrageNotification>;

type ParametrageNotificationFormDefaults = Pick<NewParametrageNotification, 'idParamNotif'>;

type ParametrageNotificationFormGroupContent = {
  idParamNotif: FormControl<IParametrageNotification['idParamNotif'] | NewParametrageNotification['idParamNotif']>;
  objetNotif: FormControl<IParametrageNotification['objetNotif']>;
  typeNotif: FormControl<IParametrageNotification['typeNotif']>;
  contenu: FormControl<IParametrageNotification['contenu']>;
};

export type ParametrageNotificationFormGroup = FormGroup<ParametrageNotificationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ParametrageNotificationFormService {
  createParametrageNotificationFormGroup(
    parametrageNotification: ParametrageNotificationFormGroupInput = { idParamNotif: null },
  ): ParametrageNotificationFormGroup {
    const parametrageNotificationRawValue = {
      ...this.getFormDefaults(),
      ...parametrageNotification,
    };
    return new FormGroup<ParametrageNotificationFormGroupContent>({
      idParamNotif: new FormControl(
        { value: parametrageNotificationRawValue.idParamNotif, disabled: parametrageNotificationRawValue.idParamNotif !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      objetNotif: new FormControl(parametrageNotificationRawValue.objetNotif),
      typeNotif: new FormControl(parametrageNotificationRawValue.typeNotif),
      contenu: new FormControl(parametrageNotificationRawValue.contenu),
    });
  }

  getParametrageNotification(form: ParametrageNotificationFormGroup): IParametrageNotification | NewParametrageNotification {
    return form.getRawValue() as IParametrageNotification | NewParametrageNotification;
  }

  resetForm(form: ParametrageNotificationFormGroup, parametrageNotification: ParametrageNotificationFormGroupInput): void {
    const parametrageNotificationRawValue = { ...this.getFormDefaults(), ...parametrageNotification };
    form.reset(
      {
        ...parametrageNotificationRawValue,
        idParamNotif: {
          value: parametrageNotificationRawValue.idParamNotif,
          disabled: parametrageNotificationRawValue.idParamNotif !== null,
        },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ParametrageNotificationFormDefaults {
    return {
      idParamNotif: null,
    };
  }
}

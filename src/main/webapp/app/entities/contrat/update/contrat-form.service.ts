import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IContrat, NewContrat } from '../contrat.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idContrat: unknown }> = Partial<Omit<T, 'idContrat'>> & { idContrat: T['idContrat'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContrat for edit and NewContratFormGroupInput for create.
 */
type ContratFormGroupInput = IContrat | PartialWithRequiredKeyOf<NewContrat>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IContrat | NewContrat> = Omit<T, 'dateValidite'> & {
  dateValidite?: string | null;
};

type ContratFormRawValue = FormValueOf<IContrat>;

type NewContratFormRawValue = FormValueOf<NewContrat>;

type ContratFormDefaults = Pick<NewContrat, 'idContrat' | 'dateValidite'>;

type ContratFormGroupContent = {
  idContrat: FormControl<ContratFormRawValue['idContrat'] | NewContrat['idContrat']>;
  dateValidite: FormControl<ContratFormRawValue['dateValidite']>;
  typeContrat: FormControl<ContratFormRawValue['typeContrat']>;
};

export type ContratFormGroup = FormGroup<ContratFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContratFormService {
  createContratFormGroup(contrat: ContratFormGroupInput = { idContrat: null }): ContratFormGroup {
    const contratRawValue = this.convertContratToContratRawValue({
      ...this.getFormDefaults(),
      ...contrat,
    });
    return new FormGroup<ContratFormGroupContent>({
      idContrat: new FormControl(
        { value: contratRawValue.idContrat, disabled: contratRawValue.idContrat !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dateValidite: new FormControl(contratRawValue.dateValidite),
      typeContrat: new FormControl(contratRawValue.typeContrat),
    });
  }

  getContrat(form: ContratFormGroup): IContrat | NewContrat {
    return this.convertContratRawValueToContrat(form.getRawValue() as ContratFormRawValue | NewContratFormRawValue);
  }

  resetForm(form: ContratFormGroup, contrat: ContratFormGroupInput): void {
    const contratRawValue = this.convertContratToContratRawValue({ ...this.getFormDefaults(), ...contrat });
    form.reset(
      {
        ...contratRawValue,
        idContrat: { value: contratRawValue.idContrat, disabled: contratRawValue.idContrat !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContratFormDefaults {
    const currentTime = dayjs();

    return {
      idContrat: null,
      dateValidite: currentTime,
    };
  }

  private convertContratRawValueToContrat(rawContrat: ContratFormRawValue | NewContratFormRawValue): IContrat | NewContrat {
    return {
      ...rawContrat,
      dateValidite: dayjs(rawContrat.dateValidite, DATE_TIME_FORMAT),
    };
  }

  private convertContratToContratRawValue(
    contrat: IContrat | (Partial<NewContrat> & ContratFormDefaults),
  ): ContratFormRawValue | PartialWithRequiredKeyOf<NewContratFormRawValue> {
    return {
      ...contrat,
      dateValidite: contrat.dateValidite ? contrat.dateValidite.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}

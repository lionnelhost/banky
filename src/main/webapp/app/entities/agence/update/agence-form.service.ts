import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IAgence, NewAgence } from '../agence.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idAgence: unknown }> = Partial<Omit<T, 'idAgence'>> & { idAgence: T['idAgence'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAgence for edit and NewAgenceFormGroupInput for create.
 */
type AgenceFormGroupInput = IAgence | PartialWithRequiredKeyOf<NewAgence>;

type AgenceFormDefaults = Pick<NewAgence, 'idAgence'>;

type AgenceFormGroupContent = {
  idAgence: FormControl<IAgence['idAgence'] | NewAgence['idAgence']>;
  codeAgence: FormControl<IAgence['codeAgence']>;
  nomAgence: FormControl<IAgence['nomAgence']>;
  banque: FormControl<IAgence['banque']>;
};

export type AgenceFormGroup = FormGroup<AgenceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AgenceFormService {
  createAgenceFormGroup(agence: AgenceFormGroupInput = { idAgence: null }): AgenceFormGroup {
    const agenceRawValue = {
      ...this.getFormDefaults(),
      ...agence,
    };
    return new FormGroup<AgenceFormGroupContent>({
      idAgence: new FormControl(
        { value: agenceRawValue.idAgence, disabled: agenceRawValue.idAgence !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codeAgence: new FormControl(agenceRawValue.codeAgence),
      nomAgence: new FormControl(agenceRawValue.nomAgence),
      banque: new FormControl(agenceRawValue.banque),
    });
  }

  getAgence(form: AgenceFormGroup): IAgence | NewAgence {
    return form.getRawValue() as IAgence | NewAgence;
  }

  resetForm(form: AgenceFormGroup, agence: AgenceFormGroupInput): void {
    const agenceRawValue = { ...this.getFormDefaults(), ...agence };
    form.reset(
      {
        ...agenceRawValue,
        idAgence: { value: agenceRawValue.idAgence, disabled: agenceRawValue.idAgence !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AgenceFormDefaults {
    return {
      idAgence: null,
    };
  }
}

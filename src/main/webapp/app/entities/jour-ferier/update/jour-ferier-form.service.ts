import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IJourFerier, NewJourFerier } from '../jour-ferier.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idJourFerie: unknown }> = Partial<Omit<T, 'idJourFerie'>> & { idJourFerie: T['idJourFerie'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IJourFerier for edit and NewJourFerierFormGroupInput for create.
 */
type JourFerierFormGroupInput = IJourFerier | PartialWithRequiredKeyOf<NewJourFerier>;

type JourFerierFormDefaults = Pick<NewJourFerier, 'idJourFerie'>;

type JourFerierFormGroupContent = {
  idJourFerie: FormControl<IJourFerier['idJourFerie'] | NewJourFerier['idJourFerie']>;
  libelle: FormControl<IJourFerier['libelle']>;
};

export type JourFerierFormGroup = FormGroup<JourFerierFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class JourFerierFormService {
  createJourFerierFormGroup(jourFerier: JourFerierFormGroupInput = { idJourFerie: null }): JourFerierFormGroup {
    const jourFerierRawValue = {
      ...this.getFormDefaults(),
      ...jourFerier,
    };
    return new FormGroup<JourFerierFormGroupContent>({
      idJourFerie: new FormControl(
        { value: jourFerierRawValue.idJourFerie, disabled: jourFerierRawValue.idJourFerie !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      libelle: new FormControl(jourFerierRawValue.libelle),
    });
  }

  getJourFerier(form: JourFerierFormGroup): IJourFerier | NewJourFerier {
    return form.getRawValue() as IJourFerier | NewJourFerier;
  }

  resetForm(form: JourFerierFormGroup, jourFerier: JourFerierFormGroupInput): void {
    const jourFerierRawValue = { ...this.getFormDefaults(), ...jourFerier };
    form.reset(
      {
        ...jourFerierRawValue,
        idJourFerie: { value: jourFerierRawValue.idJourFerie, disabled: jourFerierRawValue.idJourFerie !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): JourFerierFormDefaults {
    return {
      idJourFerie: null,
    };
  }
}

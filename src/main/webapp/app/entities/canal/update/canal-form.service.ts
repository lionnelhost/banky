import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICanal, NewCanal } from '../canal.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idCanal: unknown }> = Partial<Omit<T, 'idCanal'>> & { idCanal: T['idCanal'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICanal for edit and NewCanalFormGroupInput for create.
 */
type CanalFormGroupInput = ICanal | PartialWithRequiredKeyOf<NewCanal>;

type CanalFormDefaults = Pick<NewCanal, 'idCanal'>;

type CanalFormGroupContent = {
  idCanal: FormControl<ICanal['idCanal'] | NewCanal['idCanal']>;
  libelle: FormControl<ICanal['libelle']>;
};

export type CanalFormGroup = FormGroup<CanalFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CanalFormService {
  createCanalFormGroup(canal: CanalFormGroupInput = { idCanal: null }): CanalFormGroup {
    const canalRawValue = {
      ...this.getFormDefaults(),
      ...canal,
    };
    return new FormGroup<CanalFormGroupContent>({
      idCanal: new FormControl(
        { value: canalRawValue.idCanal, disabled: canalRawValue.idCanal !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      libelle: new FormControl(canalRawValue.libelle),
    });
  }

  getCanal(form: CanalFormGroup): ICanal | NewCanal {
    return form.getRawValue() as ICanal | NewCanal;
  }

  resetForm(form: CanalFormGroup, canal: CanalFormGroupInput): void {
    const canalRawValue = { ...this.getFormDefaults(), ...canal };
    form.reset(
      {
        ...canalRawValue,
        idCanal: { value: canalRawValue.idCanal, disabled: canalRawValue.idCanal !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CanalFormDefaults {
    return {
      idCanal: null,
    };
  }
}

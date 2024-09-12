import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IDispositifSercurite, NewDispositifSercurite } from '../dispositif-sercurite.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDispositifSercurite for edit and NewDispositifSercuriteFormGroupInput for create.
 */
type DispositifSercuriteFormGroupInput = IDispositifSercurite | PartialWithRequiredKeyOf<NewDispositifSercurite>;

type DispositifSercuriteFormDefaults = Pick<NewDispositifSercurite, 'id'>;

type DispositifSercuriteFormGroupContent = {
  id: FormControl<IDispositifSercurite['id'] | NewDispositifSercurite['id']>;
  idCanal: FormControl<IDispositifSercurite['idCanal']>;
  idTypeTransaction: FormControl<IDispositifSercurite['idTypeTransaction']>;
  idDispositif: FormControl<IDispositifSercurite['idDispositif']>;
  canal: FormControl<IDispositifSercurite['canal']>;
  typeTransaction: FormControl<IDispositifSercurite['typeTransaction']>;
  dispositifSignature: FormControl<IDispositifSercurite['dispositifSignature']>;
};

export type DispositifSercuriteFormGroup = FormGroup<DispositifSercuriteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DispositifSercuriteFormService {
  createDispositifSercuriteFormGroup(dispositifSercurite: DispositifSercuriteFormGroupInput = { id: null }): DispositifSercuriteFormGroup {
    const dispositifSercuriteRawValue = {
      ...this.getFormDefaults(),
      ...dispositifSercurite,
    };
    return new FormGroup<DispositifSercuriteFormGroupContent>({
      id: new FormControl(
        { value: dispositifSercuriteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      idCanal: new FormControl(dispositifSercuriteRawValue.idCanal),
      idTypeTransaction: new FormControl(dispositifSercuriteRawValue.idTypeTransaction),
      idDispositif: new FormControl(dispositifSercuriteRawValue.idDispositif),
      canal: new FormControl(dispositifSercuriteRawValue.canal),
      typeTransaction: new FormControl(dispositifSercuriteRawValue.typeTransaction),
      dispositifSignature: new FormControl(dispositifSercuriteRawValue.dispositifSignature),
    });
  }

  getDispositifSercurite(form: DispositifSercuriteFormGroup): IDispositifSercurite | NewDispositifSercurite {
    return form.getRawValue() as IDispositifSercurite | NewDispositifSercurite;
  }

  resetForm(form: DispositifSercuriteFormGroup, dispositifSercurite: DispositifSercuriteFormGroupInput): void {
    const dispositifSercuriteRawValue = { ...this.getFormDefaults(), ...dispositifSercurite };
    form.reset(
      {
        ...dispositifSercuriteRawValue,
        id: { value: dispositifSercuriteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DispositifSercuriteFormDefaults {
    return {
      id: null,
    };
  }
}

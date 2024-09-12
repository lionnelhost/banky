import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IDispositifSignature, NewDispositifSignature } from '../dispositif-signature.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idDispositif: unknown }> = Partial<Omit<T, 'idDispositif'>> & { idDispositif: T['idDispositif'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDispositifSignature for edit and NewDispositifSignatureFormGroupInput for create.
 */
type DispositifSignatureFormGroupInput = IDispositifSignature | PartialWithRequiredKeyOf<NewDispositifSignature>;

type DispositifSignatureFormDefaults = Pick<NewDispositifSignature, 'idDispositif'>;

type DispositifSignatureFormGroupContent = {
  idDispositif: FormControl<IDispositifSignature['idDispositif'] | NewDispositifSignature['idDispositif']>;
  libelle: FormControl<IDispositifSignature['libelle']>;
};

export type DispositifSignatureFormGroup = FormGroup<DispositifSignatureFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DispositifSignatureFormService {
  createDispositifSignatureFormGroup(
    dispositifSignature: DispositifSignatureFormGroupInput = { idDispositif: null },
  ): DispositifSignatureFormGroup {
    const dispositifSignatureRawValue = {
      ...this.getFormDefaults(),
      ...dispositifSignature,
    };
    return new FormGroup<DispositifSignatureFormGroupContent>({
      idDispositif: new FormControl(
        { value: dispositifSignatureRawValue.idDispositif, disabled: dispositifSignatureRawValue.idDispositif !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      libelle: new FormControl(dispositifSignatureRawValue.libelle),
    });
  }

  getDispositifSignature(form: DispositifSignatureFormGroup): IDispositifSignature | NewDispositifSignature {
    return form.getRawValue() as IDispositifSignature | NewDispositifSignature;
  }

  resetForm(form: DispositifSignatureFormGroup, dispositifSignature: DispositifSignatureFormGroupInput): void {
    const dispositifSignatureRawValue = { ...this.getFormDefaults(), ...dispositifSignature };
    form.reset(
      {
        ...dispositifSignatureRawValue,
        idDispositif: { value: dispositifSignatureRawValue.idDispositif, disabled: dispositifSignatureRawValue.idDispositif !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DispositifSignatureFormDefaults {
    return {
      idDispositif: null,
    };
  }
}

import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICompteBancaire, NewCompteBancaire } from '../compte-bancaire.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idCompteBancaire: unknown }> = Partial<Omit<T, 'idCompteBancaire'>> & {
  idCompteBancaire: T['idCompteBancaire'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICompteBancaire for edit and NewCompteBancaireFormGroupInput for create.
 */
type CompteBancaireFormGroupInput = ICompteBancaire | PartialWithRequiredKeyOf<NewCompteBancaire>;

type CompteBancaireFormDefaults = Pick<NewCompteBancaire, 'idCompteBancaire'>;

type CompteBancaireFormGroupContent = {
  idCompteBancaire: FormControl<ICompteBancaire['idCompteBancaire'] | NewCompteBancaire['idCompteBancaire']>;
  age: FormControl<ICompteBancaire['age']>;
  ncp: FormControl<ICompteBancaire['ncp']>;
  sde: FormControl<ICompteBancaire['sde']>;
  sin: FormControl<ICompteBancaire['sin']>;
  soldeDisponible: FormControl<ICompteBancaire['soldeDisponible']>;
  rib: FormControl<ICompteBancaire['rib']>;
  status: FormControl<ICompteBancaire['status']>;
  contrat: FormControl<ICompteBancaire['contrat']>;
};

export type CompteBancaireFormGroup = FormGroup<CompteBancaireFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CompteBancaireFormService {
  createCompteBancaireFormGroup(compteBancaire: CompteBancaireFormGroupInput = { idCompteBancaire: null }): CompteBancaireFormGroup {
    const compteBancaireRawValue = {
      ...this.getFormDefaults(),
      ...compteBancaire,
    };
    return new FormGroup<CompteBancaireFormGroupContent>({
      idCompteBancaire: new FormControl(
        { value: compteBancaireRawValue.idCompteBancaire, disabled: compteBancaireRawValue.idCompteBancaire !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      age: new FormControl(compteBancaireRawValue.age),
      ncp: new FormControl(compteBancaireRawValue.ncp),
      sde: new FormControl(compteBancaireRawValue.sde),
      sin: new FormControl(compteBancaireRawValue.sin),
      soldeDisponible: new FormControl(compteBancaireRawValue.soldeDisponible),
      rib: new FormControl(compteBancaireRawValue.rib),
      status: new FormControl(compteBancaireRawValue.status),
      contrat: new FormControl(compteBancaireRawValue.contrat),
    });
  }

  getCompteBancaire(form: CompteBancaireFormGroup): ICompteBancaire | NewCompteBancaire {
    return form.getRawValue() as ICompteBancaire | NewCompteBancaire;
  }

  resetForm(form: CompteBancaireFormGroup, compteBancaire: CompteBancaireFormGroupInput): void {
    const compteBancaireRawValue = { ...this.getFormDefaults(), ...compteBancaire };
    form.reset(
      {
        ...compteBancaireRawValue,
        idCompteBancaire: { value: compteBancaireRawValue.idCompteBancaire, disabled: compteBancaireRawValue.idCompteBancaire !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CompteBancaireFormDefaults {
    return {
      idCompteBancaire: null,
    };
  }
}

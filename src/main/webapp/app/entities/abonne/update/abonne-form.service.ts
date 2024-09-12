import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IAbonne, NewAbonne } from '../abonne.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idAbonne: unknown }> = Partial<Omit<T, 'idAbonne'>> & { idAbonne: T['idAbonne'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAbonne for edit and NewAbonneFormGroupInput for create.
 */
type AbonneFormGroupInput = IAbonne | PartialWithRequiredKeyOf<NewAbonne>;

type AbonneFormDefaults = Pick<NewAbonne, 'idAbonne'>;

type AbonneFormGroupContent = {
  idAbonne: FormControl<IAbonne['idAbonne'] | NewAbonne['idAbonne']>;
  indiceClient: FormControl<IAbonne['indiceClient']>;
  nomAbonne: FormControl<IAbonne['nomAbonne']>;
  prenomAbonne: FormControl<IAbonne['prenomAbonne']>;
  telephone: FormControl<IAbonne['telephone']>;
  email: FormControl<IAbonne['email']>;
  typePieceIdentite: FormControl<IAbonne['typePieceIdentite']>;
  numPieceIdentite: FormControl<IAbonne['numPieceIdentite']>;
  statut: FormControl<IAbonne['statut']>;
  identifiant: FormControl<IAbonne['identifiant']>;
};

export type AbonneFormGroup = FormGroup<AbonneFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AbonneFormService {
  createAbonneFormGroup(abonne: AbonneFormGroupInput = { idAbonne: null }): AbonneFormGroup {
    const abonneRawValue = {
      ...this.getFormDefaults(),
      ...abonne,
    };
    return new FormGroup<AbonneFormGroupContent>({
      idAbonne: new FormControl(
        { value: abonneRawValue.idAbonne, disabled: abonneRawValue.idAbonne !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      indiceClient: new FormControl(abonneRawValue.indiceClient),
      nomAbonne: new FormControl(abonneRawValue.nomAbonne),
      prenomAbonne: new FormControl(abonneRawValue.prenomAbonne),
      telephone: new FormControl(abonneRawValue.telephone),
      email: new FormControl(abonneRawValue.email),
      typePieceIdentite: new FormControl(abonneRawValue.typePieceIdentite),
      numPieceIdentite: new FormControl(abonneRawValue.numPieceIdentite),
      statut: new FormControl(abonneRawValue.statut),
      identifiant: new FormControl(abonneRawValue.identifiant),
    });
  }

  getAbonne(form: AbonneFormGroup): IAbonne | NewAbonne {
    return form.getRawValue() as IAbonne | NewAbonne;
  }

  resetForm(form: AbonneFormGroup, abonne: AbonneFormGroupInput): void {
    const abonneRawValue = { ...this.getFormDefaults(), ...abonne };
    form.reset(
      {
        ...abonneRawValue,
        idAbonne: { value: abonneRawValue.idAbonne, disabled: abonneRawValue.idAbonne !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AbonneFormDefaults {
    return {
      idAbonne: null,
    };
  }
}

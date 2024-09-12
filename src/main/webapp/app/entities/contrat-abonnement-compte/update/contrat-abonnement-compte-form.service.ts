import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IContratAbonnementCompte, NewContratAbonnementCompte } from '../contrat-abonnement-compte.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContratAbonnementCompte for edit and NewContratAbonnementCompteFormGroupInput for create.
 */
type ContratAbonnementCompteFormGroupInput = IContratAbonnementCompte | PartialWithRequiredKeyOf<NewContratAbonnementCompte>;

type ContratAbonnementCompteFormDefaults = Pick<NewContratAbonnementCompte, 'id'>;

type ContratAbonnementCompteFormGroupContent = {
  id: FormControl<IContratAbonnementCompte['id'] | NewContratAbonnementCompte['id']>;
  idContrat: FormControl<IContratAbonnementCompte['idContrat']>;
  idAbonne: FormControl<IContratAbonnementCompte['idAbonne']>;
  idCompteBancaire: FormControl<IContratAbonnementCompte['idCompteBancaire']>;
  contrat: FormControl<IContratAbonnementCompte['contrat']>;
  abonne: FormControl<IContratAbonnementCompte['abonne']>;
  compteBancaire: FormControl<IContratAbonnementCompte['compteBancaire']>;
};

export type ContratAbonnementCompteFormGroup = FormGroup<ContratAbonnementCompteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContratAbonnementCompteFormService {
  createContratAbonnementCompteFormGroup(
    contratAbonnementCompte: ContratAbonnementCompteFormGroupInput = { id: null },
  ): ContratAbonnementCompteFormGroup {
    const contratAbonnementCompteRawValue = {
      ...this.getFormDefaults(),
      ...contratAbonnementCompte,
    };
    return new FormGroup<ContratAbonnementCompteFormGroupContent>({
      id: new FormControl(
        { value: contratAbonnementCompteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      idContrat: new FormControl(contratAbonnementCompteRawValue.idContrat),
      idAbonne: new FormControl(contratAbonnementCompteRawValue.idAbonne),
      idCompteBancaire: new FormControl(contratAbonnementCompteRawValue.idCompteBancaire),
      contrat: new FormControl(contratAbonnementCompteRawValue.contrat),
      abonne: new FormControl(contratAbonnementCompteRawValue.abonne),
      compteBancaire: new FormControl(contratAbonnementCompteRawValue.compteBancaire),
    });
  }

  getContratAbonnementCompte(form: ContratAbonnementCompteFormGroup): IContratAbonnementCompte | NewContratAbonnementCompte {
    return form.getRawValue() as IContratAbonnementCompte | NewContratAbonnementCompte;
  }

  resetForm(form: ContratAbonnementCompteFormGroup, contratAbonnementCompte: ContratAbonnementCompteFormGroupInput): void {
    const contratAbonnementCompteRawValue = { ...this.getFormDefaults(), ...contratAbonnementCompte };
    form.reset(
      {
        ...contratAbonnementCompteRawValue,
        id: { value: contratAbonnementCompteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContratAbonnementCompteFormDefaults {
    return {
      id: null,
    };
  }
}

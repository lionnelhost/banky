import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IContratAbonnement, NewContratAbonnement } from '../contrat-abonnement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContratAbonnement for edit and NewContratAbonnementFormGroupInput for create.
 */
type ContratAbonnementFormGroupInput = IContratAbonnement | PartialWithRequiredKeyOf<NewContratAbonnement>;

type ContratAbonnementFormDefaults = Pick<NewContratAbonnement, 'id'>;

type ContratAbonnementFormGroupContent = {
  id: FormControl<IContratAbonnement['id'] | NewContratAbonnement['id']>;
  idContrat: FormControl<IContratAbonnement['idContrat']>;
  idAbonne: FormControl<IContratAbonnement['idAbonne']>;
  contrat: FormControl<IContratAbonnement['contrat']>;
  abonne: FormControl<IContratAbonnement['abonne']>;
};

export type ContratAbonnementFormGroup = FormGroup<ContratAbonnementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContratAbonnementFormService {
  createContratAbonnementFormGroup(contratAbonnement: ContratAbonnementFormGroupInput = { id: null }): ContratAbonnementFormGroup {
    const contratAbonnementRawValue = {
      ...this.getFormDefaults(),
      ...contratAbonnement,
    };
    return new FormGroup<ContratAbonnementFormGroupContent>({
      id: new FormControl(
        { value: contratAbonnementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      idContrat: new FormControl(contratAbonnementRawValue.idContrat),
      idAbonne: new FormControl(contratAbonnementRawValue.idAbonne),
      contrat: new FormControl(contratAbonnementRawValue.contrat),
      abonne: new FormControl(contratAbonnementRawValue.abonne),
    });
  }

  getContratAbonnement(form: ContratAbonnementFormGroup): IContratAbonnement | NewContratAbonnement {
    return form.getRawValue() as IContratAbonnement | NewContratAbonnement;
  }

  resetForm(form: ContratAbonnementFormGroup, contratAbonnement: ContratAbonnementFormGroupInput): void {
    const contratAbonnementRawValue = { ...this.getFormDefaults(), ...contratAbonnement };
    form.reset(
      {
        ...contratAbonnementRawValue,
        id: { value: contratAbonnementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContratAbonnementFormDefaults {
    return {
      id: null,
    };
  }
}

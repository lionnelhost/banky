import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IClient, NewClient } from '../client.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idClient: unknown }> = Partial<Omit<T, 'idClient'>> & { idClient: T['idClient'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IClient for edit and NewClientFormGroupInput for create.
 */
type ClientFormGroupInput = IClient | PartialWithRequiredKeyOf<NewClient>;

type ClientFormDefaults = Pick<NewClient, 'idClient'>;

type ClientFormGroupContent = {
  idClient: FormControl<IClient['idClient'] | NewClient['idClient']>;
  indiceClient: FormControl<IClient['indiceClient']>;
  nomClient: FormControl<IClient['nomClient']>;
  prenomClient: FormControl<IClient['prenomClient']>;
  raisonSociale: FormControl<IClient['raisonSociale']>;
  telephone: FormControl<IClient['telephone']>;
  email: FormControl<IClient['email']>;
  contrat: FormControl<IClient['contrat']>;
  typeClient: FormControl<IClient['typeClient']>;
};

export type ClientFormGroup = FormGroup<ClientFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ClientFormService {
  createClientFormGroup(client: ClientFormGroupInput = { idClient: null }): ClientFormGroup {
    const clientRawValue = {
      ...this.getFormDefaults(),
      ...client,
    };
    return new FormGroup<ClientFormGroupContent>({
      idClient: new FormControl(
        { value: clientRawValue.idClient, disabled: clientRawValue.idClient !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      indiceClient: new FormControl(clientRawValue.indiceClient),
      nomClient: new FormControl(clientRawValue.nomClient),
      prenomClient: new FormControl(clientRawValue.prenomClient),
      raisonSociale: new FormControl(clientRawValue.raisonSociale),
      telephone: new FormControl(clientRawValue.telephone),
      email: new FormControl(clientRawValue.email),
      contrat: new FormControl(clientRawValue.contrat),
      typeClient: new FormControl(clientRawValue.typeClient),
    });
  }

  getClient(form: ClientFormGroup): IClient | NewClient {
    return form.getRawValue() as IClient | NewClient;
  }

  resetForm(form: ClientFormGroup, client: ClientFormGroupInput): void {
    const clientRawValue = { ...this.getFormDefaults(), ...client };
    form.reset(
      {
        ...clientRawValue,
        idClient: { value: clientRawValue.idClient, disabled: clientRawValue.idClient !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ClientFormDefaults {
    return {
      idClient: null,
    };
  }
}

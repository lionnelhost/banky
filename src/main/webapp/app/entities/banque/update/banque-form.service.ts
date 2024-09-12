import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IBanque, NewBanque } from '../banque.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idBanque: unknown }> = Partial<Omit<T, 'idBanque'>> & { idBanque: T['idBanque'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBanque for edit and NewBanqueFormGroupInput for create.
 */
type BanqueFormGroupInput = IBanque | PartialWithRequiredKeyOf<NewBanque>;

type BanqueFormDefaults = Pick<NewBanque, 'idBanque'>;

type BanqueFormGroupContent = {
  idBanque: FormControl<IBanque['idBanque'] | NewBanque['idBanque']>;
  code: FormControl<IBanque['code']>;
  devise: FormControl<IBanque['devise']>;
  langue: FormControl<IBanque['langue']>;
  logo: FormControl<IBanque['logo']>;
  codeSwift: FormControl<IBanque['codeSwift']>;
  codeIban: FormControl<IBanque['codeIban']>;
  codePays: FormControl<IBanque['codePays']>;
  fuseauHoraire: FormControl<IBanque['fuseauHoraire']>;
  cutOffTime: FormControl<IBanque['cutOffTime']>;
  codeParticipant: FormControl<IBanque['codeParticipant']>;
  libelleParticipant: FormControl<IBanque['libelleParticipant']>;
};

export type BanqueFormGroup = FormGroup<BanqueFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BanqueFormService {
  createBanqueFormGroup(banque: BanqueFormGroupInput = { idBanque: null }): BanqueFormGroup {
    const banqueRawValue = {
      ...this.getFormDefaults(),
      ...banque,
    };
    return new FormGroup<BanqueFormGroupContent>({
      idBanque: new FormControl(
        { value: banqueRawValue.idBanque, disabled: banqueRawValue.idBanque !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(banqueRawValue.code),
      devise: new FormControl(banqueRawValue.devise),
      langue: new FormControl(banqueRawValue.langue),
      logo: new FormControl(banqueRawValue.logo),
      codeSwift: new FormControl(banqueRawValue.codeSwift),
      codeIban: new FormControl(banqueRawValue.codeIban),
      codePays: new FormControl(banqueRawValue.codePays),
      fuseauHoraire: new FormControl(banqueRawValue.fuseauHoraire),
      cutOffTime: new FormControl(banqueRawValue.cutOffTime),
      codeParticipant: new FormControl(banqueRawValue.codeParticipant),
      libelleParticipant: new FormControl(banqueRawValue.libelleParticipant),
    });
  }

  getBanque(form: BanqueFormGroup): IBanque | NewBanque {
    return form.getRawValue() as IBanque | NewBanque;
  }

  resetForm(form: BanqueFormGroup, banque: BanqueFormGroupInput): void {
    const banqueRawValue = { ...this.getFormDefaults(), ...banque };
    form.reset(
      {
        ...banqueRawValue,
        idBanque: { value: banqueRawValue.idBanque, disabled: banqueRawValue.idBanque !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): BanqueFormDefaults {
    return {
      idBanque: null,
    };
  }
}

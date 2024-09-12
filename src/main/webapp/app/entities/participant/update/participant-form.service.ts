import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IParticipant, NewParticipant } from '../participant.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idParticipant: unknown }> = Partial<Omit<T, 'idParticipant'>> & {
  idParticipant: T['idParticipant'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IParticipant for edit and NewParticipantFormGroupInput for create.
 */
type ParticipantFormGroupInput = IParticipant | PartialWithRequiredKeyOf<NewParticipant>;

type ParticipantFormDefaults = Pick<NewParticipant, 'idParticipant' | 'isActif'>;

type ParticipantFormGroupContent = {
  idParticipant: FormControl<IParticipant['idParticipant'] | NewParticipant['idParticipant']>;
  codeParticipant: FormControl<IParticipant['codeParticipant']>;
  codeBanque: FormControl<IParticipant['codeBanque']>;
  nomBanque: FormControl<IParticipant['nomBanque']>;
  libelle: FormControl<IParticipant['libelle']>;
  pays: FormControl<IParticipant['pays']>;
  isActif: FormControl<IParticipant['isActif']>;
};

export type ParticipantFormGroup = FormGroup<ParticipantFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ParticipantFormService {
  createParticipantFormGroup(participant: ParticipantFormGroupInput = { idParticipant: null }): ParticipantFormGroup {
    const participantRawValue = {
      ...this.getFormDefaults(),
      ...participant,
    };
    return new FormGroup<ParticipantFormGroupContent>({
      idParticipant: new FormControl(
        { value: participantRawValue.idParticipant, disabled: participantRawValue.idParticipant !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codeParticipant: new FormControl(participantRawValue.codeParticipant),
      codeBanque: new FormControl(participantRawValue.codeBanque),
      nomBanque: new FormControl(participantRawValue.nomBanque),
      libelle: new FormControl(participantRawValue.libelle),
      pays: new FormControl(participantRawValue.pays),
      isActif: new FormControl(participantRawValue.isActif),
    });
  }

  getParticipant(form: ParticipantFormGroup): IParticipant | NewParticipant {
    return form.getRawValue() as IParticipant | NewParticipant;
  }

  resetForm(form: ParticipantFormGroup, participant: ParticipantFormGroupInput): void {
    const participantRawValue = { ...this.getFormDefaults(), ...participant };
    form.reset(
      {
        ...participantRawValue,
        idParticipant: { value: participantRawValue.idParticipant, disabled: participantRawValue.idParticipant !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ParticipantFormDefaults {
    return {
      idParticipant: null,
      isActif: false,
    };
  }
}

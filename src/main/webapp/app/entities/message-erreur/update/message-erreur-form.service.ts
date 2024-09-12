import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IMessageErreur, NewMessageErreur } from '../message-erreur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idMessageErreur: unknown }> = Partial<Omit<T, 'idMessageErreur'>> & {
  idMessageErreur: T['idMessageErreur'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMessageErreur for edit and NewMessageErreurFormGroupInput for create.
 */
type MessageErreurFormGroupInput = IMessageErreur | PartialWithRequiredKeyOf<NewMessageErreur>;

type MessageErreurFormDefaults = Pick<NewMessageErreur, 'idMessageErreur'>;

type MessageErreurFormGroupContent = {
  idMessageErreur: FormControl<IMessageErreur['idMessageErreur'] | NewMessageErreur['idMessageErreur']>;
  codeErreur: FormControl<IMessageErreur['codeErreur']>;
  description: FormControl<IMessageErreur['description']>;
};

export type MessageErreurFormGroup = FormGroup<MessageErreurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MessageErreurFormService {
  createMessageErreurFormGroup(messageErreur: MessageErreurFormGroupInput = { idMessageErreur: null }): MessageErreurFormGroup {
    const messageErreurRawValue = {
      ...this.getFormDefaults(),
      ...messageErreur,
    };
    return new FormGroup<MessageErreurFormGroupContent>({
      idMessageErreur: new FormControl(
        { value: messageErreurRawValue.idMessageErreur, disabled: messageErreurRawValue.idMessageErreur !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codeErreur: new FormControl(messageErreurRawValue.codeErreur),
      description: new FormControl(messageErreurRawValue.description),
    });
  }

  getMessageErreur(form: MessageErreurFormGroup): IMessageErreur | NewMessageErreur {
    return form.getRawValue() as IMessageErreur | NewMessageErreur;
  }

  resetForm(form: MessageErreurFormGroup, messageErreur: MessageErreurFormGroupInput): void {
    const messageErreurRawValue = { ...this.getFormDefaults(), ...messageErreur };
    form.reset(
      {
        ...messageErreurRawValue,
        idMessageErreur: { value: messageErreurRawValue.idMessageErreur, disabled: messageErreurRawValue.idMessageErreur !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MessageErreurFormDefaults {
    return {
      idMessageErreur: null,
    };
  }
}

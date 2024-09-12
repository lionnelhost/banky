import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMessageErreur } from '../message-erreur.model';
import { MessageErreurService } from '../service/message-erreur.service';
import { MessageErreurFormGroup, MessageErreurFormService } from './message-erreur-form.service';

@Component({
  standalone: true,
  selector: 'jhi-message-erreur-update',
  templateUrl: './message-erreur-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MessageErreurUpdateComponent implements OnInit {
  isSaving = false;
  messageErreur: IMessageErreur | null = null;

  protected messageErreurService = inject(MessageErreurService);
  protected messageErreurFormService = inject(MessageErreurFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MessageErreurFormGroup = this.messageErreurFormService.createMessageErreurFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ messageErreur }) => {
      this.messageErreur = messageErreur;
      if (messageErreur) {
        this.updateForm(messageErreur);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const messageErreur = this.messageErreurFormService.getMessageErreur(this.editForm);
    if (messageErreur.idMessageErreur !== null) {
      this.subscribeToSaveResponse(this.messageErreurService.update(messageErreur));
    } else {
      this.subscribeToSaveResponse(this.messageErreurService.create(messageErreur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMessageErreur>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(messageErreur: IMessageErreur): void {
    this.messageErreur = messageErreur;
    this.messageErreurFormService.resetForm(this.editForm, messageErreur);
  }
}

import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IParticipant } from '../participant.model';
import { ParticipantService } from '../service/participant.service';
import { ParticipantFormGroup, ParticipantFormService } from './participant-form.service';

@Component({
  standalone: true,
  selector: 'jhi-participant-update',
  templateUrl: './participant-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ParticipantUpdateComponent implements OnInit {
  isSaving = false;
  participant: IParticipant | null = null;

  protected participantService = inject(ParticipantService);
  protected participantFormService = inject(ParticipantFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ParticipantFormGroup = this.participantFormService.createParticipantFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ participant }) => {
      this.participant = participant;
      if (participant) {
        this.updateForm(participant);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const participant = this.participantFormService.getParticipant(this.editForm);
    if (participant.idParticipant !== null) {
      this.subscribeToSaveResponse(this.participantService.update(participant));
    } else {
      this.subscribeToSaveResponse(this.participantService.create(participant));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParticipant>>): void {
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

  protected updateForm(participant: IParticipant): void {
    this.participant = participant;
    this.participantFormService.resetForm(this.editForm, participant);
  }
}

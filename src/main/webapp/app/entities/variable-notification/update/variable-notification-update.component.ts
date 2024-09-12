import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IVariableNotification } from '../variable-notification.model';
import { VariableNotificationService } from '../service/variable-notification.service';
import { VariableNotificationFormGroup, VariableNotificationFormService } from './variable-notification-form.service';

@Component({
  standalone: true,
  selector: 'jhi-variable-notification-update',
  templateUrl: './variable-notification-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class VariableNotificationUpdateComponent implements OnInit {
  isSaving = false;
  variableNotification: IVariableNotification | null = null;

  protected variableNotificationService = inject(VariableNotificationService);
  protected variableNotificationFormService = inject(VariableNotificationFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: VariableNotificationFormGroup = this.variableNotificationFormService.createVariableNotificationFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ variableNotification }) => {
      this.variableNotification = variableNotification;
      if (variableNotification) {
        this.updateForm(variableNotification);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const variableNotification = this.variableNotificationFormService.getVariableNotification(this.editForm);
    if (variableNotification.idVarNotification !== null) {
      this.subscribeToSaveResponse(this.variableNotificationService.update(variableNotification));
    } else {
      this.subscribeToSaveResponse(this.variableNotificationService.create(variableNotification));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVariableNotification>>): void {
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

  protected updateForm(variableNotification: IVariableNotification): void {
    this.variableNotification = variableNotification;
    this.variableNotificationFormService.resetForm(this.editForm, variableNotification);
  }
}

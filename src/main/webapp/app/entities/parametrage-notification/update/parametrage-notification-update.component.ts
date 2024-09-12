import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IParametrageNotification } from '../parametrage-notification.model';
import { ParametrageNotificationService } from '../service/parametrage-notification.service';
import { ParametrageNotificationFormGroup, ParametrageNotificationFormService } from './parametrage-notification-form.service';

@Component({
  standalone: true,
  selector: 'jhi-parametrage-notification-update',
  templateUrl: './parametrage-notification-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ParametrageNotificationUpdateComponent implements OnInit {
  isSaving = false;
  parametrageNotification: IParametrageNotification | null = null;

  protected parametrageNotificationService = inject(ParametrageNotificationService);
  protected parametrageNotificationFormService = inject(ParametrageNotificationFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ParametrageNotificationFormGroup = this.parametrageNotificationFormService.createParametrageNotificationFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parametrageNotification }) => {
      this.parametrageNotification = parametrageNotification;
      if (parametrageNotification) {
        this.updateForm(parametrageNotification);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parametrageNotification = this.parametrageNotificationFormService.getParametrageNotification(this.editForm);
    if (parametrageNotification.idParamNotif !== null) {
      this.subscribeToSaveResponse(this.parametrageNotificationService.update(parametrageNotification));
    } else {
      this.subscribeToSaveResponse(this.parametrageNotificationService.create(parametrageNotification));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParametrageNotification>>): void {
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

  protected updateForm(parametrageNotification: IParametrageNotification): void {
    this.parametrageNotification = parametrageNotification;
    this.parametrageNotificationFormService.resetForm(this.editForm, parametrageNotification);
  }
}

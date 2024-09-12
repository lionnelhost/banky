import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IParametrageGlobal } from '../parametrage-global.model';
import { ParametrageGlobalService } from '../service/parametrage-global.service';
import { ParametrageGlobalFormGroup, ParametrageGlobalFormService } from './parametrage-global-form.service';

@Component({
  standalone: true,
  selector: 'jhi-parametrage-global-update',
  templateUrl: './parametrage-global-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ParametrageGlobalUpdateComponent implements OnInit {
  isSaving = false;
  parametrageGlobal: IParametrageGlobal | null = null;

  protected parametrageGlobalService = inject(ParametrageGlobalService);
  protected parametrageGlobalFormService = inject(ParametrageGlobalFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ParametrageGlobalFormGroup = this.parametrageGlobalFormService.createParametrageGlobalFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parametrageGlobal }) => {
      this.parametrageGlobal = parametrageGlobal;
      if (parametrageGlobal) {
        this.updateForm(parametrageGlobal);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parametrageGlobal = this.parametrageGlobalFormService.getParametrageGlobal(this.editForm);
    if (parametrageGlobal.idParamGlobal !== null) {
      this.subscribeToSaveResponse(this.parametrageGlobalService.update(parametrageGlobal));
    } else {
      this.subscribeToSaveResponse(this.parametrageGlobalService.create(parametrageGlobal));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParametrageGlobal>>): void {
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

  protected updateForm(parametrageGlobal: IParametrageGlobal): void {
    this.parametrageGlobal = parametrageGlobal;
    this.parametrageGlobalFormService.resetForm(this.editForm, parametrageGlobal);
  }
}

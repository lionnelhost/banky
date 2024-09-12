import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICanal } from '../canal.model';
import { CanalService } from '../service/canal.service';
import { CanalFormGroup, CanalFormService } from './canal-form.service';

@Component({
  standalone: true,
  selector: 'jhi-canal-update',
  templateUrl: './canal-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CanalUpdateComponent implements OnInit {
  isSaving = false;
  canal: ICanal | null = null;

  protected canalService = inject(CanalService);
  protected canalFormService = inject(CanalFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CanalFormGroup = this.canalFormService.createCanalFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ canal }) => {
      this.canal = canal;
      if (canal) {
        this.updateForm(canal);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const canal = this.canalFormService.getCanal(this.editForm);
    if (canal.idCanal !== null) {
      this.subscribeToSaveResponse(this.canalService.update(canal));
    } else {
      this.subscribeToSaveResponse(this.canalService.create(canal));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICanal>>): void {
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

  protected updateForm(canal: ICanal): void {
    this.canal = canal;
    this.canalFormService.resetForm(this.editForm, canal);
  }
}

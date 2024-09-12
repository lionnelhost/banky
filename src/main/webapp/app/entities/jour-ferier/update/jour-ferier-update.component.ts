import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IJourFerier } from '../jour-ferier.model';
import { JourFerierService } from '../service/jour-ferier.service';
import { JourFerierFormGroup, JourFerierFormService } from './jour-ferier-form.service';

@Component({
  standalone: true,
  selector: 'jhi-jour-ferier-update',
  templateUrl: './jour-ferier-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class JourFerierUpdateComponent implements OnInit {
  isSaving = false;
  jourFerier: IJourFerier | null = null;

  protected jourFerierService = inject(JourFerierService);
  protected jourFerierFormService = inject(JourFerierFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: JourFerierFormGroup = this.jourFerierFormService.createJourFerierFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jourFerier }) => {
      this.jourFerier = jourFerier;
      if (jourFerier) {
        this.updateForm(jourFerier);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jourFerier = this.jourFerierFormService.getJourFerier(this.editForm);
    if (jourFerier.idJourFerie !== null) {
      this.subscribeToSaveResponse(this.jourFerierService.update(jourFerier));
    } else {
      this.subscribeToSaveResponse(this.jourFerierService.create(jourFerier));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJourFerier>>): void {
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

  protected updateForm(jourFerier: IJourFerier): void {
    this.jourFerier = jourFerier;
    this.jourFerierFormService.resetForm(this.editForm, jourFerier);
  }
}

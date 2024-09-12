import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { TypePieceIdentite } from 'app/entities/enumerations/type-piece-identite.model';
import { StatutAbonne } from 'app/entities/enumerations/statut-abonne.model';
import { IAbonne } from '../abonne.model';
import { AbonneService } from '../service/abonne.service';
import { AbonneFormGroup, AbonneFormService } from './abonne-form.service';

@Component({
  standalone: true,
  selector: 'jhi-abonne-update',
  templateUrl: './abonne-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AbonneUpdateComponent implements OnInit {
  isSaving = false;
  abonne: IAbonne | null = null;
  typePieceIdentiteValues = Object.keys(TypePieceIdentite);
  statutAbonneValues = Object.keys(StatutAbonne);

  protected abonneService = inject(AbonneService);
  protected abonneFormService = inject(AbonneFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AbonneFormGroup = this.abonneFormService.createAbonneFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ abonne }) => {
      this.abonne = abonne;
      if (abonne) {
        this.updateForm(abonne);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const abonne = this.abonneFormService.getAbonne(this.editForm);
    if (abonne.idAbonne !== null) {
      this.subscribeToSaveResponse(this.abonneService.update(abonne));
    } else {
      this.subscribeToSaveResponse(this.abonneService.create(abonne));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAbonne>>): void {
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

  protected updateForm(abonne: IAbonne): void {
    this.abonne = abonne;
    this.abonneFormService.resetForm(this.editForm, abonne);
  }
}

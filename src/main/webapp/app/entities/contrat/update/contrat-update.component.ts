import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITypeContrat } from 'app/entities/type-contrat/type-contrat.model';
import { TypeContratService } from 'app/entities/type-contrat/service/type-contrat.service';
import { IContrat } from '../contrat.model';
import { ContratService } from '../service/contrat.service';
import { ContratFormGroup, ContratFormService } from './contrat-form.service';

@Component({
  standalone: true,
  selector: 'jhi-contrat-update',
  templateUrl: './contrat-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContratUpdateComponent implements OnInit {
  isSaving = false;
  contrat: IContrat | null = null;

  typeContratsSharedCollection: ITypeContrat[] = [];

  protected contratService = inject(ContratService);
  protected contratFormService = inject(ContratFormService);
  protected typeContratService = inject(TypeContratService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ContratFormGroup = this.contratFormService.createContratFormGroup();

  compareTypeContrat = (o1: ITypeContrat | null, o2: ITypeContrat | null): boolean => this.typeContratService.compareTypeContrat(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contrat }) => {
      this.contrat = contrat;
      if (contrat) {
        this.updateForm(contrat);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contrat = this.contratFormService.getContrat(this.editForm);
    if (contrat.idContrat !== null) {
      this.subscribeToSaveResponse(this.contratService.update(contrat));
    } else {
      this.subscribeToSaveResponse(this.contratService.create(contrat));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContrat>>): void {
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

  protected updateForm(contrat: IContrat): void {
    this.contrat = contrat;
    this.contratFormService.resetForm(this.editForm, contrat);

    this.typeContratsSharedCollection = this.typeContratService.addTypeContratToCollectionIfMissing<ITypeContrat>(
      this.typeContratsSharedCollection,
      contrat.typeContrat,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.typeContratService
      .query()
      .pipe(map((res: HttpResponse<ITypeContrat[]>) => res.body ?? []))
      .pipe(
        map((typeContrats: ITypeContrat[]) =>
          this.typeContratService.addTypeContratToCollectionIfMissing<ITypeContrat>(typeContrats, this.contrat?.typeContrat),
        ),
      )
      .subscribe((typeContrats: ITypeContrat[]) => (this.typeContratsSharedCollection = typeContrats));
  }
}

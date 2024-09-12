import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IContrat } from 'app/entities/contrat/contrat.model';
import { ContratService } from 'app/entities/contrat/service/contrat.service';
import { StatutCompteBancaire } from 'app/entities/enumerations/statut-compte-bancaire.model';
import { CompteBancaireService } from '../service/compte-bancaire.service';
import { ICompteBancaire } from '../compte-bancaire.model';
import { CompteBancaireFormGroup, CompteBancaireFormService } from './compte-bancaire-form.service';

@Component({
  standalone: true,
  selector: 'jhi-compte-bancaire-update',
  templateUrl: './compte-bancaire-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CompteBancaireUpdateComponent implements OnInit {
  isSaving = false;
  compteBancaire: ICompteBancaire | null = null;
  statutCompteBancaireValues = Object.keys(StatutCompteBancaire);

  contratsSharedCollection: IContrat[] = [];

  protected compteBancaireService = inject(CompteBancaireService);
  protected compteBancaireFormService = inject(CompteBancaireFormService);
  protected contratService = inject(ContratService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CompteBancaireFormGroup = this.compteBancaireFormService.createCompteBancaireFormGroup();

  compareContrat = (o1: IContrat | null, o2: IContrat | null): boolean => this.contratService.compareContrat(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ compteBancaire }) => {
      this.compteBancaire = compteBancaire;
      if (compteBancaire) {
        this.updateForm(compteBancaire);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const compteBancaire = this.compteBancaireFormService.getCompteBancaire(this.editForm);
    if (compteBancaire.idCompteBancaire !== null) {
      this.subscribeToSaveResponse(this.compteBancaireService.update(compteBancaire));
    } else {
      this.subscribeToSaveResponse(this.compteBancaireService.create(compteBancaire));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompteBancaire>>): void {
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

  protected updateForm(compteBancaire: ICompteBancaire): void {
    this.compteBancaire = compteBancaire;
    this.compteBancaireFormService.resetForm(this.editForm, compteBancaire);

    this.contratsSharedCollection = this.contratService.addContratToCollectionIfMissing<IContrat>(
      this.contratsSharedCollection,
      compteBancaire.contrat,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contratService
      .query()
      .pipe(map((res: HttpResponse<IContrat[]>) => res.body ?? []))
      .pipe(
        map((contrats: IContrat[]) =>
          this.contratService.addContratToCollectionIfMissing<IContrat>(contrats, this.compteBancaire?.contrat),
        ),
      )
      .subscribe((contrats: IContrat[]) => (this.contratsSharedCollection = contrats));
  }
}

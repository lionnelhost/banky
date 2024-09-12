import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IContrat } from 'app/entities/contrat/contrat.model';
import { ContratService } from 'app/entities/contrat/service/contrat.service';
import { IAbonne } from 'app/entities/abonne/abonne.model';
import { AbonneService } from 'app/entities/abonne/service/abonne.service';
import { ContratAbonnementService } from '../service/contrat-abonnement.service';
import { IContratAbonnement } from '../contrat-abonnement.model';
import { ContratAbonnementFormGroup, ContratAbonnementFormService } from './contrat-abonnement-form.service';

@Component({
  standalone: true,
  selector: 'jhi-contrat-abonnement-update',
  templateUrl: './contrat-abonnement-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContratAbonnementUpdateComponent implements OnInit {
  isSaving = false;
  contratAbonnement: IContratAbonnement | null = null;

  contratsSharedCollection: IContrat[] = [];
  abonnesSharedCollection: IAbonne[] = [];

  protected contratAbonnementService = inject(ContratAbonnementService);
  protected contratAbonnementFormService = inject(ContratAbonnementFormService);
  protected contratService = inject(ContratService);
  protected abonneService = inject(AbonneService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ContratAbonnementFormGroup = this.contratAbonnementFormService.createContratAbonnementFormGroup();

  compareContrat = (o1: IContrat | null, o2: IContrat | null): boolean => this.contratService.compareContrat(o1, o2);

  compareAbonne = (o1: IAbonne | null, o2: IAbonne | null): boolean => this.abonneService.compareAbonne(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contratAbonnement }) => {
      this.contratAbonnement = contratAbonnement;
      if (contratAbonnement) {
        this.updateForm(contratAbonnement);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contratAbonnement = this.contratAbonnementFormService.getContratAbonnement(this.editForm);
    if (contratAbonnement.id !== null) {
      this.subscribeToSaveResponse(this.contratAbonnementService.update(contratAbonnement));
    } else {
      this.subscribeToSaveResponse(this.contratAbonnementService.create(contratAbonnement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContratAbonnement>>): void {
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

  protected updateForm(contratAbonnement: IContratAbonnement): void {
    this.contratAbonnement = contratAbonnement;
    this.contratAbonnementFormService.resetForm(this.editForm, contratAbonnement);

    this.contratsSharedCollection = this.contratService.addContratToCollectionIfMissing<IContrat>(
      this.contratsSharedCollection,
      contratAbonnement.contrat,
    );
    this.abonnesSharedCollection = this.abonneService.addAbonneToCollectionIfMissing<IAbonne>(
      this.abonnesSharedCollection,
      contratAbonnement.abonne,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contratService
      .query()
      .pipe(map((res: HttpResponse<IContrat[]>) => res.body ?? []))
      .pipe(
        map((contrats: IContrat[]) =>
          this.contratService.addContratToCollectionIfMissing<IContrat>(contrats, this.contratAbonnement?.contrat),
        ),
      )
      .subscribe((contrats: IContrat[]) => (this.contratsSharedCollection = contrats));

    this.abonneService
      .query()
      .pipe(map((res: HttpResponse<IAbonne[]>) => res.body ?? []))
      .pipe(
        map((abonnes: IAbonne[]) => this.abonneService.addAbonneToCollectionIfMissing<IAbonne>(abonnes, this.contratAbonnement?.abonne)),
      )
      .subscribe((abonnes: IAbonne[]) => (this.abonnesSharedCollection = abonnes));
  }
}

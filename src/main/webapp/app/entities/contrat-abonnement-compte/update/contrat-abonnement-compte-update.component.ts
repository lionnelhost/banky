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
import { ICompteBancaire } from 'app/entities/compte-bancaire/compte-bancaire.model';
import { CompteBancaireService } from 'app/entities/compte-bancaire/service/compte-bancaire.service';
import { ContratAbonnementCompteService } from '../service/contrat-abonnement-compte.service';
import { IContratAbonnementCompte } from '../contrat-abonnement-compte.model';
import { ContratAbonnementCompteFormGroup, ContratAbonnementCompteFormService } from './contrat-abonnement-compte-form.service';

@Component({
  standalone: true,
  selector: 'jhi-contrat-abonnement-compte-update',
  templateUrl: './contrat-abonnement-compte-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContratAbonnementCompteUpdateComponent implements OnInit {
  isSaving = false;
  contratAbonnementCompte: IContratAbonnementCompte | null = null;

  contratsSharedCollection: IContrat[] = [];
  abonnesSharedCollection: IAbonne[] = [];
  compteBancairesSharedCollection: ICompteBancaire[] = [];

  protected contratAbonnementCompteService = inject(ContratAbonnementCompteService);
  protected contratAbonnementCompteFormService = inject(ContratAbonnementCompteFormService);
  protected contratService = inject(ContratService);
  protected abonneService = inject(AbonneService);
  protected compteBancaireService = inject(CompteBancaireService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ContratAbonnementCompteFormGroup = this.contratAbonnementCompteFormService.createContratAbonnementCompteFormGroup();

  compareContrat = (o1: IContrat | null, o2: IContrat | null): boolean => this.contratService.compareContrat(o1, o2);

  compareAbonne = (o1: IAbonne | null, o2: IAbonne | null): boolean => this.abonneService.compareAbonne(o1, o2);

  compareCompteBancaire = (o1: ICompteBancaire | null, o2: ICompteBancaire | null): boolean =>
    this.compteBancaireService.compareCompteBancaire(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contratAbonnementCompte }) => {
      this.contratAbonnementCompte = contratAbonnementCompte;
      if (contratAbonnementCompte) {
        this.updateForm(contratAbonnementCompte);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contratAbonnementCompte = this.contratAbonnementCompteFormService.getContratAbonnementCompte(this.editForm);
    if (contratAbonnementCompte.id !== null) {
      this.subscribeToSaveResponse(this.contratAbonnementCompteService.update(contratAbonnementCompte));
    } else {
      this.subscribeToSaveResponse(this.contratAbonnementCompteService.create(contratAbonnementCompte));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContratAbonnementCompte>>): void {
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

  protected updateForm(contratAbonnementCompte: IContratAbonnementCompte): void {
    this.contratAbonnementCompte = contratAbonnementCompte;
    this.contratAbonnementCompteFormService.resetForm(this.editForm, contratAbonnementCompte);

    this.contratsSharedCollection = this.contratService.addContratToCollectionIfMissing<IContrat>(
      this.contratsSharedCollection,
      contratAbonnementCompte.contrat,
    );
    this.abonnesSharedCollection = this.abonneService.addAbonneToCollectionIfMissing<IAbonne>(
      this.abonnesSharedCollection,
      contratAbonnementCompte.abonne,
    );
    this.compteBancairesSharedCollection = this.compteBancaireService.addCompteBancaireToCollectionIfMissing<ICompteBancaire>(
      this.compteBancairesSharedCollection,
      contratAbonnementCompte.compteBancaire,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contratService
      .query()
      .pipe(map((res: HttpResponse<IContrat[]>) => res.body ?? []))
      .pipe(
        map((contrats: IContrat[]) =>
          this.contratService.addContratToCollectionIfMissing<IContrat>(contrats, this.contratAbonnementCompte?.contrat),
        ),
      )
      .subscribe((contrats: IContrat[]) => (this.contratsSharedCollection = contrats));

    this.abonneService
      .query()
      .pipe(map((res: HttpResponse<IAbonne[]>) => res.body ?? []))
      .pipe(
        map((abonnes: IAbonne[]) =>
          this.abonneService.addAbonneToCollectionIfMissing<IAbonne>(abonnes, this.contratAbonnementCompte?.abonne),
        ),
      )
      .subscribe((abonnes: IAbonne[]) => (this.abonnesSharedCollection = abonnes));

    this.compteBancaireService
      .query()
      .pipe(map((res: HttpResponse<ICompteBancaire[]>) => res.body ?? []))
      .pipe(
        map((compteBancaires: ICompteBancaire[]) =>
          this.compteBancaireService.addCompteBancaireToCollectionIfMissing<ICompteBancaire>(
            compteBancaires,
            this.contratAbonnementCompte?.compteBancaire,
          ),
        ),
      )
      .subscribe((compteBancaires: ICompteBancaire[]) => (this.compteBancairesSharedCollection = compteBancaires));
  }
}

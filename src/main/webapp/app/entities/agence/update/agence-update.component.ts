import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IBanque } from 'app/entities/banque/banque.model';
import { BanqueService } from 'app/entities/banque/service/banque.service';
import { IAgence } from '../agence.model';
import { AgenceService } from '../service/agence.service';
import { AgenceFormGroup, AgenceFormService } from './agence-form.service';

@Component({
  standalone: true,
  selector: 'jhi-agence-update',
  templateUrl: './agence-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AgenceUpdateComponent implements OnInit {
  isSaving = false;
  agence: IAgence | null = null;

  banquesSharedCollection: IBanque[] = [];

  protected agenceService = inject(AgenceService);
  protected agenceFormService = inject(AgenceFormService);
  protected banqueService = inject(BanqueService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AgenceFormGroup = this.agenceFormService.createAgenceFormGroup();

  compareBanque = (o1: IBanque | null, o2: IBanque | null): boolean => this.banqueService.compareBanque(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agence }) => {
      this.agence = agence;
      if (agence) {
        this.updateForm(agence);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agence = this.agenceFormService.getAgence(this.editForm);
    if (agence.idAgence !== null) {
      this.subscribeToSaveResponse(this.agenceService.update(agence));
    } else {
      this.subscribeToSaveResponse(this.agenceService.create(agence));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgence>>): void {
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

  protected updateForm(agence: IAgence): void {
    this.agence = agence;
    this.agenceFormService.resetForm(this.editForm, agence);

    this.banquesSharedCollection = this.banqueService.addBanqueToCollectionIfMissing<IBanque>(this.banquesSharedCollection, agence.banque);
  }

  protected loadRelationshipsOptions(): void {
    this.banqueService
      .query()
      .pipe(map((res: HttpResponse<IBanque[]>) => res.body ?? []))
      .pipe(map((banques: IBanque[]) => this.banqueService.addBanqueToCollectionIfMissing<IBanque>(banques, this.agence?.banque)))
      .subscribe((banques: IBanque[]) => (this.banquesSharedCollection = banques));
  }
}

import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { Devise } from 'app/entities/enumerations/devise.model';
import { Langue } from 'app/entities/enumerations/langue.model';
import { IBanque } from '../banque.model';
import { BanqueService } from '../service/banque.service';
import { BanqueFormGroup, BanqueFormService } from './banque-form.service';

@Component({
  standalone: true,
  selector: 'jhi-banque-update',
  templateUrl: './banque-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class BanqueUpdateComponent implements OnInit {
  isSaving = false;
  banque: IBanque | null = null;
  deviseValues = Object.keys(Devise);
  langueValues = Object.keys(Langue);

  protected banqueService = inject(BanqueService);
  protected banqueFormService = inject(BanqueFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: BanqueFormGroup = this.banqueFormService.createBanqueFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ banque }) => {
      this.banque = banque;
      if (banque) {
        this.updateForm(banque);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const banque = this.banqueFormService.getBanque(this.editForm);
    if (banque.idBanque !== null) {
      this.subscribeToSaveResponse(this.banqueService.update(banque));
    } else {
      this.subscribeToSaveResponse(this.banqueService.create(banque));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBanque>>): void {
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

  protected updateForm(banque: IBanque): void {
    this.banque = banque;
    this.banqueFormService.resetForm(this.editForm, banque);
  }
}

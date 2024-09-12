import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDispositifSignature } from '../dispositif-signature.model';
import { DispositifSignatureService } from '../service/dispositif-signature.service';
import { DispositifSignatureFormGroup, DispositifSignatureFormService } from './dispositif-signature-form.service';

@Component({
  standalone: true,
  selector: 'jhi-dispositif-signature-update',
  templateUrl: './dispositif-signature-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DispositifSignatureUpdateComponent implements OnInit {
  isSaving = false;
  dispositifSignature: IDispositifSignature | null = null;

  protected dispositifSignatureService = inject(DispositifSignatureService);
  protected dispositifSignatureFormService = inject(DispositifSignatureFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DispositifSignatureFormGroup = this.dispositifSignatureFormService.createDispositifSignatureFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dispositifSignature }) => {
      this.dispositifSignature = dispositifSignature;
      if (dispositifSignature) {
        this.updateForm(dispositifSignature);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dispositifSignature = this.dispositifSignatureFormService.getDispositifSignature(this.editForm);
    if (dispositifSignature.idDispositif !== null) {
      this.subscribeToSaveResponse(this.dispositifSignatureService.update(dispositifSignature));
    } else {
      this.subscribeToSaveResponse(this.dispositifSignatureService.create(dispositifSignature));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDispositifSignature>>): void {
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

  protected updateForm(dispositifSignature: IDispositifSignature): void {
    this.dispositifSignature = dispositifSignature;
    this.dispositifSignatureFormService.resetForm(this.editForm, dispositifSignature);
  }
}

import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITypeClient } from '../type-client.model';
import { TypeClientService } from '../service/type-client.service';
import { TypeClientFormGroup, TypeClientFormService } from './type-client-form.service';

@Component({
  standalone: true,
  selector: 'jhi-type-client-update',
  templateUrl: './type-client-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TypeClientUpdateComponent implements OnInit {
  isSaving = false;
  typeClient: ITypeClient | null = null;

  protected typeClientService = inject(TypeClientService);
  protected typeClientFormService = inject(TypeClientFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TypeClientFormGroup = this.typeClientFormService.createTypeClientFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeClient }) => {
      this.typeClient = typeClient;
      if (typeClient) {
        this.updateForm(typeClient);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeClient = this.typeClientFormService.getTypeClient(this.editForm);
    if (typeClient.idTypeClient !== null) {
      this.subscribeToSaveResponse(this.typeClientService.update(typeClient));
    } else {
      this.subscribeToSaveResponse(this.typeClientService.create(typeClient));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeClient>>): void {
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

  protected updateForm(typeClient: ITypeClient): void {
    this.typeClient = typeClient;
    this.typeClientFormService.resetForm(this.editForm, typeClient);
  }
}

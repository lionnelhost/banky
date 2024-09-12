import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITypeTransaction } from '../type-transaction.model';
import { TypeTransactionService } from '../service/type-transaction.service';
import { TypeTransactionFormGroup, TypeTransactionFormService } from './type-transaction-form.service';

@Component({
  standalone: true,
  selector: 'jhi-type-transaction-update',
  templateUrl: './type-transaction-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TypeTransactionUpdateComponent implements OnInit {
  isSaving = false;
  typeTransaction: ITypeTransaction | null = null;

  protected typeTransactionService = inject(TypeTransactionService);
  protected typeTransactionFormService = inject(TypeTransactionFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TypeTransactionFormGroup = this.typeTransactionFormService.createTypeTransactionFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeTransaction }) => {
      this.typeTransaction = typeTransaction;
      if (typeTransaction) {
        this.updateForm(typeTransaction);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeTransaction = this.typeTransactionFormService.getTypeTransaction(this.editForm);
    if (typeTransaction.idTypeTransaction !== null) {
      this.subscribeToSaveResponse(this.typeTransactionService.update(typeTransaction));
    } else {
      this.subscribeToSaveResponse(this.typeTransactionService.create(typeTransaction));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeTransaction>>): void {
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

  protected updateForm(typeTransaction: ITypeTransaction): void {
    this.typeTransaction = typeTransaction;
    this.typeTransactionFormService.resetForm(this.editForm, typeTransaction);
  }
}

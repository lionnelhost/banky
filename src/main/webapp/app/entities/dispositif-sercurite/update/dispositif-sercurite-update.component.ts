import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICanal } from 'app/entities/canal/canal.model';
import { CanalService } from 'app/entities/canal/service/canal.service';
import { ITypeTransaction } from 'app/entities/type-transaction/type-transaction.model';
import { TypeTransactionService } from 'app/entities/type-transaction/service/type-transaction.service';
import { IDispositifSignature } from 'app/entities/dispositif-signature/dispositif-signature.model';
import { DispositifSignatureService } from 'app/entities/dispositif-signature/service/dispositif-signature.service';
import { DispositifSercuriteService } from '../service/dispositif-sercurite.service';
import { IDispositifSercurite } from '../dispositif-sercurite.model';
import { DispositifSercuriteFormGroup, DispositifSercuriteFormService } from './dispositif-sercurite-form.service';

@Component({
  standalone: true,
  selector: 'jhi-dispositif-sercurite-update',
  templateUrl: './dispositif-sercurite-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DispositifSercuriteUpdateComponent implements OnInit {
  isSaving = false;
  dispositifSercurite: IDispositifSercurite | null = null;

  canalsSharedCollection: ICanal[] = [];
  typeTransactionsSharedCollection: ITypeTransaction[] = [];
  dispositifSignaturesSharedCollection: IDispositifSignature[] = [];

  protected dispositifSercuriteService = inject(DispositifSercuriteService);
  protected dispositifSercuriteFormService = inject(DispositifSercuriteFormService);
  protected canalService = inject(CanalService);
  protected typeTransactionService = inject(TypeTransactionService);
  protected dispositifSignatureService = inject(DispositifSignatureService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DispositifSercuriteFormGroup = this.dispositifSercuriteFormService.createDispositifSercuriteFormGroup();

  compareCanal = (o1: ICanal | null, o2: ICanal | null): boolean => this.canalService.compareCanal(o1, o2);

  compareTypeTransaction = (o1: ITypeTransaction | null, o2: ITypeTransaction | null): boolean =>
    this.typeTransactionService.compareTypeTransaction(o1, o2);

  compareDispositifSignature = (o1: IDispositifSignature | null, o2: IDispositifSignature | null): boolean =>
    this.dispositifSignatureService.compareDispositifSignature(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dispositifSercurite }) => {
      this.dispositifSercurite = dispositifSercurite;
      if (dispositifSercurite) {
        this.updateForm(dispositifSercurite);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dispositifSercurite = this.dispositifSercuriteFormService.getDispositifSercurite(this.editForm);
    if (dispositifSercurite.id !== null) {
      this.subscribeToSaveResponse(this.dispositifSercuriteService.update(dispositifSercurite));
    } else {
      this.subscribeToSaveResponse(this.dispositifSercuriteService.create(dispositifSercurite));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDispositifSercurite>>): void {
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

  protected updateForm(dispositifSercurite: IDispositifSercurite): void {
    this.dispositifSercurite = dispositifSercurite;
    this.dispositifSercuriteFormService.resetForm(this.editForm, dispositifSercurite);

    this.canalsSharedCollection = this.canalService.addCanalToCollectionIfMissing<ICanal>(
      this.canalsSharedCollection,
      dispositifSercurite.canal,
    );
    this.typeTransactionsSharedCollection = this.typeTransactionService.addTypeTransactionToCollectionIfMissing<ITypeTransaction>(
      this.typeTransactionsSharedCollection,
      dispositifSercurite.typeTransaction,
    );
    this.dispositifSignaturesSharedCollection =
      this.dispositifSignatureService.addDispositifSignatureToCollectionIfMissing<IDispositifSignature>(
        this.dispositifSignaturesSharedCollection,
        dispositifSercurite.dispositifSignature,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.canalService
      .query()
      .pipe(map((res: HttpResponse<ICanal[]>) => res.body ?? []))
      .pipe(map((canals: ICanal[]) => this.canalService.addCanalToCollectionIfMissing<ICanal>(canals, this.dispositifSercurite?.canal)))
      .subscribe((canals: ICanal[]) => (this.canalsSharedCollection = canals));

    this.typeTransactionService
      .query()
      .pipe(map((res: HttpResponse<ITypeTransaction[]>) => res.body ?? []))
      .pipe(
        map((typeTransactions: ITypeTransaction[]) =>
          this.typeTransactionService.addTypeTransactionToCollectionIfMissing<ITypeTransaction>(
            typeTransactions,
            this.dispositifSercurite?.typeTransaction,
          ),
        ),
      )
      .subscribe((typeTransactions: ITypeTransaction[]) => (this.typeTransactionsSharedCollection = typeTransactions));

    this.dispositifSignatureService
      .query()
      .pipe(map((res: HttpResponse<IDispositifSignature[]>) => res.body ?? []))
      .pipe(
        map((dispositifSignatures: IDispositifSignature[]) =>
          this.dispositifSignatureService.addDispositifSignatureToCollectionIfMissing<IDispositifSignature>(
            dispositifSignatures,
            this.dispositifSercurite?.dispositifSignature,
          ),
        ),
      )
      .subscribe((dispositifSignatures: IDispositifSignature[]) => (this.dispositifSignaturesSharedCollection = dispositifSignatures));
  }
}

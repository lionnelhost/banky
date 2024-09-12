import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITypeTransaction } from '../type-transaction.model';
import { TypeTransactionService } from '../service/type-transaction.service';

@Component({
  standalone: true,
  templateUrl: './type-transaction-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TypeTransactionDeleteDialogComponent {
  typeTransaction?: ITypeTransaction;

  protected typeTransactionService = inject(TypeTransactionService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.typeTransactionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

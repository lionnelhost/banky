import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IBanque } from '../banque.model';
import { BanqueService } from '../service/banque.service';

@Component({
  standalone: true,
  templateUrl: './banque-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class BanqueDeleteDialogComponent {
  banque?: IBanque;

  protected banqueService = inject(BanqueService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.banqueService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

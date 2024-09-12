import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IJourFerier } from '../jour-ferier.model';
import { JourFerierService } from '../service/jour-ferier.service';

@Component({
  standalone: true,
  templateUrl: './jour-ferier-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class JourFerierDeleteDialogComponent {
  jourFerier?: IJourFerier;

  protected jourFerierService = inject(JourFerierService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.jourFerierService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

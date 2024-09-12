import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAgence } from '../agence.model';
import { AgenceService } from '../service/agence.service';

@Component({
  standalone: true,
  templateUrl: './agence-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AgenceDeleteDialogComponent {
  agence?: IAgence;

  protected agenceService = inject(AgenceService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.agenceService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

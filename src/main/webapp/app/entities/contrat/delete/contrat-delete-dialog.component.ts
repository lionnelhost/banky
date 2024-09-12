import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IContrat } from '../contrat.model';
import { ContratService } from '../service/contrat.service';

@Component({
  standalone: true,
  templateUrl: './contrat-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ContratDeleteDialogComponent {
  contrat?: IContrat;

  protected contratService = inject(ContratService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.contratService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

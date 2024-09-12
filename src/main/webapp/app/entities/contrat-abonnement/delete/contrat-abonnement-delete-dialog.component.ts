import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IContratAbonnement } from '../contrat-abonnement.model';
import { ContratAbonnementService } from '../service/contrat-abonnement.service';

@Component({
  standalone: true,
  templateUrl: './contrat-abonnement-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ContratAbonnementDeleteDialogComponent {
  contratAbonnement?: IContratAbonnement;

  protected contratAbonnementService = inject(ContratAbonnementService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contratAbonnementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

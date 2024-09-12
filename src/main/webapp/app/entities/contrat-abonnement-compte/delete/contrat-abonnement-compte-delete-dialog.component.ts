import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IContratAbonnementCompte } from '../contrat-abonnement-compte.model';
import { ContratAbonnementCompteService } from '../service/contrat-abonnement-compte.service';

@Component({
  standalone: true,
  templateUrl: './contrat-abonnement-compte-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ContratAbonnementCompteDeleteDialogComponent {
  contratAbonnementCompte?: IContratAbonnementCompte;

  protected contratAbonnementCompteService = inject(ContratAbonnementCompteService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contratAbonnementCompteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

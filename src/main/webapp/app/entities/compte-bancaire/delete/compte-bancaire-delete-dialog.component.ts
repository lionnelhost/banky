import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICompteBancaire } from '../compte-bancaire.model';
import { CompteBancaireService } from '../service/compte-bancaire.service';

@Component({
  standalone: true,
  templateUrl: './compte-bancaire-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CompteBancaireDeleteDialogComponent {
  compteBancaire?: ICompteBancaire;

  protected compteBancaireService = inject(CompteBancaireService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.compteBancaireService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

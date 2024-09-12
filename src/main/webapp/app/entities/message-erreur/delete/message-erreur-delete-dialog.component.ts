import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMessageErreur } from '../message-erreur.model';
import { MessageErreurService } from '../service/message-erreur.service';

@Component({
  standalone: true,
  templateUrl: './message-erreur-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MessageErreurDeleteDialogComponent {
  messageErreur?: IMessageErreur;

  protected messageErreurService = inject(MessageErreurService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.messageErreurService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

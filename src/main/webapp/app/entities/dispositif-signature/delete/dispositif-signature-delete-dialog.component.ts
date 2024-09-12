import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDispositifSignature } from '../dispositif-signature.model';
import { DispositifSignatureService } from '../service/dispositif-signature.service';

@Component({
  standalone: true,
  templateUrl: './dispositif-signature-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DispositifSignatureDeleteDialogComponent {
  dispositifSignature?: IDispositifSignature;

  protected dispositifSignatureService = inject(DispositifSignatureService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.dispositifSignatureService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

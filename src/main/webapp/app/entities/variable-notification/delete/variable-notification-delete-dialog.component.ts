import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IVariableNotification } from '../variable-notification.model';
import { VariableNotificationService } from '../service/variable-notification.service';

@Component({
  standalone: true,
  templateUrl: './variable-notification-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class VariableNotificationDeleteDialogComponent {
  variableNotification?: IVariableNotification;

  protected variableNotificationService = inject(VariableNotificationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.variableNotificationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IParametrageNotification } from '../parametrage-notification.model';
import { ParametrageNotificationService } from '../service/parametrage-notification.service';

@Component({
  standalone: true,
  templateUrl: './parametrage-notification-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ParametrageNotificationDeleteDialogComponent {
  parametrageNotification?: IParametrageNotification;

  protected parametrageNotificationService = inject(ParametrageNotificationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.parametrageNotificationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICanal } from '../canal.model';
import { CanalService } from '../service/canal.service';

@Component({
  standalone: true,
  templateUrl: './canal-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CanalDeleteDialogComponent {
  canal?: ICanal;

  protected canalService = inject(CanalService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.canalService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDispositifSercurite } from '../dispositif-sercurite.model';
import { DispositifSercuriteService } from '../service/dispositif-sercurite.service';

@Component({
  standalone: true,
  templateUrl: './dispositif-sercurite-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DispositifSercuriteDeleteDialogComponent {
  dispositifSercurite?: IDispositifSercurite;

  protected dispositifSercuriteService = inject(DispositifSercuriteService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dispositifSercuriteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

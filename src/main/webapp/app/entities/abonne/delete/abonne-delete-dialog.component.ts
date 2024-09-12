import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAbonne } from '../abonne.model';
import { AbonneService } from '../service/abonne.service';

@Component({
  standalone: true,
  templateUrl: './abonne-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AbonneDeleteDialogComponent {
  abonne?: IAbonne;

  protected abonneService = inject(AbonneService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.abonneService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITypeClient } from '../type-client.model';
import { TypeClientService } from '../service/type-client.service';

@Component({
  standalone: true,
  templateUrl: './type-client-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TypeClientDeleteDialogComponent {
  typeClient?: ITypeClient;

  protected typeClientService = inject(TypeClientService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.typeClientService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

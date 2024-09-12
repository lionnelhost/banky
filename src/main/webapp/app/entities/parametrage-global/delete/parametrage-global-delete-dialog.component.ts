import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IParametrageGlobal } from '../parametrage-global.model';
import { ParametrageGlobalService } from '../service/parametrage-global.service';

@Component({
  standalone: true,
  templateUrl: './parametrage-global-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ParametrageGlobalDeleteDialogComponent {
  parametrageGlobal?: IParametrageGlobal;

  protected parametrageGlobalService = inject(ParametrageGlobalService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.parametrageGlobalService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

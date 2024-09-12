import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ITypeTransaction } from '../type-transaction.model';

@Component({
  standalone: true,
  selector: 'jhi-type-transaction-detail',
  templateUrl: './type-transaction-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TypeTransactionDetailComponent {
  typeTransaction = input<ITypeTransaction | null>(null);

  previousState(): void {
    window.history.back();
  }
}

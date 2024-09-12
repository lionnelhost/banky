import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ITypeClient } from '../type-client.model';

@Component({
  standalone: true,
  selector: 'jhi-type-client-detail',
  templateUrl: './type-client-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TypeClientDetailComponent {
  typeClient = input<ITypeClient | null>(null);

  previousState(): void {
    window.history.back();
  }
}

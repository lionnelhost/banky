import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IDispositifSignature } from '../dispositif-signature.model';

@Component({
  standalone: true,
  selector: 'jhi-dispositif-signature-detail',
  templateUrl: './dispositif-signature-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DispositifSignatureDetailComponent {
  dispositifSignature = input<IDispositifSignature | null>(null);

  previousState(): void {
    window.history.back();
  }
}

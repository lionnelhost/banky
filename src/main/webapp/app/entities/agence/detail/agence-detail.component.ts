import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IAgence } from '../agence.model';

@Component({
  standalone: true,
  selector: 'jhi-agence-detail',
  templateUrl: './agence-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AgenceDetailComponent {
  agence = input<IAgence | null>(null);

  previousState(): void {
    window.history.back();
  }
}

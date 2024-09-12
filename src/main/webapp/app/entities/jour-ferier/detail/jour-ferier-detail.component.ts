import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IJourFerier } from '../jour-ferier.model';

@Component({
  standalone: true,
  selector: 'jhi-jour-ferier-detail',
  templateUrl: './jour-ferier-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class JourFerierDetailComponent {
  jourFerier = input<IJourFerier | null>(null);

  previousState(): void {
    window.history.back();
  }
}

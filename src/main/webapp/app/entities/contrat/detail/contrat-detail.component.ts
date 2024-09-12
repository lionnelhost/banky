import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IContrat } from '../contrat.model';

@Component({
  standalone: true,
  selector: 'jhi-contrat-detail',
  templateUrl: './contrat-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ContratDetailComponent {
  contrat = input<IContrat | null>(null);

  previousState(): void {
    window.history.back();
  }
}

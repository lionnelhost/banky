import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IContratAbonnement } from '../contrat-abonnement.model';

@Component({
  standalone: true,
  selector: 'jhi-contrat-abonnement-detail',
  templateUrl: './contrat-abonnement-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ContratAbonnementDetailComponent {
  contratAbonnement = input<IContratAbonnement | null>(null);

  previousState(): void {
    window.history.back();
  }
}

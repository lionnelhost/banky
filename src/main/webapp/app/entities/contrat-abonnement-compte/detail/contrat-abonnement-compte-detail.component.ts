import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IContratAbonnementCompte } from '../contrat-abonnement-compte.model';

@Component({
  standalone: true,
  selector: 'jhi-contrat-abonnement-compte-detail',
  templateUrl: './contrat-abonnement-compte-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ContratAbonnementCompteDetailComponent {
  contratAbonnementCompte = input<IContratAbonnementCompte | null>(null);

  previousState(): void {
    window.history.back();
  }
}

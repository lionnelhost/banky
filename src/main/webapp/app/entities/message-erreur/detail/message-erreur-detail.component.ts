import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IMessageErreur } from '../message-erreur.model';

@Component({
  standalone: true,
  selector: 'jhi-message-erreur-detail',
  templateUrl: './message-erreur-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class MessageErreurDetailComponent {
  messageErreur = input<IMessageErreur | null>(null);

  previousState(): void {
    window.history.back();
  }
}

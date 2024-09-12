import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IParametrageNotification } from '../parametrage-notification.model';

@Component({
  standalone: true,
  selector: 'jhi-parametrage-notification-detail',
  templateUrl: './parametrage-notification-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ParametrageNotificationDetailComponent {
  parametrageNotification = input<IParametrageNotification | null>(null);

  previousState(): void {
    window.history.back();
  }
}

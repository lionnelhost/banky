import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IVariableNotification } from '../variable-notification.model';

@Component({
  standalone: true,
  selector: 'jhi-variable-notification-detail',
  templateUrl: './variable-notification-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class VariableNotificationDetailComponent {
  variableNotification = input<IVariableNotification | null>(null);

  previousState(): void {
    window.history.back();
  }
}

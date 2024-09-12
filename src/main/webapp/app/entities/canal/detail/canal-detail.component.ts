import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ICanal } from '../canal.model';

@Component({
  standalone: true,
  selector: 'jhi-canal-detail',
  templateUrl: './canal-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CanalDetailComponent {
  canal = input<ICanal | null>(null);

  previousState(): void {
    window.history.back();
  }
}

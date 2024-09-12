import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IDispositifSercurite } from '../dispositif-sercurite.model';

@Component({
  standalone: true,
  selector: 'jhi-dispositif-sercurite-detail',
  templateUrl: './dispositif-sercurite-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DispositifSercuriteDetailComponent {
  dispositifSercurite = input<IDispositifSercurite | null>(null);

  previousState(): void {
    window.history.back();
  }
}

import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IAbonne } from '../abonne.model';

@Component({
  standalone: true,
  selector: 'jhi-abonne-detail',
  templateUrl: './abonne-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AbonneDetailComponent {
  abonne = input<IAbonne | null>(null);

  previousState(): void {
    window.history.back();
  }
}

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ContratAbonnementDetailComponent } from './contrat-abonnement-detail.component';

describe('ContratAbonnement Management Detail Component', () => {
  let comp: ContratAbonnementDetailComponent;
  let fixture: ComponentFixture<ContratAbonnementDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContratAbonnementDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./contrat-abonnement-detail.component').then(m => m.ContratAbonnementDetailComponent),
              resolve: { contratAbonnement: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ContratAbonnementDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContratAbonnementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load contratAbonnement on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ContratAbonnementDetailComponent);

      // THEN
      expect(instance.contratAbonnement()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});

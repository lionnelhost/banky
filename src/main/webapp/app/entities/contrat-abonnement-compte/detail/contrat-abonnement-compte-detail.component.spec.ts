import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ContratAbonnementCompteDetailComponent } from './contrat-abonnement-compte-detail.component';

describe('ContratAbonnementCompte Management Detail Component', () => {
  let comp: ContratAbonnementCompteDetailComponent;
  let fixture: ComponentFixture<ContratAbonnementCompteDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContratAbonnementCompteDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./contrat-abonnement-compte-detail.component').then(m => m.ContratAbonnementCompteDetailComponent),
              resolve: { contratAbonnementCompte: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ContratAbonnementCompteDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContratAbonnementCompteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load contratAbonnementCompte on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ContratAbonnementCompteDetailComponent);

      // THEN
      expect(instance.contratAbonnementCompte()).toEqual(expect.objectContaining({ id: 123 }));
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

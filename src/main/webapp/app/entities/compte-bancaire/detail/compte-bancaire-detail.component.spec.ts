import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CompteBancaireDetailComponent } from './compte-bancaire-detail.component';

describe('CompteBancaire Management Detail Component', () => {
  let comp: CompteBancaireDetailComponent;
  let fixture: ComponentFixture<CompteBancaireDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompteBancaireDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./compte-bancaire-detail.component').then(m => m.CompteBancaireDetailComponent),
              resolve: { compteBancaire: () => of({ idCompteBancaire: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CompteBancaireDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompteBancaireDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load compteBancaire on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CompteBancaireDetailComponent);

      // THEN
      expect(instance.compteBancaire()).toEqual(expect.objectContaining({ idCompteBancaire: 'ABC' }));
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

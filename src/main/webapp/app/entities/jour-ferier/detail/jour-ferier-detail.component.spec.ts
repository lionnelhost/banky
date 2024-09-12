import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { JourFerierDetailComponent } from './jour-ferier-detail.component';

describe('JourFerier Management Detail Component', () => {
  let comp: JourFerierDetailComponent;
  let fixture: ComponentFixture<JourFerierDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JourFerierDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./jour-ferier-detail.component').then(m => m.JourFerierDetailComponent),
              resolve: { jourFerier: () => of({ idJourFerie: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(JourFerierDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JourFerierDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load jourFerier on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', JourFerierDetailComponent);

      // THEN
      expect(instance.jourFerier()).toEqual(expect.objectContaining({ idJourFerie: 'ABC' }));
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

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AgenceDetailComponent } from './agence-detail.component';

describe('Agence Management Detail Component', () => {
  let comp: AgenceDetailComponent;
  let fixture: ComponentFixture<AgenceDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgenceDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./agence-detail.component').then(m => m.AgenceDetailComponent),
              resolve: { agence: () => of({ idAgence: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AgenceDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AgenceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load agence on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AgenceDetailComponent);

      // THEN
      expect(instance.agence()).toEqual(expect.objectContaining({ idAgence: 'ABC' }));
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

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AbonneDetailComponent } from './abonne-detail.component';

describe('Abonne Management Detail Component', () => {
  let comp: AbonneDetailComponent;
  let fixture: ComponentFixture<AbonneDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AbonneDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./abonne-detail.component').then(m => m.AbonneDetailComponent),
              resolve: { abonne: () => of({ idAbonne: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AbonneDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AbonneDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load abonne on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AbonneDetailComponent);

      // THEN
      expect(instance.abonne()).toEqual(expect.objectContaining({ idAbonne: 'ABC' }));
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

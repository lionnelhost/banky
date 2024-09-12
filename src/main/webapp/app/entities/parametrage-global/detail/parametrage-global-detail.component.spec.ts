import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ParametrageGlobalDetailComponent } from './parametrage-global-detail.component';

describe('ParametrageGlobal Management Detail Component', () => {
  let comp: ParametrageGlobalDetailComponent;
  let fixture: ComponentFixture<ParametrageGlobalDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ParametrageGlobalDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./parametrage-global-detail.component').then(m => m.ParametrageGlobalDetailComponent),
              resolve: { parametrageGlobal: () => of({ idParamGlobal: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ParametrageGlobalDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParametrageGlobalDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load parametrageGlobal on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ParametrageGlobalDetailComponent);

      // THEN
      expect(instance.parametrageGlobal()).toEqual(expect.objectContaining({ idParamGlobal: 'ABC' }));
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

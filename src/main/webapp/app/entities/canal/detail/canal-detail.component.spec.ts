import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CanalDetailComponent } from './canal-detail.component';

describe('Canal Management Detail Component', () => {
  let comp: CanalDetailComponent;
  let fixture: ComponentFixture<CanalDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CanalDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./canal-detail.component').then(m => m.CanalDetailComponent),
              resolve: { canal: () => of({ idCanal: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CanalDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CanalDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load canal on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CanalDetailComponent);

      // THEN
      expect(instance.canal()).toEqual(expect.objectContaining({ idCanal: 'ABC' }));
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

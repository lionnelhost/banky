import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ParametrageNotificationDetailComponent } from './parametrage-notification-detail.component';

describe('ParametrageNotification Management Detail Component', () => {
  let comp: ParametrageNotificationDetailComponent;
  let fixture: ComponentFixture<ParametrageNotificationDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ParametrageNotificationDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./parametrage-notification-detail.component').then(m => m.ParametrageNotificationDetailComponent),
              resolve: { parametrageNotification: () => of({ idParamNotif: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ParametrageNotificationDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParametrageNotificationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load parametrageNotification on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ParametrageNotificationDetailComponent);

      // THEN
      expect(instance.parametrageNotification()).toEqual(expect.objectContaining({ idParamNotif: 'ABC' }));
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

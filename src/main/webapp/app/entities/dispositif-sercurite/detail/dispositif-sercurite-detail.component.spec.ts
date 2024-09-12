import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DispositifSercuriteDetailComponent } from './dispositif-sercurite-detail.component';

describe('DispositifSercurite Management Detail Component', () => {
  let comp: DispositifSercuriteDetailComponent;
  let fixture: ComponentFixture<DispositifSercuriteDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DispositifSercuriteDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./dispositif-sercurite-detail.component').then(m => m.DispositifSercuriteDetailComponent),
              resolve: { dispositifSercurite: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DispositifSercuriteDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DispositifSercuriteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dispositifSercurite on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DispositifSercuriteDetailComponent);

      // THEN
      expect(instance.dispositifSercurite()).toEqual(expect.objectContaining({ id: 123 }));
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

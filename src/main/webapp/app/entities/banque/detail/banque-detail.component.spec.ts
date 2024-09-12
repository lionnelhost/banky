import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { BanqueDetailComponent } from './banque-detail.component';

describe('Banque Management Detail Component', () => {
  let comp: BanqueDetailComponent;
  let fixture: ComponentFixture<BanqueDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BanqueDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./banque-detail.component').then(m => m.BanqueDetailComponent),
              resolve: { banque: () => of({ idBanque: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(BanqueDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BanqueDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load banque on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', BanqueDetailComponent);

      // THEN
      expect(instance.banque()).toEqual(expect.objectContaining({ idBanque: 'ABC' }));
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

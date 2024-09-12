import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DispositifSignatureDetailComponent } from './dispositif-signature-detail.component';

describe('DispositifSignature Management Detail Component', () => {
  let comp: DispositifSignatureDetailComponent;
  let fixture: ComponentFixture<DispositifSignatureDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DispositifSignatureDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./dispositif-signature-detail.component').then(m => m.DispositifSignatureDetailComponent),
              resolve: { dispositifSignature: () => of({ idDispositif: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DispositifSignatureDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DispositifSignatureDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dispositifSignature on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DispositifSignatureDetailComponent);

      // THEN
      expect(instance.dispositifSignature()).toEqual(expect.objectContaining({ idDispositif: 'ABC' }));
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

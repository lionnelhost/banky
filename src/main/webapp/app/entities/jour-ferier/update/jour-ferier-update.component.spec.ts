import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { JourFerierService } from '../service/jour-ferier.service';
import { IJourFerier } from '../jour-ferier.model';
import { JourFerierFormService } from './jour-ferier-form.service';

import { JourFerierUpdateComponent } from './jour-ferier-update.component';

describe('JourFerier Management Update Component', () => {
  let comp: JourFerierUpdateComponent;
  let fixture: ComponentFixture<JourFerierUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let jourFerierFormService: JourFerierFormService;
  let jourFerierService: JourFerierService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [JourFerierUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(JourFerierUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JourFerierUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    jourFerierFormService = TestBed.inject(JourFerierFormService);
    jourFerierService = TestBed.inject(JourFerierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const jourFerier: IJourFerier = { idJourFerie: 'CBA' };

      activatedRoute.data = of({ jourFerier });
      comp.ngOnInit();

      expect(comp.jourFerier).toEqual(jourFerier);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourFerier>>();
      const jourFerier = { idJourFerie: 'ABC' };
      jest.spyOn(jourFerierFormService, 'getJourFerier').mockReturnValue(jourFerier);
      jest.spyOn(jourFerierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jourFerier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: jourFerier }));
      saveSubject.complete();

      // THEN
      expect(jourFerierFormService.getJourFerier).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(jourFerierService.update).toHaveBeenCalledWith(expect.objectContaining(jourFerier));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourFerier>>();
      const jourFerier = { idJourFerie: 'ABC' };
      jest.spyOn(jourFerierFormService, 'getJourFerier').mockReturnValue({ idJourFerie: null });
      jest.spyOn(jourFerierService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jourFerier: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: jourFerier }));
      saveSubject.complete();

      // THEN
      expect(jourFerierFormService.getJourFerier).toHaveBeenCalled();
      expect(jourFerierService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourFerier>>();
      const jourFerier = { idJourFerie: 'ABC' };
      jest.spyOn(jourFerierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jourFerier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(jourFerierService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

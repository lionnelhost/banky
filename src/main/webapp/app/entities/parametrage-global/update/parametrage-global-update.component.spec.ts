import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ParametrageGlobalService } from '../service/parametrage-global.service';
import { IParametrageGlobal } from '../parametrage-global.model';
import { ParametrageGlobalFormService } from './parametrage-global-form.service';

import { ParametrageGlobalUpdateComponent } from './parametrage-global-update.component';

describe('ParametrageGlobal Management Update Component', () => {
  let comp: ParametrageGlobalUpdateComponent;
  let fixture: ComponentFixture<ParametrageGlobalUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let parametrageGlobalFormService: ParametrageGlobalFormService;
  let parametrageGlobalService: ParametrageGlobalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ParametrageGlobalUpdateComponent],
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
      .overrideTemplate(ParametrageGlobalUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParametrageGlobalUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    parametrageGlobalFormService = TestBed.inject(ParametrageGlobalFormService);
    parametrageGlobalService = TestBed.inject(ParametrageGlobalService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const parametrageGlobal: IParametrageGlobal = { idParamGlobal: 'CBA' };

      activatedRoute.data = of({ parametrageGlobal });
      comp.ngOnInit();

      expect(comp.parametrageGlobal).toEqual(parametrageGlobal);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParametrageGlobal>>();
      const parametrageGlobal = { idParamGlobal: 'ABC' };
      jest.spyOn(parametrageGlobalFormService, 'getParametrageGlobal').mockReturnValue(parametrageGlobal);
      jest.spyOn(parametrageGlobalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parametrageGlobal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: parametrageGlobal }));
      saveSubject.complete();

      // THEN
      expect(parametrageGlobalFormService.getParametrageGlobal).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(parametrageGlobalService.update).toHaveBeenCalledWith(expect.objectContaining(parametrageGlobal));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParametrageGlobal>>();
      const parametrageGlobal = { idParamGlobal: 'ABC' };
      jest.spyOn(parametrageGlobalFormService, 'getParametrageGlobal').mockReturnValue({ idParamGlobal: null });
      jest.spyOn(parametrageGlobalService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parametrageGlobal: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: parametrageGlobal }));
      saveSubject.complete();

      // THEN
      expect(parametrageGlobalFormService.getParametrageGlobal).toHaveBeenCalled();
      expect(parametrageGlobalService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParametrageGlobal>>();
      const parametrageGlobal = { idParamGlobal: 'ABC' };
      jest.spyOn(parametrageGlobalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parametrageGlobal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(parametrageGlobalService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

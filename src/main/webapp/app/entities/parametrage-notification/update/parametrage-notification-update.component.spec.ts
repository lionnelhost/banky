import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ParametrageNotificationService } from '../service/parametrage-notification.service';
import { IParametrageNotification } from '../parametrage-notification.model';
import { ParametrageNotificationFormService } from './parametrage-notification-form.service';

import { ParametrageNotificationUpdateComponent } from './parametrage-notification-update.component';

describe('ParametrageNotification Management Update Component', () => {
  let comp: ParametrageNotificationUpdateComponent;
  let fixture: ComponentFixture<ParametrageNotificationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let parametrageNotificationFormService: ParametrageNotificationFormService;
  let parametrageNotificationService: ParametrageNotificationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ParametrageNotificationUpdateComponent],
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
      .overrideTemplate(ParametrageNotificationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParametrageNotificationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    parametrageNotificationFormService = TestBed.inject(ParametrageNotificationFormService);
    parametrageNotificationService = TestBed.inject(ParametrageNotificationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const parametrageNotification: IParametrageNotification = { idParamNotif: 'CBA' };

      activatedRoute.data = of({ parametrageNotification });
      comp.ngOnInit();

      expect(comp.parametrageNotification).toEqual(parametrageNotification);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParametrageNotification>>();
      const parametrageNotification = { idParamNotif: 'ABC' };
      jest.spyOn(parametrageNotificationFormService, 'getParametrageNotification').mockReturnValue(parametrageNotification);
      jest.spyOn(parametrageNotificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parametrageNotification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: parametrageNotification }));
      saveSubject.complete();

      // THEN
      expect(parametrageNotificationFormService.getParametrageNotification).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(parametrageNotificationService.update).toHaveBeenCalledWith(expect.objectContaining(parametrageNotification));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParametrageNotification>>();
      const parametrageNotification = { idParamNotif: 'ABC' };
      jest.spyOn(parametrageNotificationFormService, 'getParametrageNotification').mockReturnValue({ idParamNotif: null });
      jest.spyOn(parametrageNotificationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parametrageNotification: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: parametrageNotification }));
      saveSubject.complete();

      // THEN
      expect(parametrageNotificationFormService.getParametrageNotification).toHaveBeenCalled();
      expect(parametrageNotificationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParametrageNotification>>();
      const parametrageNotification = { idParamNotif: 'ABC' };
      jest.spyOn(parametrageNotificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parametrageNotification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(parametrageNotificationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

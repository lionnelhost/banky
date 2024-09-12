import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { VariableNotificationService } from '../service/variable-notification.service';
import { IVariableNotification } from '../variable-notification.model';
import { VariableNotificationFormService } from './variable-notification-form.service';

import { VariableNotificationUpdateComponent } from './variable-notification-update.component';

describe('VariableNotification Management Update Component', () => {
  let comp: VariableNotificationUpdateComponent;
  let fixture: ComponentFixture<VariableNotificationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let variableNotificationFormService: VariableNotificationFormService;
  let variableNotificationService: VariableNotificationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [VariableNotificationUpdateComponent],
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
      .overrideTemplate(VariableNotificationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VariableNotificationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    variableNotificationFormService = TestBed.inject(VariableNotificationFormService);
    variableNotificationService = TestBed.inject(VariableNotificationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const variableNotification: IVariableNotification = { idVarNotification: 'CBA' };

      activatedRoute.data = of({ variableNotification });
      comp.ngOnInit();

      expect(comp.variableNotification).toEqual(variableNotification);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVariableNotification>>();
      const variableNotification = { idVarNotification: 'ABC' };
      jest.spyOn(variableNotificationFormService, 'getVariableNotification').mockReturnValue(variableNotification);
      jest.spyOn(variableNotificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ variableNotification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: variableNotification }));
      saveSubject.complete();

      // THEN
      expect(variableNotificationFormService.getVariableNotification).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(variableNotificationService.update).toHaveBeenCalledWith(expect.objectContaining(variableNotification));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVariableNotification>>();
      const variableNotification = { idVarNotification: 'ABC' };
      jest.spyOn(variableNotificationFormService, 'getVariableNotification').mockReturnValue({ idVarNotification: null });
      jest.spyOn(variableNotificationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ variableNotification: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: variableNotification }));
      saveSubject.complete();

      // THEN
      expect(variableNotificationFormService.getVariableNotification).toHaveBeenCalled();
      expect(variableNotificationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVariableNotification>>();
      const variableNotification = { idVarNotification: 'ABC' };
      jest.spyOn(variableNotificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ variableNotification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(variableNotificationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

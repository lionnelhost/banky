import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { MessageErreurService } from '../service/message-erreur.service';
import { IMessageErreur } from '../message-erreur.model';
import { MessageErreurFormService } from './message-erreur-form.service';

import { MessageErreurUpdateComponent } from './message-erreur-update.component';

describe('MessageErreur Management Update Component', () => {
  let comp: MessageErreurUpdateComponent;
  let fixture: ComponentFixture<MessageErreurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let messageErreurFormService: MessageErreurFormService;
  let messageErreurService: MessageErreurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MessageErreurUpdateComponent],
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
      .overrideTemplate(MessageErreurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MessageErreurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    messageErreurFormService = TestBed.inject(MessageErreurFormService);
    messageErreurService = TestBed.inject(MessageErreurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const messageErreur: IMessageErreur = { idMessageErreur: 'CBA' };

      activatedRoute.data = of({ messageErreur });
      comp.ngOnInit();

      expect(comp.messageErreur).toEqual(messageErreur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMessageErreur>>();
      const messageErreur = { idMessageErreur: 'ABC' };
      jest.spyOn(messageErreurFormService, 'getMessageErreur').mockReturnValue(messageErreur);
      jest.spyOn(messageErreurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ messageErreur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: messageErreur }));
      saveSubject.complete();

      // THEN
      expect(messageErreurFormService.getMessageErreur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(messageErreurService.update).toHaveBeenCalledWith(expect.objectContaining(messageErreur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMessageErreur>>();
      const messageErreur = { idMessageErreur: 'ABC' };
      jest.spyOn(messageErreurFormService, 'getMessageErreur').mockReturnValue({ idMessageErreur: null });
      jest.spyOn(messageErreurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ messageErreur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: messageErreur }));
      saveSubject.complete();

      // THEN
      expect(messageErreurFormService.getMessageErreur).toHaveBeenCalled();
      expect(messageErreurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMessageErreur>>();
      const messageErreur = { idMessageErreur: 'ABC' };
      jest.spyOn(messageErreurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ messageErreur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(messageErreurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { DispositifSignatureService } from '../service/dispositif-signature.service';
import { IDispositifSignature } from '../dispositif-signature.model';
import { DispositifSignatureFormService } from './dispositif-signature-form.service';

import { DispositifSignatureUpdateComponent } from './dispositif-signature-update.component';

describe('DispositifSignature Management Update Component', () => {
  let comp: DispositifSignatureUpdateComponent;
  let fixture: ComponentFixture<DispositifSignatureUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dispositifSignatureFormService: DispositifSignatureFormService;
  let dispositifSignatureService: DispositifSignatureService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DispositifSignatureUpdateComponent],
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
      .overrideTemplate(DispositifSignatureUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DispositifSignatureUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dispositifSignatureFormService = TestBed.inject(DispositifSignatureFormService);
    dispositifSignatureService = TestBed.inject(DispositifSignatureService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const dispositifSignature: IDispositifSignature = { idDispositif: 'CBA' };

      activatedRoute.data = of({ dispositifSignature });
      comp.ngOnInit();

      expect(comp.dispositifSignature).toEqual(dispositifSignature);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDispositifSignature>>();
      const dispositifSignature = { idDispositif: 'ABC' };
      jest.spyOn(dispositifSignatureFormService, 'getDispositifSignature').mockReturnValue(dispositifSignature);
      jest.spyOn(dispositifSignatureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dispositifSignature });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dispositifSignature }));
      saveSubject.complete();

      // THEN
      expect(dispositifSignatureFormService.getDispositifSignature).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dispositifSignatureService.update).toHaveBeenCalledWith(expect.objectContaining(dispositifSignature));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDispositifSignature>>();
      const dispositifSignature = { idDispositif: 'ABC' };
      jest.spyOn(dispositifSignatureFormService, 'getDispositifSignature').mockReturnValue({ idDispositif: null });
      jest.spyOn(dispositifSignatureService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dispositifSignature: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dispositifSignature }));
      saveSubject.complete();

      // THEN
      expect(dispositifSignatureFormService.getDispositifSignature).toHaveBeenCalled();
      expect(dispositifSignatureService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDispositifSignature>>();
      const dispositifSignature = { idDispositif: 'ABC' };
      jest.spyOn(dispositifSignatureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dispositifSignature });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dispositifSignatureService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

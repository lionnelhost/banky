import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { BanqueService } from '../service/banque.service';
import { IBanque } from '../banque.model';
import { BanqueFormService } from './banque-form.service';

import { BanqueUpdateComponent } from './banque-update.component';

describe('Banque Management Update Component', () => {
  let comp: BanqueUpdateComponent;
  let fixture: ComponentFixture<BanqueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let banqueFormService: BanqueFormService;
  let banqueService: BanqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [BanqueUpdateComponent],
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
      .overrideTemplate(BanqueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BanqueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    banqueFormService = TestBed.inject(BanqueFormService);
    banqueService = TestBed.inject(BanqueService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const banque: IBanque = { idBanque: 'CBA' };

      activatedRoute.data = of({ banque });
      comp.ngOnInit();

      expect(comp.banque).toEqual(banque);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBanque>>();
      const banque = { idBanque: 'ABC' };
      jest.spyOn(banqueFormService, 'getBanque').mockReturnValue(banque);
      jest.spyOn(banqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ banque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: banque }));
      saveSubject.complete();

      // THEN
      expect(banqueFormService.getBanque).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(banqueService.update).toHaveBeenCalledWith(expect.objectContaining(banque));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBanque>>();
      const banque = { idBanque: 'ABC' };
      jest.spyOn(banqueFormService, 'getBanque').mockReturnValue({ idBanque: null });
      jest.spyOn(banqueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ banque: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: banque }));
      saveSubject.complete();

      // THEN
      expect(banqueFormService.getBanque).toHaveBeenCalled();
      expect(banqueService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBanque>>();
      const banque = { idBanque: 'ABC' };
      jest.spyOn(banqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ banque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(banqueService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

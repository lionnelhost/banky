import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AbonneService } from '../service/abonne.service';
import { IAbonne } from '../abonne.model';
import { AbonneFormService } from './abonne-form.service';

import { AbonneUpdateComponent } from './abonne-update.component';

describe('Abonne Management Update Component', () => {
  let comp: AbonneUpdateComponent;
  let fixture: ComponentFixture<AbonneUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let abonneFormService: AbonneFormService;
  let abonneService: AbonneService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AbonneUpdateComponent],
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
      .overrideTemplate(AbonneUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AbonneUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    abonneFormService = TestBed.inject(AbonneFormService);
    abonneService = TestBed.inject(AbonneService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const abonne: IAbonne = { idAbonne: 'CBA' };

      activatedRoute.data = of({ abonne });
      comp.ngOnInit();

      expect(comp.abonne).toEqual(abonne);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAbonne>>();
      const abonne = { idAbonne: 'ABC' };
      jest.spyOn(abonneFormService, 'getAbonne').mockReturnValue(abonne);
      jest.spyOn(abonneService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ abonne });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: abonne }));
      saveSubject.complete();

      // THEN
      expect(abonneFormService.getAbonne).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(abonneService.update).toHaveBeenCalledWith(expect.objectContaining(abonne));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAbonne>>();
      const abonne = { idAbonne: 'ABC' };
      jest.spyOn(abonneFormService, 'getAbonne').mockReturnValue({ idAbonne: null });
      jest.spyOn(abonneService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ abonne: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: abonne }));
      saveSubject.complete();

      // THEN
      expect(abonneFormService.getAbonne).toHaveBeenCalled();
      expect(abonneService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAbonne>>();
      const abonne = { idAbonne: 'ABC' };
      jest.spyOn(abonneService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ abonne });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(abonneService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

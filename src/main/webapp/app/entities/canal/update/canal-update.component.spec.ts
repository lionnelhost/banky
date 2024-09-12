import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { CanalService } from '../service/canal.service';
import { ICanal } from '../canal.model';
import { CanalFormService } from './canal-form.service';

import { CanalUpdateComponent } from './canal-update.component';

describe('Canal Management Update Component', () => {
  let comp: CanalUpdateComponent;
  let fixture: ComponentFixture<CanalUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let canalFormService: CanalFormService;
  let canalService: CanalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CanalUpdateComponent],
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
      .overrideTemplate(CanalUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CanalUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    canalFormService = TestBed.inject(CanalFormService);
    canalService = TestBed.inject(CanalService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const canal: ICanal = { idCanal: 'CBA' };

      activatedRoute.data = of({ canal });
      comp.ngOnInit();

      expect(comp.canal).toEqual(canal);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICanal>>();
      const canal = { idCanal: 'ABC' };
      jest.spyOn(canalFormService, 'getCanal').mockReturnValue(canal);
      jest.spyOn(canalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ canal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: canal }));
      saveSubject.complete();

      // THEN
      expect(canalFormService.getCanal).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(canalService.update).toHaveBeenCalledWith(expect.objectContaining(canal));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICanal>>();
      const canal = { idCanal: 'ABC' };
      jest.spyOn(canalFormService, 'getCanal').mockReturnValue({ idCanal: null });
      jest.spyOn(canalService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ canal: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: canal }));
      saveSubject.complete();

      // THEN
      expect(canalFormService.getCanal).toHaveBeenCalled();
      expect(canalService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICanal>>();
      const canal = { idCanal: 'ABC' };
      jest.spyOn(canalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ canal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(canalService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

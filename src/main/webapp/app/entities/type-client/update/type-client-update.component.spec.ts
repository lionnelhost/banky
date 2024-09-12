import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { TypeClientService } from '../service/type-client.service';
import { ITypeClient } from '../type-client.model';
import { TypeClientFormService } from './type-client-form.service';

import { TypeClientUpdateComponent } from './type-client-update.component';

describe('TypeClient Management Update Component', () => {
  let comp: TypeClientUpdateComponent;
  let fixture: ComponentFixture<TypeClientUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let typeClientFormService: TypeClientFormService;
  let typeClientService: TypeClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TypeClientUpdateComponent],
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
      .overrideTemplate(TypeClientUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TypeClientUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    typeClientFormService = TestBed.inject(TypeClientFormService);
    typeClientService = TestBed.inject(TypeClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const typeClient: ITypeClient = { idTypeClient: 'CBA' };

      activatedRoute.data = of({ typeClient });
      comp.ngOnInit();

      expect(comp.typeClient).toEqual(typeClient);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypeClient>>();
      const typeClient = { idTypeClient: 'ABC' };
      jest.spyOn(typeClientFormService, 'getTypeClient').mockReturnValue(typeClient);
      jest.spyOn(typeClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typeClient }));
      saveSubject.complete();

      // THEN
      expect(typeClientFormService.getTypeClient).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(typeClientService.update).toHaveBeenCalledWith(expect.objectContaining(typeClient));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypeClient>>();
      const typeClient = { idTypeClient: 'ABC' };
      jest.spyOn(typeClientFormService, 'getTypeClient').mockReturnValue({ idTypeClient: null });
      jest.spyOn(typeClientService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeClient: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typeClient }));
      saveSubject.complete();

      // THEN
      expect(typeClientFormService.getTypeClient).toHaveBeenCalled();
      expect(typeClientService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypeClient>>();
      const typeClient = { idTypeClient: 'ABC' };
      jest.spyOn(typeClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(typeClientService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

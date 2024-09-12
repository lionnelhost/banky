import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { TypeTransactionService } from '../service/type-transaction.service';
import { ITypeTransaction } from '../type-transaction.model';
import { TypeTransactionFormService } from './type-transaction-form.service';

import { TypeTransactionUpdateComponent } from './type-transaction-update.component';

describe('TypeTransaction Management Update Component', () => {
  let comp: TypeTransactionUpdateComponent;
  let fixture: ComponentFixture<TypeTransactionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let typeTransactionFormService: TypeTransactionFormService;
  let typeTransactionService: TypeTransactionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TypeTransactionUpdateComponent],
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
      .overrideTemplate(TypeTransactionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TypeTransactionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    typeTransactionFormService = TestBed.inject(TypeTransactionFormService);
    typeTransactionService = TestBed.inject(TypeTransactionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const typeTransaction: ITypeTransaction = { idTypeTransaction: 'CBA' };

      activatedRoute.data = of({ typeTransaction });
      comp.ngOnInit();

      expect(comp.typeTransaction).toEqual(typeTransaction);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypeTransaction>>();
      const typeTransaction = { idTypeTransaction: 'ABC' };
      jest.spyOn(typeTransactionFormService, 'getTypeTransaction').mockReturnValue(typeTransaction);
      jest.spyOn(typeTransactionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeTransaction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typeTransaction }));
      saveSubject.complete();

      // THEN
      expect(typeTransactionFormService.getTypeTransaction).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(typeTransactionService.update).toHaveBeenCalledWith(expect.objectContaining(typeTransaction));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypeTransaction>>();
      const typeTransaction = { idTypeTransaction: 'ABC' };
      jest.spyOn(typeTransactionFormService, 'getTypeTransaction').mockReturnValue({ idTypeTransaction: null });
      jest.spyOn(typeTransactionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeTransaction: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typeTransaction }));
      saveSubject.complete();

      // THEN
      expect(typeTransactionFormService.getTypeTransaction).toHaveBeenCalled();
      expect(typeTransactionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypeTransaction>>();
      const typeTransaction = { idTypeTransaction: 'ABC' };
      jest.spyOn(typeTransactionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeTransaction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(typeTransactionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

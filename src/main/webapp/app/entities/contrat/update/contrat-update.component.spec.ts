import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ITypeContrat } from 'app/entities/type-contrat/type-contrat.model';
import { TypeContratService } from 'app/entities/type-contrat/service/type-contrat.service';
import { ContratService } from '../service/contrat.service';
import { IContrat } from '../contrat.model';
import { ContratFormService } from './contrat-form.service';

import { ContratUpdateComponent } from './contrat-update.component';

describe('Contrat Management Update Component', () => {
  let comp: ContratUpdateComponent;
  let fixture: ComponentFixture<ContratUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contratFormService: ContratFormService;
  let contratService: ContratService;
  let typeContratService: TypeContratService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ContratUpdateComponent],
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
      .overrideTemplate(ContratUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContratUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contratFormService = TestBed.inject(ContratFormService);
    contratService = TestBed.inject(ContratService);
    typeContratService = TestBed.inject(TypeContratService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TypeContrat query and add missing value', () => {
      const contrat: IContrat = { idContrat: 'CBA' };
      const typeContrat: ITypeContrat = { idTypeContrat: '08b7d363-71cd-41b9-bd05-66bf63baba22' };
      contrat.typeContrat = typeContrat;

      const typeContratCollection: ITypeContrat[] = [{ idTypeContrat: 'df4e04be-0c61-450c-90e9-6ed6cc7ab59a' }];
      jest.spyOn(typeContratService, 'query').mockReturnValue(of(new HttpResponse({ body: typeContratCollection })));
      const additionalTypeContrats = [typeContrat];
      const expectedCollection: ITypeContrat[] = [...additionalTypeContrats, ...typeContratCollection];
      jest.spyOn(typeContratService, 'addTypeContratToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contrat });
      comp.ngOnInit();

      expect(typeContratService.query).toHaveBeenCalled();
      expect(typeContratService.addTypeContratToCollectionIfMissing).toHaveBeenCalledWith(
        typeContratCollection,
        ...additionalTypeContrats.map(expect.objectContaining),
      );
      expect(comp.typeContratsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contrat: IContrat = { idContrat: 'CBA' };
      const typeContrat: ITypeContrat = { idTypeContrat: '1e2f95b1-9f31-47c6-a4b3-835a5503e6f2' };
      contrat.typeContrat = typeContrat;

      activatedRoute.data = of({ contrat });
      comp.ngOnInit();

      expect(comp.typeContratsSharedCollection).toContain(typeContrat);
      expect(comp.contrat).toEqual(contrat);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContrat>>();
      const contrat = { idContrat: 'ABC' };
      jest.spyOn(contratFormService, 'getContrat').mockReturnValue(contrat);
      jest.spyOn(contratService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contrat }));
      saveSubject.complete();

      // THEN
      expect(contratFormService.getContrat).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contratService.update).toHaveBeenCalledWith(expect.objectContaining(contrat));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContrat>>();
      const contrat = { idContrat: 'ABC' };
      jest.spyOn(contratFormService, 'getContrat').mockReturnValue({ idContrat: null });
      jest.spyOn(contratService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contrat: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contrat }));
      saveSubject.complete();

      // THEN
      expect(contratFormService.getContrat).toHaveBeenCalled();
      expect(contratService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContrat>>();
      const contrat = { idContrat: 'ABC' };
      jest.spyOn(contratService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contratService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTypeContrat', () => {
      it('Should forward to typeContratService', () => {
        const entity = { idTypeContrat: 'ABC' };
        const entity2 = { idTypeContrat: 'CBA' };
        jest.spyOn(typeContratService, 'compareTypeContrat');
        comp.compareTypeContrat(entity, entity2);
        expect(typeContratService.compareTypeContrat).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

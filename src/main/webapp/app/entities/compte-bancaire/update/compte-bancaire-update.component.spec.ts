import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IContrat } from 'app/entities/contrat/contrat.model';
import { ContratService } from 'app/entities/contrat/service/contrat.service';
import { CompteBancaireService } from '../service/compte-bancaire.service';
import { ICompteBancaire } from '../compte-bancaire.model';
import { CompteBancaireFormService } from './compte-bancaire-form.service';

import { CompteBancaireUpdateComponent } from './compte-bancaire-update.component';

describe('CompteBancaire Management Update Component', () => {
  let comp: CompteBancaireUpdateComponent;
  let fixture: ComponentFixture<CompteBancaireUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let compteBancaireFormService: CompteBancaireFormService;
  let compteBancaireService: CompteBancaireService;
  let contratService: ContratService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CompteBancaireUpdateComponent],
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
      .overrideTemplate(CompteBancaireUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CompteBancaireUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    compteBancaireFormService = TestBed.inject(CompteBancaireFormService);
    compteBancaireService = TestBed.inject(CompteBancaireService);
    contratService = TestBed.inject(ContratService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Contrat query and add missing value', () => {
      const compteBancaire: ICompteBancaire = { idCompteBancaire: 'CBA' };
      const contrat: IContrat = { idContrat: 'b4760cd6-7587-4503-864c-ac1e91cf3ab2' };
      compteBancaire.contrat = contrat;

      const contratCollection: IContrat[] = [{ idContrat: '3752cd18-929b-495c-b067-2732233dbacc' }];
      jest.spyOn(contratService, 'query').mockReturnValue(of(new HttpResponse({ body: contratCollection })));
      const additionalContrats = [contrat];
      const expectedCollection: IContrat[] = [...additionalContrats, ...contratCollection];
      jest.spyOn(contratService, 'addContratToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ compteBancaire });
      comp.ngOnInit();

      expect(contratService.query).toHaveBeenCalled();
      expect(contratService.addContratToCollectionIfMissing).toHaveBeenCalledWith(
        contratCollection,
        ...additionalContrats.map(expect.objectContaining),
      );
      expect(comp.contratsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const compteBancaire: ICompteBancaire = { idCompteBancaire: 'CBA' };
      const contrat: IContrat = { idContrat: 'b029372a-7c91-42ad-8fdf-7a51ffe4998c' };
      compteBancaire.contrat = contrat;

      activatedRoute.data = of({ compteBancaire });
      comp.ngOnInit();

      expect(comp.contratsSharedCollection).toContain(contrat);
      expect(comp.compteBancaire).toEqual(compteBancaire);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompteBancaire>>();
      const compteBancaire = { idCompteBancaire: 'ABC' };
      jest.spyOn(compteBancaireFormService, 'getCompteBancaire').mockReturnValue(compteBancaire);
      jest.spyOn(compteBancaireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ compteBancaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: compteBancaire }));
      saveSubject.complete();

      // THEN
      expect(compteBancaireFormService.getCompteBancaire).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(compteBancaireService.update).toHaveBeenCalledWith(expect.objectContaining(compteBancaire));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompteBancaire>>();
      const compteBancaire = { idCompteBancaire: 'ABC' };
      jest.spyOn(compteBancaireFormService, 'getCompteBancaire').mockReturnValue({ idCompteBancaire: null });
      jest.spyOn(compteBancaireService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ compteBancaire: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: compteBancaire }));
      saveSubject.complete();

      // THEN
      expect(compteBancaireFormService.getCompteBancaire).toHaveBeenCalled();
      expect(compteBancaireService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompteBancaire>>();
      const compteBancaire = { idCompteBancaire: 'ABC' };
      jest.spyOn(compteBancaireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ compteBancaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(compteBancaireService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareContrat', () => {
      it('Should forward to contratService', () => {
        const entity = { idContrat: 'ABC' };
        const entity2 = { idContrat: 'CBA' };
        jest.spyOn(contratService, 'compareContrat');
        comp.compareContrat(entity, entity2);
        expect(contratService.compareContrat).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IContrat } from 'app/entities/contrat/contrat.model';
import { ContratService } from 'app/entities/contrat/service/contrat.service';
import { IAbonne } from 'app/entities/abonne/abonne.model';
import { AbonneService } from 'app/entities/abonne/service/abonne.service';
import { IContratAbonnement } from '../contrat-abonnement.model';
import { ContratAbonnementService } from '../service/contrat-abonnement.service';
import { ContratAbonnementFormService } from './contrat-abonnement-form.service';

import { ContratAbonnementUpdateComponent } from './contrat-abonnement-update.component';

describe('ContratAbonnement Management Update Component', () => {
  let comp: ContratAbonnementUpdateComponent;
  let fixture: ComponentFixture<ContratAbonnementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contratAbonnementFormService: ContratAbonnementFormService;
  let contratAbonnementService: ContratAbonnementService;
  let contratService: ContratService;
  let abonneService: AbonneService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ContratAbonnementUpdateComponent],
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
      .overrideTemplate(ContratAbonnementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContratAbonnementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contratAbonnementFormService = TestBed.inject(ContratAbonnementFormService);
    contratAbonnementService = TestBed.inject(ContratAbonnementService);
    contratService = TestBed.inject(ContratService);
    abonneService = TestBed.inject(AbonneService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Contrat query and add missing value', () => {
      const contratAbonnement: IContratAbonnement = { id: 456 };
      const contrat: IContrat = { idContrat: '7a6a5a0e-3f40-4824-9f90-1daef34eff31' };
      contratAbonnement.contrat = contrat;

      const contratCollection: IContrat[] = [{ idContrat: '8a1a25cb-4182-4b71-b7b5-7b35d6385ee0' }];
      jest.spyOn(contratService, 'query').mockReturnValue(of(new HttpResponse({ body: contratCollection })));
      const additionalContrats = [contrat];
      const expectedCollection: IContrat[] = [...additionalContrats, ...contratCollection];
      jest.spyOn(contratService, 'addContratToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contratAbonnement });
      comp.ngOnInit();

      expect(contratService.query).toHaveBeenCalled();
      expect(contratService.addContratToCollectionIfMissing).toHaveBeenCalledWith(
        contratCollection,
        ...additionalContrats.map(expect.objectContaining),
      );
      expect(comp.contratsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Abonne query and add missing value', () => {
      const contratAbonnement: IContratAbonnement = { id: 456 };
      const abonne: IAbonne = { idAbonne: '5b48cb88-5e3a-4b63-b538-ca8e66dffb76' };
      contratAbonnement.abonne = abonne;

      const abonneCollection: IAbonne[] = [{ idAbonne: 'f358e3fe-b5a4-4390-95fe-e6189eae1ddf' }];
      jest.spyOn(abonneService, 'query').mockReturnValue(of(new HttpResponse({ body: abonneCollection })));
      const additionalAbonnes = [abonne];
      const expectedCollection: IAbonne[] = [...additionalAbonnes, ...abonneCollection];
      jest.spyOn(abonneService, 'addAbonneToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contratAbonnement });
      comp.ngOnInit();

      expect(abonneService.query).toHaveBeenCalled();
      expect(abonneService.addAbonneToCollectionIfMissing).toHaveBeenCalledWith(
        abonneCollection,
        ...additionalAbonnes.map(expect.objectContaining),
      );
      expect(comp.abonnesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contratAbonnement: IContratAbonnement = { id: 456 };
      const contrat: IContrat = { idContrat: '9420c897-baf9-4936-9eb4-e2e051c2e937' };
      contratAbonnement.contrat = contrat;
      const abonne: IAbonne = { idAbonne: '07be2d68-02dd-42cb-a62e-cf8c4e744c5f' };
      contratAbonnement.abonne = abonne;

      activatedRoute.data = of({ contratAbonnement });
      comp.ngOnInit();

      expect(comp.contratsSharedCollection).toContain(contrat);
      expect(comp.abonnesSharedCollection).toContain(abonne);
      expect(comp.contratAbonnement).toEqual(contratAbonnement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContratAbonnement>>();
      const contratAbonnement = { id: 123 };
      jest.spyOn(contratAbonnementFormService, 'getContratAbonnement').mockReturnValue(contratAbonnement);
      jest.spyOn(contratAbonnementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contratAbonnement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contratAbonnement }));
      saveSubject.complete();

      // THEN
      expect(contratAbonnementFormService.getContratAbonnement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contratAbonnementService.update).toHaveBeenCalledWith(expect.objectContaining(contratAbonnement));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContratAbonnement>>();
      const contratAbonnement = { id: 123 };
      jest.spyOn(contratAbonnementFormService, 'getContratAbonnement').mockReturnValue({ id: null });
      jest.spyOn(contratAbonnementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contratAbonnement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contratAbonnement }));
      saveSubject.complete();

      // THEN
      expect(contratAbonnementFormService.getContratAbonnement).toHaveBeenCalled();
      expect(contratAbonnementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContratAbonnement>>();
      const contratAbonnement = { id: 123 };
      jest.spyOn(contratAbonnementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contratAbonnement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contratAbonnementService.update).toHaveBeenCalled();
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

    describe('compareAbonne', () => {
      it('Should forward to abonneService', () => {
        const entity = { idAbonne: 'ABC' };
        const entity2 = { idAbonne: 'CBA' };
        jest.spyOn(abonneService, 'compareAbonne');
        comp.compareAbonne(entity, entity2);
        expect(abonneService.compareAbonne).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IContrat } from 'app/entities/contrat/contrat.model';
import { ContratService } from 'app/entities/contrat/service/contrat.service';
import { IAbonne } from 'app/entities/abonne/abonne.model';
import { AbonneService } from 'app/entities/abonne/service/abonne.service';
import { ICompteBancaire } from 'app/entities/compte-bancaire/compte-bancaire.model';
import { CompteBancaireService } from 'app/entities/compte-bancaire/service/compte-bancaire.service';
import { IContratAbonnementCompte } from '../contrat-abonnement-compte.model';
import { ContratAbonnementCompteService } from '../service/contrat-abonnement-compte.service';
import { ContratAbonnementCompteFormService } from './contrat-abonnement-compte-form.service';

import { ContratAbonnementCompteUpdateComponent } from './contrat-abonnement-compte-update.component';

describe('ContratAbonnementCompte Management Update Component', () => {
  let comp: ContratAbonnementCompteUpdateComponent;
  let fixture: ComponentFixture<ContratAbonnementCompteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contratAbonnementCompteFormService: ContratAbonnementCompteFormService;
  let contratAbonnementCompteService: ContratAbonnementCompteService;
  let contratService: ContratService;
  let abonneService: AbonneService;
  let compteBancaireService: CompteBancaireService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ContratAbonnementCompteUpdateComponent],
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
      .overrideTemplate(ContratAbonnementCompteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContratAbonnementCompteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contratAbonnementCompteFormService = TestBed.inject(ContratAbonnementCompteFormService);
    contratAbonnementCompteService = TestBed.inject(ContratAbonnementCompteService);
    contratService = TestBed.inject(ContratService);
    abonneService = TestBed.inject(AbonneService);
    compteBancaireService = TestBed.inject(CompteBancaireService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Contrat query and add missing value', () => {
      const contratAbonnementCompte: IContratAbonnementCompte = { id: 456 };
      const contrat: IContrat = { idContrat: 'a7d20e2f-c2b3-4cc3-b5b4-6e4f012ff2c5' };
      contratAbonnementCompte.contrat = contrat;

      const contratCollection: IContrat[] = [{ idContrat: 'efe6e827-a446-45e2-966c-84b8a2a35e2e' }];
      jest.spyOn(contratService, 'query').mockReturnValue(of(new HttpResponse({ body: contratCollection })));
      const additionalContrats = [contrat];
      const expectedCollection: IContrat[] = [...additionalContrats, ...contratCollection];
      jest.spyOn(contratService, 'addContratToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contratAbonnementCompte });
      comp.ngOnInit();

      expect(contratService.query).toHaveBeenCalled();
      expect(contratService.addContratToCollectionIfMissing).toHaveBeenCalledWith(
        contratCollection,
        ...additionalContrats.map(expect.objectContaining),
      );
      expect(comp.contratsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Abonne query and add missing value', () => {
      const contratAbonnementCompte: IContratAbonnementCompte = { id: 456 };
      const abonne: IAbonne = { idAbonne: 'a6a29be7-ae04-4b18-99a2-1121ff8eef19' };
      contratAbonnementCompte.abonne = abonne;

      const abonneCollection: IAbonne[] = [{ idAbonne: 'c6458ce8-6470-4ab0-b75f-da9e0747fc7f' }];
      jest.spyOn(abonneService, 'query').mockReturnValue(of(new HttpResponse({ body: abonneCollection })));
      const additionalAbonnes = [abonne];
      const expectedCollection: IAbonne[] = [...additionalAbonnes, ...abonneCollection];
      jest.spyOn(abonneService, 'addAbonneToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contratAbonnementCompte });
      comp.ngOnInit();

      expect(abonneService.query).toHaveBeenCalled();
      expect(abonneService.addAbonneToCollectionIfMissing).toHaveBeenCalledWith(
        abonneCollection,
        ...additionalAbonnes.map(expect.objectContaining),
      );
      expect(comp.abonnesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CompteBancaire query and add missing value', () => {
      const contratAbonnementCompte: IContratAbonnementCompte = { id: 456 };
      const compteBancaire: ICompteBancaire = { idCompteBancaire: '474e08f1-c937-4f09-84c6-42fe3e622200' };
      contratAbonnementCompte.compteBancaire = compteBancaire;

      const compteBancaireCollection: ICompteBancaire[] = [{ idCompteBancaire: 'd35b896f-6fde-4a9d-ba6a-b2cfe2aaa905' }];
      jest.spyOn(compteBancaireService, 'query').mockReturnValue(of(new HttpResponse({ body: compteBancaireCollection })));
      const additionalCompteBancaires = [compteBancaire];
      const expectedCollection: ICompteBancaire[] = [...additionalCompteBancaires, ...compteBancaireCollection];
      jest.spyOn(compteBancaireService, 'addCompteBancaireToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contratAbonnementCompte });
      comp.ngOnInit();

      expect(compteBancaireService.query).toHaveBeenCalled();
      expect(compteBancaireService.addCompteBancaireToCollectionIfMissing).toHaveBeenCalledWith(
        compteBancaireCollection,
        ...additionalCompteBancaires.map(expect.objectContaining),
      );
      expect(comp.compteBancairesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contratAbonnementCompte: IContratAbonnementCompte = { id: 456 };
      const contrat: IContrat = { idContrat: 'c4bd509c-ea9c-4640-8284-97c90a13e0af' };
      contratAbonnementCompte.contrat = contrat;
      const abonne: IAbonne = { idAbonne: '004dd3c7-996e-4c40-bb26-62c4c3d83613' };
      contratAbonnementCompte.abonne = abonne;
      const compteBancaire: ICompteBancaire = { idCompteBancaire: '9157e138-acb3-414b-8b07-dc0923df5ad4' };
      contratAbonnementCompte.compteBancaire = compteBancaire;

      activatedRoute.data = of({ contratAbonnementCompte });
      comp.ngOnInit();

      expect(comp.contratsSharedCollection).toContain(contrat);
      expect(comp.abonnesSharedCollection).toContain(abonne);
      expect(comp.compteBancairesSharedCollection).toContain(compteBancaire);
      expect(comp.contratAbonnementCompte).toEqual(contratAbonnementCompte);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContratAbonnementCompte>>();
      const contratAbonnementCompte = { id: 123 };
      jest.spyOn(contratAbonnementCompteFormService, 'getContratAbonnementCompte').mockReturnValue(contratAbonnementCompte);
      jest.spyOn(contratAbonnementCompteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contratAbonnementCompte });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contratAbonnementCompte }));
      saveSubject.complete();

      // THEN
      expect(contratAbonnementCompteFormService.getContratAbonnementCompte).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contratAbonnementCompteService.update).toHaveBeenCalledWith(expect.objectContaining(contratAbonnementCompte));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContratAbonnementCompte>>();
      const contratAbonnementCompte = { id: 123 };
      jest.spyOn(contratAbonnementCompteFormService, 'getContratAbonnementCompte').mockReturnValue({ id: null });
      jest.spyOn(contratAbonnementCompteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contratAbonnementCompte: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contratAbonnementCompte }));
      saveSubject.complete();

      // THEN
      expect(contratAbonnementCompteFormService.getContratAbonnementCompte).toHaveBeenCalled();
      expect(contratAbonnementCompteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContratAbonnementCompte>>();
      const contratAbonnementCompte = { id: 123 };
      jest.spyOn(contratAbonnementCompteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contratAbonnementCompte });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contratAbonnementCompteService.update).toHaveBeenCalled();
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

    describe('compareCompteBancaire', () => {
      it('Should forward to compteBancaireService', () => {
        const entity = { idCompteBancaire: 'ABC' };
        const entity2 = { idCompteBancaire: 'CBA' };
        jest.spyOn(compteBancaireService, 'compareCompteBancaire');
        comp.compareCompteBancaire(entity, entity2);
        expect(compteBancaireService.compareCompteBancaire).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

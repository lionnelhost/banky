import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IBanque } from 'app/entities/banque/banque.model';
import { BanqueService } from 'app/entities/banque/service/banque.service';
import { AgenceService } from '../service/agence.service';
import { IAgence } from '../agence.model';
import { AgenceFormService } from './agence-form.service';

import { AgenceUpdateComponent } from './agence-update.component';

describe('Agence Management Update Component', () => {
  let comp: AgenceUpdateComponent;
  let fixture: ComponentFixture<AgenceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let agenceFormService: AgenceFormService;
  let agenceService: AgenceService;
  let banqueService: BanqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AgenceUpdateComponent],
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
      .overrideTemplate(AgenceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AgenceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    agenceFormService = TestBed.inject(AgenceFormService);
    agenceService = TestBed.inject(AgenceService);
    banqueService = TestBed.inject(BanqueService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Banque query and add missing value', () => {
      const agence: IAgence = { idAgence: 'CBA' };
      const banque: IBanque = { idBanque: 'd5550cc0-c760-4e35-a290-7b6936e4931e' };
      agence.banque = banque;

      const banqueCollection: IBanque[] = [{ idBanque: 'fd5b944e-6a3d-42f7-a9e3-73850a8c48dc' }];
      jest.spyOn(banqueService, 'query').mockReturnValue(of(new HttpResponse({ body: banqueCollection })));
      const additionalBanques = [banque];
      const expectedCollection: IBanque[] = [...additionalBanques, ...banqueCollection];
      jest.spyOn(banqueService, 'addBanqueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agence });
      comp.ngOnInit();

      expect(banqueService.query).toHaveBeenCalled();
      expect(banqueService.addBanqueToCollectionIfMissing).toHaveBeenCalledWith(
        banqueCollection,
        ...additionalBanques.map(expect.objectContaining),
      );
      expect(comp.banquesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const agence: IAgence = { idAgence: 'CBA' };
      const banque: IBanque = { idBanque: '16022053-19a3-4b2a-b082-a8fa94dccd12' };
      agence.banque = banque;

      activatedRoute.data = of({ agence });
      comp.ngOnInit();

      expect(comp.banquesSharedCollection).toContain(banque);
      expect(comp.agence).toEqual(agence);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgence>>();
      const agence = { idAgence: 'ABC' };
      jest.spyOn(agenceFormService, 'getAgence').mockReturnValue(agence);
      jest.spyOn(agenceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agence }));
      saveSubject.complete();

      // THEN
      expect(agenceFormService.getAgence).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(agenceService.update).toHaveBeenCalledWith(expect.objectContaining(agence));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgence>>();
      const agence = { idAgence: 'ABC' };
      jest.spyOn(agenceFormService, 'getAgence').mockReturnValue({ idAgence: null });
      jest.spyOn(agenceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agence: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agence }));
      saveSubject.complete();

      // THEN
      expect(agenceFormService.getAgence).toHaveBeenCalled();
      expect(agenceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgence>>();
      const agence = { idAgence: 'ABC' };
      jest.spyOn(agenceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(agenceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareBanque', () => {
      it('Should forward to banqueService', () => {
        const entity = { idBanque: 'ABC' };
        const entity2 = { idBanque: 'CBA' };
        jest.spyOn(banqueService, 'compareBanque');
        comp.compareBanque(entity, entity2);
        expect(banqueService.compareBanque).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

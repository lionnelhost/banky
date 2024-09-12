import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ICanal } from 'app/entities/canal/canal.model';
import { CanalService } from 'app/entities/canal/service/canal.service';
import { ITypeTransaction } from 'app/entities/type-transaction/type-transaction.model';
import { TypeTransactionService } from 'app/entities/type-transaction/service/type-transaction.service';
import { IDispositifSignature } from 'app/entities/dispositif-signature/dispositif-signature.model';
import { DispositifSignatureService } from 'app/entities/dispositif-signature/service/dispositif-signature.service';
import { IDispositifSercurite } from '../dispositif-sercurite.model';
import { DispositifSercuriteService } from '../service/dispositif-sercurite.service';
import { DispositifSercuriteFormService } from './dispositif-sercurite-form.service';

import { DispositifSercuriteUpdateComponent } from './dispositif-sercurite-update.component';

describe('DispositifSercurite Management Update Component', () => {
  let comp: DispositifSercuriteUpdateComponent;
  let fixture: ComponentFixture<DispositifSercuriteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dispositifSercuriteFormService: DispositifSercuriteFormService;
  let dispositifSercuriteService: DispositifSercuriteService;
  let canalService: CanalService;
  let typeTransactionService: TypeTransactionService;
  let dispositifSignatureService: DispositifSignatureService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DispositifSercuriteUpdateComponent],
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
      .overrideTemplate(DispositifSercuriteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DispositifSercuriteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dispositifSercuriteFormService = TestBed.inject(DispositifSercuriteFormService);
    dispositifSercuriteService = TestBed.inject(DispositifSercuriteService);
    canalService = TestBed.inject(CanalService);
    typeTransactionService = TestBed.inject(TypeTransactionService);
    dispositifSignatureService = TestBed.inject(DispositifSignatureService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Canal query and add missing value', () => {
      const dispositifSercurite: IDispositifSercurite = { id: 456 };
      const canal: ICanal = { idCanal: '9d9ce93b-4b25-46bc-a2da-1cf49f9400c5' };
      dispositifSercurite.canal = canal;

      const canalCollection: ICanal[] = [{ idCanal: '03d8bfc4-6e95-4f6b-9a3c-720ead7833dc' }];
      jest.spyOn(canalService, 'query').mockReturnValue(of(new HttpResponse({ body: canalCollection })));
      const additionalCanals = [canal];
      const expectedCollection: ICanal[] = [...additionalCanals, ...canalCollection];
      jest.spyOn(canalService, 'addCanalToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dispositifSercurite });
      comp.ngOnInit();

      expect(canalService.query).toHaveBeenCalled();
      expect(canalService.addCanalToCollectionIfMissing).toHaveBeenCalledWith(
        canalCollection,
        ...additionalCanals.map(expect.objectContaining),
      );
      expect(comp.canalsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TypeTransaction query and add missing value', () => {
      const dispositifSercurite: IDispositifSercurite = { id: 456 };
      const typeTransaction: ITypeTransaction = { idTypeTransaction: '77d1fb6d-7dea-49b9-910e-719fcd216de1' };
      dispositifSercurite.typeTransaction = typeTransaction;

      const typeTransactionCollection: ITypeTransaction[] = [{ idTypeTransaction: '46aad1c2-af4a-4f38-95c8-7dbdb87b807a' }];
      jest.spyOn(typeTransactionService, 'query').mockReturnValue(of(new HttpResponse({ body: typeTransactionCollection })));
      const additionalTypeTransactions = [typeTransaction];
      const expectedCollection: ITypeTransaction[] = [...additionalTypeTransactions, ...typeTransactionCollection];
      jest.spyOn(typeTransactionService, 'addTypeTransactionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dispositifSercurite });
      comp.ngOnInit();

      expect(typeTransactionService.query).toHaveBeenCalled();
      expect(typeTransactionService.addTypeTransactionToCollectionIfMissing).toHaveBeenCalledWith(
        typeTransactionCollection,
        ...additionalTypeTransactions.map(expect.objectContaining),
      );
      expect(comp.typeTransactionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DispositifSignature query and add missing value', () => {
      const dispositifSercurite: IDispositifSercurite = { id: 456 };
      const dispositifSignature: IDispositifSignature = { idDispositif: '99133d13-3fa5-4c0e-bf13-53ac8f46deaf' };
      dispositifSercurite.dispositifSignature = dispositifSignature;

      const dispositifSignatureCollection: IDispositifSignature[] = [{ idDispositif: '72ebdc00-ae22-49dc-901b-2f68f352b6c3' }];
      jest.spyOn(dispositifSignatureService, 'query').mockReturnValue(of(new HttpResponse({ body: dispositifSignatureCollection })));
      const additionalDispositifSignatures = [dispositifSignature];
      const expectedCollection: IDispositifSignature[] = [...additionalDispositifSignatures, ...dispositifSignatureCollection];
      jest.spyOn(dispositifSignatureService, 'addDispositifSignatureToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dispositifSercurite });
      comp.ngOnInit();

      expect(dispositifSignatureService.query).toHaveBeenCalled();
      expect(dispositifSignatureService.addDispositifSignatureToCollectionIfMissing).toHaveBeenCalledWith(
        dispositifSignatureCollection,
        ...additionalDispositifSignatures.map(expect.objectContaining),
      );
      expect(comp.dispositifSignaturesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dispositifSercurite: IDispositifSercurite = { id: 456 };
      const canal: ICanal = { idCanal: '04b7c5ec-dfa6-45a0-bf90-723abe5e45fc' };
      dispositifSercurite.canal = canal;
      const typeTransaction: ITypeTransaction = { idTypeTransaction: '73d51873-9061-4a4b-832e-746856d963c9' };
      dispositifSercurite.typeTransaction = typeTransaction;
      const dispositifSignature: IDispositifSignature = { idDispositif: '52850e02-033f-481e-be65-061f0404ce01' };
      dispositifSercurite.dispositifSignature = dispositifSignature;

      activatedRoute.data = of({ dispositifSercurite });
      comp.ngOnInit();

      expect(comp.canalsSharedCollection).toContain(canal);
      expect(comp.typeTransactionsSharedCollection).toContain(typeTransaction);
      expect(comp.dispositifSignaturesSharedCollection).toContain(dispositifSignature);
      expect(comp.dispositifSercurite).toEqual(dispositifSercurite);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDispositifSercurite>>();
      const dispositifSercurite = { id: 123 };
      jest.spyOn(dispositifSercuriteFormService, 'getDispositifSercurite').mockReturnValue(dispositifSercurite);
      jest.spyOn(dispositifSercuriteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dispositifSercurite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dispositifSercurite }));
      saveSubject.complete();

      // THEN
      expect(dispositifSercuriteFormService.getDispositifSercurite).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dispositifSercuriteService.update).toHaveBeenCalledWith(expect.objectContaining(dispositifSercurite));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDispositifSercurite>>();
      const dispositifSercurite = { id: 123 };
      jest.spyOn(dispositifSercuriteFormService, 'getDispositifSercurite').mockReturnValue({ id: null });
      jest.spyOn(dispositifSercuriteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dispositifSercurite: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dispositifSercurite }));
      saveSubject.complete();

      // THEN
      expect(dispositifSercuriteFormService.getDispositifSercurite).toHaveBeenCalled();
      expect(dispositifSercuriteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDispositifSercurite>>();
      const dispositifSercurite = { id: 123 };
      jest.spyOn(dispositifSercuriteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dispositifSercurite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dispositifSercuriteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCanal', () => {
      it('Should forward to canalService', () => {
        const entity = { idCanal: 'ABC' };
        const entity2 = { idCanal: 'CBA' };
        jest.spyOn(canalService, 'compareCanal');
        comp.compareCanal(entity, entity2);
        expect(canalService.compareCanal).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTypeTransaction', () => {
      it('Should forward to typeTransactionService', () => {
        const entity = { idTypeTransaction: 'ABC' };
        const entity2 = { idTypeTransaction: 'CBA' };
        jest.spyOn(typeTransactionService, 'compareTypeTransaction');
        comp.compareTypeTransaction(entity, entity2);
        expect(typeTransactionService.compareTypeTransaction).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDispositifSignature', () => {
      it('Should forward to dispositifSignatureService', () => {
        const entity = { idDispositif: 'ABC' };
        const entity2 = { idDispositif: 'CBA' };
        jest.spyOn(dispositifSignatureService, 'compareDispositifSignature');
        comp.compareDispositifSignature(entity, entity2);
        expect(dispositifSignatureService.compareDispositifSignature).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

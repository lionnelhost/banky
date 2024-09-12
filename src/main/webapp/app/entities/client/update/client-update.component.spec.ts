import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IContrat } from 'app/entities/contrat/contrat.model';
import { ContratService } from 'app/entities/contrat/service/contrat.service';
import { ITypeClient } from 'app/entities/type-client/type-client.model';
import { TypeClientService } from 'app/entities/type-client/service/type-client.service';
import { IClient } from '../client.model';
import { ClientService } from '../service/client.service';
import { ClientFormService } from './client-form.service';

import { ClientUpdateComponent } from './client-update.component';

describe('Client Management Update Component', () => {
  let comp: ClientUpdateComponent;
  let fixture: ComponentFixture<ClientUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let clientFormService: ClientFormService;
  let clientService: ClientService;
  let contratService: ContratService;
  let typeClientService: TypeClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ClientUpdateComponent],
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
      .overrideTemplate(ClientUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClientUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    clientFormService = TestBed.inject(ClientFormService);
    clientService = TestBed.inject(ClientService);
    contratService = TestBed.inject(ContratService);
    typeClientService = TestBed.inject(TypeClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call contrat query and add missing value', () => {
      const client: IClient = { idClient: 'CBA' };
      const contrat: IContrat = { idContrat: '6c17246d-14f9-4737-9c78-0cd007f5c106' };
      client.contrat = contrat;

      const contratCollection: IContrat[] = [{ idContrat: '92387c87-ec10-4985-9914-c3abcc69b026' }];
      jest.spyOn(contratService, 'query').mockReturnValue(of(new HttpResponse({ body: contratCollection })));
      const expectedCollection: IContrat[] = [contrat, ...contratCollection];
      jest.spyOn(contratService, 'addContratToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ client });
      comp.ngOnInit();

      expect(contratService.query).toHaveBeenCalled();
      expect(contratService.addContratToCollectionIfMissing).toHaveBeenCalledWith(contratCollection, contrat);
      expect(comp.contratsCollection).toEqual(expectedCollection);
    });

    it('Should call TypeClient query and add missing value', () => {
      const client: IClient = { idClient: 'CBA' };
      const typeClient: ITypeClient = { idTypeClient: 'f3fecdf9-3f26-46f2-a6c6-5ca0ce0d4524' };
      client.typeClient = typeClient;

      const typeClientCollection: ITypeClient[] = [{ idTypeClient: '20d3a154-23de-4bca-8505-c22cea3787c1' }];
      jest.spyOn(typeClientService, 'query').mockReturnValue(of(new HttpResponse({ body: typeClientCollection })));
      const additionalTypeClients = [typeClient];
      const expectedCollection: ITypeClient[] = [...additionalTypeClients, ...typeClientCollection];
      jest.spyOn(typeClientService, 'addTypeClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ client });
      comp.ngOnInit();

      expect(typeClientService.query).toHaveBeenCalled();
      expect(typeClientService.addTypeClientToCollectionIfMissing).toHaveBeenCalledWith(
        typeClientCollection,
        ...additionalTypeClients.map(expect.objectContaining),
      );
      expect(comp.typeClientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const client: IClient = { idClient: 'CBA' };
      const contrat: IContrat = { idContrat: '86e178c7-9284-47bf-9b71-62ec9d2d23e9' };
      client.contrat = contrat;
      const typeClient: ITypeClient = { idTypeClient: 'f0041810-d05d-43a6-bef3-143f8fa7d46c' };
      client.typeClient = typeClient;

      activatedRoute.data = of({ client });
      comp.ngOnInit();

      expect(comp.contratsCollection).toContain(contrat);
      expect(comp.typeClientsSharedCollection).toContain(typeClient);
      expect(comp.client).toEqual(client);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClient>>();
      const client = { idClient: 'ABC' };
      jest.spyOn(clientFormService, 'getClient').mockReturnValue(client);
      jest.spyOn(clientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ client });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: client }));
      saveSubject.complete();

      // THEN
      expect(clientFormService.getClient).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(clientService.update).toHaveBeenCalledWith(expect.objectContaining(client));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClient>>();
      const client = { idClient: 'ABC' };
      jest.spyOn(clientFormService, 'getClient').mockReturnValue({ idClient: null });
      jest.spyOn(clientService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ client: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: client }));
      saveSubject.complete();

      // THEN
      expect(clientFormService.getClient).toHaveBeenCalled();
      expect(clientService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClient>>();
      const client = { idClient: 'ABC' };
      jest.spyOn(clientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ client });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(clientService.update).toHaveBeenCalled();
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

    describe('compareTypeClient', () => {
      it('Should forward to typeClientService', () => {
        const entity = { idTypeClient: 'ABC' };
        const entity2 = { idTypeClient: 'CBA' };
        jest.spyOn(typeClientService, 'compareTypeClient');
        comp.compareTypeClient(entity, entity2);
        expect(typeClientService.compareTypeClient).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

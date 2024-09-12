jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ContratAbonnementCompteService } from '../service/contrat-abonnement-compte.service';

import { ContratAbonnementCompteDeleteDialogComponent } from './contrat-abonnement-compte-delete-dialog.component';

describe('ContratAbonnementCompte Management Delete Component', () => {
  let comp: ContratAbonnementCompteDeleteDialogComponent;
  let fixture: ComponentFixture<ContratAbonnementCompteDeleteDialogComponent>;
  let service: ContratAbonnementCompteService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ContratAbonnementCompteDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(ContratAbonnementCompteDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ContratAbonnementCompteDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ContratAbonnementCompteService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});

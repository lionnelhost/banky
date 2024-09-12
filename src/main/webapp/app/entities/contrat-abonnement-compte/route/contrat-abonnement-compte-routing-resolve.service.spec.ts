import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IContratAbonnementCompte } from '../contrat-abonnement-compte.model';
import { ContratAbonnementCompteService } from '../service/contrat-abonnement-compte.service';

import contratAbonnementCompteResolve from './contrat-abonnement-compte-routing-resolve.service';

describe('ContratAbonnementCompte routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ContratAbonnementCompteService;
  let resultContratAbonnementCompte: IContratAbonnementCompte | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(ContratAbonnementCompteService);
    resultContratAbonnementCompte = undefined;
  });

  describe('resolve', () => {
    it('should return IContratAbonnementCompte returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        contratAbonnementCompteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultContratAbonnementCompte = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultContratAbonnementCompte).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        contratAbonnementCompteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultContratAbonnementCompte = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultContratAbonnementCompte).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IContratAbonnementCompte>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        contratAbonnementCompteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultContratAbonnementCompte = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultContratAbonnementCompte).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IContrat } from '../contrat.model';
import { ContratService } from '../service/contrat.service';

import contratResolve from './contrat-routing-resolve.service';

describe('Contrat routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ContratService;
  let resultContrat: IContrat | null | undefined;

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
    service = TestBed.inject(ContratService);
    resultContrat = undefined;
  });

  describe('resolve', () => {
    it('should return IContrat returned by find', () => {
      // GIVEN
      service.find = jest.fn(idContrat => of(new HttpResponse({ body: { idContrat } })));
      mockActivatedRouteSnapshot.params = { idContrat: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        contratResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultContrat = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultContrat).toEqual({ idContrat: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        contratResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultContrat = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultContrat).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IContrat>({ body: null })));
      mockActivatedRouteSnapshot.params = { idContrat: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        contratResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultContrat = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultContrat).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

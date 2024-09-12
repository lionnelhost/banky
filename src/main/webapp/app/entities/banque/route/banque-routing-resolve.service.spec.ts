import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IBanque } from '../banque.model';
import { BanqueService } from '../service/banque.service';

import banqueResolve from './banque-routing-resolve.service';

describe('Banque routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: BanqueService;
  let resultBanque: IBanque | null | undefined;

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
    service = TestBed.inject(BanqueService);
    resultBanque = undefined;
  });

  describe('resolve', () => {
    it('should return IBanque returned by find', () => {
      // GIVEN
      service.find = jest.fn(idBanque => of(new HttpResponse({ body: { idBanque } })));
      mockActivatedRouteSnapshot.params = { idBanque: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        banqueResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultBanque = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultBanque).toEqual({ idBanque: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        banqueResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultBanque = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBanque).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IBanque>({ body: null })));
      mockActivatedRouteSnapshot.params = { idBanque: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        banqueResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultBanque = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultBanque).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

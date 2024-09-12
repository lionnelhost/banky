import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAgence } from '../agence.model';
import { AgenceService } from '../service/agence.service';

import agenceResolve from './agence-routing-resolve.service';

describe('Agence routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AgenceService;
  let resultAgence: IAgence | null | undefined;

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
    service = TestBed.inject(AgenceService);
    resultAgence = undefined;
  });

  describe('resolve', () => {
    it('should return IAgence returned by find', () => {
      // GIVEN
      service.find = jest.fn(idAgence => of(new HttpResponse({ body: { idAgence } })));
      mockActivatedRouteSnapshot.params = { idAgence: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        agenceResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAgence = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultAgence).toEqual({ idAgence: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        agenceResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAgence = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAgence).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAgence>({ body: null })));
      mockActivatedRouteSnapshot.params = { idAgence: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        agenceResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAgence = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultAgence).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

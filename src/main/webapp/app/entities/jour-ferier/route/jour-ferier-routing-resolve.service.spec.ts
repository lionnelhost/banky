import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IJourFerier } from '../jour-ferier.model';
import { JourFerierService } from '../service/jour-ferier.service';

import jourFerierResolve from './jour-ferier-routing-resolve.service';

describe('JourFerier routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: JourFerierService;
  let resultJourFerier: IJourFerier | null | undefined;

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
    service = TestBed.inject(JourFerierService);
    resultJourFerier = undefined;
  });

  describe('resolve', () => {
    it('should return IJourFerier returned by find', () => {
      // GIVEN
      service.find = jest.fn(idJourFerie => of(new HttpResponse({ body: { idJourFerie } })));
      mockActivatedRouteSnapshot.params = { idJourFerie: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        jourFerierResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultJourFerier = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultJourFerier).toEqual({ idJourFerie: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        jourFerierResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultJourFerier = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultJourFerier).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IJourFerier>({ body: null })));
      mockActivatedRouteSnapshot.params = { idJourFerie: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        jourFerierResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultJourFerier = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultJourFerier).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

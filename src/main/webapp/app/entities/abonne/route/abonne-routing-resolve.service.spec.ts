import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAbonne } from '../abonne.model';
import { AbonneService } from '../service/abonne.service';

import abonneResolve from './abonne-routing-resolve.service';

describe('Abonne routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AbonneService;
  let resultAbonne: IAbonne | null | undefined;

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
    service = TestBed.inject(AbonneService);
    resultAbonne = undefined;
  });

  describe('resolve', () => {
    it('should return IAbonne returned by find', () => {
      // GIVEN
      service.find = jest.fn(idAbonne => of(new HttpResponse({ body: { idAbonne } })));
      mockActivatedRouteSnapshot.params = { idAbonne: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        abonneResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAbonne = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultAbonne).toEqual({ idAbonne: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        abonneResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAbonne = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAbonne).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAbonne>({ body: null })));
      mockActivatedRouteSnapshot.params = { idAbonne: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        abonneResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAbonne = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultAbonne).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IParametrageGlobal } from '../parametrage-global.model';
import { ParametrageGlobalService } from '../service/parametrage-global.service';

import parametrageGlobalResolve from './parametrage-global-routing-resolve.service';

describe('ParametrageGlobal routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ParametrageGlobalService;
  let resultParametrageGlobal: IParametrageGlobal | null | undefined;

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
    service = TestBed.inject(ParametrageGlobalService);
    resultParametrageGlobal = undefined;
  });

  describe('resolve', () => {
    it('should return IParametrageGlobal returned by find', () => {
      // GIVEN
      service.find = jest.fn(idParamGlobal => of(new HttpResponse({ body: { idParamGlobal } })));
      mockActivatedRouteSnapshot.params = { idParamGlobal: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        parametrageGlobalResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultParametrageGlobal = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultParametrageGlobal).toEqual({ idParamGlobal: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        parametrageGlobalResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultParametrageGlobal = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultParametrageGlobal).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IParametrageGlobal>({ body: null })));
      mockActivatedRouteSnapshot.params = { idParamGlobal: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        parametrageGlobalResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultParametrageGlobal = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultParametrageGlobal).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

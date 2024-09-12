import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ICanal } from '../canal.model';
import { CanalService } from '../service/canal.service';

import canalResolve from './canal-routing-resolve.service';

describe('Canal routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: CanalService;
  let resultCanal: ICanal | null | undefined;

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
    service = TestBed.inject(CanalService);
    resultCanal = undefined;
  });

  describe('resolve', () => {
    it('should return ICanal returned by find', () => {
      // GIVEN
      service.find = jest.fn(idCanal => of(new HttpResponse({ body: { idCanal } })));
      mockActivatedRouteSnapshot.params = { idCanal: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        canalResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCanal = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultCanal).toEqual({ idCanal: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        canalResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCanal = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCanal).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICanal>({ body: null })));
      mockActivatedRouteSnapshot.params = { idCanal: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        canalResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCanal = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultCanal).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

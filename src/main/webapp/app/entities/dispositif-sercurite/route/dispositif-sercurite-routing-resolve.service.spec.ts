import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IDispositifSercurite } from '../dispositif-sercurite.model';
import { DispositifSercuriteService } from '../service/dispositif-sercurite.service';

import dispositifSercuriteResolve from './dispositif-sercurite-routing-resolve.service';

describe('DispositifSercurite routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: DispositifSercuriteService;
  let resultDispositifSercurite: IDispositifSercurite | null | undefined;

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
    service = TestBed.inject(DispositifSercuriteService);
    resultDispositifSercurite = undefined;
  });

  describe('resolve', () => {
    it('should return IDispositifSercurite returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        dispositifSercuriteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDispositifSercurite = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultDispositifSercurite).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        dispositifSercuriteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDispositifSercurite = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDispositifSercurite).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IDispositifSercurite>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        dispositifSercuriteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDispositifSercurite = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultDispositifSercurite).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

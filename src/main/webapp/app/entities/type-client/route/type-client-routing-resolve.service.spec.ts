import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ITypeClient } from '../type-client.model';
import { TypeClientService } from '../service/type-client.service';

import typeClientResolve from './type-client-routing-resolve.service';

describe('TypeClient routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: TypeClientService;
  let resultTypeClient: ITypeClient | null | undefined;

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
    service = TestBed.inject(TypeClientService);
    resultTypeClient = undefined;
  });

  describe('resolve', () => {
    it('should return ITypeClient returned by find', () => {
      // GIVEN
      service.find = jest.fn(idTypeClient => of(new HttpResponse({ body: { idTypeClient } })));
      mockActivatedRouteSnapshot.params = { idTypeClient: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        typeClientResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTypeClient = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultTypeClient).toEqual({ idTypeClient: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        typeClientResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTypeClient = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTypeClient).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITypeClient>({ body: null })));
      mockActivatedRouteSnapshot.params = { idTypeClient: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        typeClientResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTypeClient = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultTypeClient).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

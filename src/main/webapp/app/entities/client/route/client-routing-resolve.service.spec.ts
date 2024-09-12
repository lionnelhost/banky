import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IClient } from '../client.model';
import { ClientService } from '../service/client.service';

import clientResolve from './client-routing-resolve.service';

describe('Client routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ClientService;
  let resultClient: IClient | null | undefined;

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
    service = TestBed.inject(ClientService);
    resultClient = undefined;
  });

  describe('resolve', () => {
    it('should return IClient returned by find', () => {
      // GIVEN
      service.find = jest.fn(idClient => of(new HttpResponse({ body: { idClient } })));
      mockActivatedRouteSnapshot.params = { idClient: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        clientResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultClient = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultClient).toEqual({ idClient: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        clientResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultClient = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultClient).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IClient>({ body: null })));
      mockActivatedRouteSnapshot.params = { idClient: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        clientResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultClient = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultClient).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

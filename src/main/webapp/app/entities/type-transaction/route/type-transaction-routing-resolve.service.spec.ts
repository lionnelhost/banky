import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ITypeTransaction } from '../type-transaction.model';
import { TypeTransactionService } from '../service/type-transaction.service';

import typeTransactionResolve from './type-transaction-routing-resolve.service';

describe('TypeTransaction routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: TypeTransactionService;
  let resultTypeTransaction: ITypeTransaction | null | undefined;

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
    service = TestBed.inject(TypeTransactionService);
    resultTypeTransaction = undefined;
  });

  describe('resolve', () => {
    it('should return ITypeTransaction returned by find', () => {
      // GIVEN
      service.find = jest.fn(idTypeTransaction => of(new HttpResponse({ body: { idTypeTransaction } })));
      mockActivatedRouteSnapshot.params = { idTypeTransaction: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        typeTransactionResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTypeTransaction = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultTypeTransaction).toEqual({ idTypeTransaction: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        typeTransactionResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTypeTransaction = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTypeTransaction).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITypeTransaction>({ body: null })));
      mockActivatedRouteSnapshot.params = { idTypeTransaction: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        typeTransactionResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTypeTransaction = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultTypeTransaction).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

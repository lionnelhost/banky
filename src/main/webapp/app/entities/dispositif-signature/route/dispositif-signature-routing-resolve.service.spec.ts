import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IDispositifSignature } from '../dispositif-signature.model';
import { DispositifSignatureService } from '../service/dispositif-signature.service';

import dispositifSignatureResolve from './dispositif-signature-routing-resolve.service';

describe('DispositifSignature routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: DispositifSignatureService;
  let resultDispositifSignature: IDispositifSignature | null | undefined;

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
    service = TestBed.inject(DispositifSignatureService);
    resultDispositifSignature = undefined;
  });

  describe('resolve', () => {
    it('should return IDispositifSignature returned by find', () => {
      // GIVEN
      service.find = jest.fn(idDispositif => of(new HttpResponse({ body: { idDispositif } })));
      mockActivatedRouteSnapshot.params = { idDispositif: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        dispositifSignatureResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDispositifSignature = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultDispositifSignature).toEqual({ idDispositif: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        dispositifSignatureResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDispositifSignature = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDispositifSignature).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IDispositifSignature>({ body: null })));
      mockActivatedRouteSnapshot.params = { idDispositif: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        dispositifSignatureResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDispositifSignature = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultDispositifSignature).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

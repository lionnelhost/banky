import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IParametrageNotification } from '../parametrage-notification.model';
import { ParametrageNotificationService } from '../service/parametrage-notification.service';

import parametrageNotificationResolve from './parametrage-notification-routing-resolve.service';

describe('ParametrageNotification routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ParametrageNotificationService;
  let resultParametrageNotification: IParametrageNotification | null | undefined;

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
    service = TestBed.inject(ParametrageNotificationService);
    resultParametrageNotification = undefined;
  });

  describe('resolve', () => {
    it('should return IParametrageNotification returned by find', () => {
      // GIVEN
      service.find = jest.fn(idParamNotif => of(new HttpResponse({ body: { idParamNotif } })));
      mockActivatedRouteSnapshot.params = { idParamNotif: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        parametrageNotificationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultParametrageNotification = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultParametrageNotification).toEqual({ idParamNotif: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        parametrageNotificationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultParametrageNotification = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultParametrageNotification).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IParametrageNotification>({ body: null })));
      mockActivatedRouteSnapshot.params = { idParamNotif: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        parametrageNotificationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultParametrageNotification = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultParametrageNotification).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

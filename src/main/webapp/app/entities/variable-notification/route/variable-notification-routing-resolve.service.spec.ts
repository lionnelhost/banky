import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IVariableNotification } from '../variable-notification.model';
import { VariableNotificationService } from '../service/variable-notification.service';

import variableNotificationResolve from './variable-notification-routing-resolve.service';

describe('VariableNotification routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: VariableNotificationService;
  let resultVariableNotification: IVariableNotification | null | undefined;

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
    service = TestBed.inject(VariableNotificationService);
    resultVariableNotification = undefined;
  });

  describe('resolve', () => {
    it('should return IVariableNotification returned by find', () => {
      // GIVEN
      service.find = jest.fn(idVarNotification => of(new HttpResponse({ body: { idVarNotification } })));
      mockActivatedRouteSnapshot.params = { idVarNotification: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        variableNotificationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultVariableNotification = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultVariableNotification).toEqual({ idVarNotification: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        variableNotificationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultVariableNotification = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultVariableNotification).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IVariableNotification>({ body: null })));
      mockActivatedRouteSnapshot.params = { idVarNotification: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        variableNotificationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultVariableNotification = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultVariableNotification).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

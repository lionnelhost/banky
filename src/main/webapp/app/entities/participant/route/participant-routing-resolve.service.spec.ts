import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IParticipant } from '../participant.model';
import { ParticipantService } from '../service/participant.service';

import participantResolve from './participant-routing-resolve.service';

describe('Participant routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ParticipantService;
  let resultParticipant: IParticipant | null | undefined;

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
    service = TestBed.inject(ParticipantService);
    resultParticipant = undefined;
  });

  describe('resolve', () => {
    it('should return IParticipant returned by find', () => {
      // GIVEN
      service.find = jest.fn(idParticipant => of(new HttpResponse({ body: { idParticipant } })));
      mockActivatedRouteSnapshot.params = { idParticipant: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        participantResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultParticipant = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultParticipant).toEqual({ idParticipant: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        participantResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultParticipant = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultParticipant).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IParticipant>({ body: null })));
      mockActivatedRouteSnapshot.params = { idParticipant: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        participantResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultParticipant = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultParticipant).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

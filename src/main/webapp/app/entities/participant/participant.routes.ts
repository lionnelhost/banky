import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import ParticipantResolve from './route/participant-routing-resolve.service';

const participantRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/participant.component').then(m => m.ParticipantComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idParticipant/view',
    loadComponent: () => import('./detail/participant-detail.component').then(m => m.ParticipantDetailComponent),
    resolve: {
      participant: ParticipantResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/participant-update.component').then(m => m.ParticipantUpdateComponent),
    resolve: {
      participant: ParticipantResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idParticipant/edit',
    loadComponent: () => import('./update/participant-update.component').then(m => m.ParticipantUpdateComponent),
    resolve: {
      participant: ParticipantResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default participantRoute;

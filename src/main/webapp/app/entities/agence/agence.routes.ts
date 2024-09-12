import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import AgenceResolve from './route/agence-routing-resolve.service';

const agenceRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/agence.component').then(m => m.AgenceComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idAgence/view',
    loadComponent: () => import('./detail/agence-detail.component').then(m => m.AgenceDetailComponent),
    resolve: {
      agence: AgenceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/agence-update.component').then(m => m.AgenceUpdateComponent),
    resolve: {
      agence: AgenceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idAgence/edit',
    loadComponent: () => import('./update/agence-update.component').then(m => m.AgenceUpdateComponent),
    resolve: {
      agence: AgenceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default agenceRoute;

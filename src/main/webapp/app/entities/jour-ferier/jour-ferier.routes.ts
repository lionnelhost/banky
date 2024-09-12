import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import JourFerierResolve from './route/jour-ferier-routing-resolve.service';

const jourFerierRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/jour-ferier.component').then(m => m.JourFerierComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idJourFerie/view',
    loadComponent: () => import('./detail/jour-ferier-detail.component').then(m => m.JourFerierDetailComponent),
    resolve: {
      jourFerier: JourFerierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/jour-ferier-update.component').then(m => m.JourFerierUpdateComponent),
    resolve: {
      jourFerier: JourFerierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idJourFerie/edit',
    loadComponent: () => import('./update/jour-ferier-update.component').then(m => m.JourFerierUpdateComponent),
    resolve: {
      jourFerier: JourFerierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default jourFerierRoute;

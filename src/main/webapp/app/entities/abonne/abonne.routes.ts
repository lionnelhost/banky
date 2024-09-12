import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import AbonneResolve from './route/abonne-routing-resolve.service';

const abonneRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/abonne.component').then(m => m.AbonneComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idAbonne/view',
    loadComponent: () => import('./detail/abonne-detail.component').then(m => m.AbonneDetailComponent),
    resolve: {
      abonne: AbonneResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/abonne-update.component').then(m => m.AbonneUpdateComponent),
    resolve: {
      abonne: AbonneResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idAbonne/edit',
    loadComponent: () => import('./update/abonne-update.component').then(m => m.AbonneUpdateComponent),
    resolve: {
      abonne: AbonneResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default abonneRoute;

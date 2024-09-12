import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import ParametrageGlobalResolve from './route/parametrage-global-routing-resolve.service';

const parametrageGlobalRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/parametrage-global.component').then(m => m.ParametrageGlobalComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idParamGlobal/view',
    loadComponent: () => import('./detail/parametrage-global-detail.component').then(m => m.ParametrageGlobalDetailComponent),
    resolve: {
      parametrageGlobal: ParametrageGlobalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/parametrage-global-update.component').then(m => m.ParametrageGlobalUpdateComponent),
    resolve: {
      parametrageGlobal: ParametrageGlobalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idParamGlobal/edit',
    loadComponent: () => import('./update/parametrage-global-update.component').then(m => m.ParametrageGlobalUpdateComponent),
    resolve: {
      parametrageGlobal: ParametrageGlobalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default parametrageGlobalRoute;

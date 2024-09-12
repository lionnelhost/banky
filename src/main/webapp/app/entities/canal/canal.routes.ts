import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import CanalResolve from './route/canal-routing-resolve.service';

const canalRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/canal.component').then(m => m.CanalComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idCanal/view',
    loadComponent: () => import('./detail/canal-detail.component').then(m => m.CanalDetailComponent),
    resolve: {
      canal: CanalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/canal-update.component').then(m => m.CanalUpdateComponent),
    resolve: {
      canal: CanalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idCanal/edit',
    loadComponent: () => import('./update/canal-update.component').then(m => m.CanalUpdateComponent),
    resolve: {
      canal: CanalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default canalRoute;

import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import BanqueResolve from './route/banque-routing-resolve.service';

const banqueRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/banque.component').then(m => m.BanqueComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idBanque/view',
    loadComponent: () => import('./detail/banque-detail.component').then(m => m.BanqueDetailComponent),
    resolve: {
      banque: BanqueResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/banque-update.component').then(m => m.BanqueUpdateComponent),
    resolve: {
      banque: BanqueResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idBanque/edit',
    loadComponent: () => import('./update/banque-update.component').then(m => m.BanqueUpdateComponent),
    resolve: {
      banque: BanqueResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default banqueRoute;

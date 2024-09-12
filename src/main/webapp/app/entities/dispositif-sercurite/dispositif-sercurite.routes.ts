import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import DispositifSercuriteResolve from './route/dispositif-sercurite-routing-resolve.service';

const dispositifSercuriteRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/dispositif-sercurite.component').then(m => m.DispositifSercuriteComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/dispositif-sercurite-detail.component').then(m => m.DispositifSercuriteDetailComponent),
    resolve: {
      dispositifSercurite: DispositifSercuriteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/dispositif-sercurite-update.component').then(m => m.DispositifSercuriteUpdateComponent),
    resolve: {
      dispositifSercurite: DispositifSercuriteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/dispositif-sercurite-update.component').then(m => m.DispositifSercuriteUpdateComponent),
    resolve: {
      dispositifSercurite: DispositifSercuriteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default dispositifSercuriteRoute;

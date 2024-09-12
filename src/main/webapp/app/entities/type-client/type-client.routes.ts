import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import TypeClientResolve from './route/type-client-routing-resolve.service';

const typeClientRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/type-client.component').then(m => m.TypeClientComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idTypeClient/view',
    loadComponent: () => import('./detail/type-client-detail.component').then(m => m.TypeClientDetailComponent),
    resolve: {
      typeClient: TypeClientResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/type-client-update.component').then(m => m.TypeClientUpdateComponent),
    resolve: {
      typeClient: TypeClientResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idTypeClient/edit',
    loadComponent: () => import('./update/type-client-update.component').then(m => m.TypeClientUpdateComponent),
    resolve: {
      typeClient: TypeClientResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default typeClientRoute;

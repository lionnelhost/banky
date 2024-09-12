import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import TypeTransactionResolve from './route/type-transaction-routing-resolve.service';

const typeTransactionRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/type-transaction.component').then(m => m.TypeTransactionComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idTypeTransaction/view',
    loadComponent: () => import('./detail/type-transaction-detail.component').then(m => m.TypeTransactionDetailComponent),
    resolve: {
      typeTransaction: TypeTransactionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/type-transaction-update.component').then(m => m.TypeTransactionUpdateComponent),
    resolve: {
      typeTransaction: TypeTransactionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idTypeTransaction/edit',
    loadComponent: () => import('./update/type-transaction-update.component').then(m => m.TypeTransactionUpdateComponent),
    resolve: {
      typeTransaction: TypeTransactionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default typeTransactionRoute;

import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ContratAbonnementResolve from './route/contrat-abonnement-routing-resolve.service';

const contratAbonnementRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/contrat-abonnement.component').then(m => m.ContratAbonnementComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/contrat-abonnement-detail.component').then(m => m.ContratAbonnementDetailComponent),
    resolve: {
      contratAbonnement: ContratAbonnementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/contrat-abonnement-update.component').then(m => m.ContratAbonnementUpdateComponent),
    resolve: {
      contratAbonnement: ContratAbonnementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/contrat-abonnement-update.component').then(m => m.ContratAbonnementUpdateComponent),
    resolve: {
      contratAbonnement: ContratAbonnementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default contratAbonnementRoute;

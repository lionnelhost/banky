import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ContratAbonnementCompteResolve from './route/contrat-abonnement-compte-routing-resolve.service';

const contratAbonnementCompteRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/contrat-abonnement-compte.component').then(m => m.ContratAbonnementCompteComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/contrat-abonnement-compte-detail.component').then(m => m.ContratAbonnementCompteDetailComponent),
    resolve: {
      contratAbonnementCompte: ContratAbonnementCompteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/contrat-abonnement-compte-update.component').then(m => m.ContratAbonnementCompteUpdateComponent),
    resolve: {
      contratAbonnementCompte: ContratAbonnementCompteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/contrat-abonnement-compte-update.component').then(m => m.ContratAbonnementCompteUpdateComponent),
    resolve: {
      contratAbonnementCompte: ContratAbonnementCompteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default contratAbonnementCompteRoute;

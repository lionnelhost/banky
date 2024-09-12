import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import CompteBancaireResolve from './route/compte-bancaire-routing-resolve.service';

const compteBancaireRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/compte-bancaire.component').then(m => m.CompteBancaireComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idCompteBancaire/view',
    loadComponent: () => import('./detail/compte-bancaire-detail.component').then(m => m.CompteBancaireDetailComponent),
    resolve: {
      compteBancaire: CompteBancaireResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/compte-bancaire-update.component').then(m => m.CompteBancaireUpdateComponent),
    resolve: {
      compteBancaire: CompteBancaireResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idCompteBancaire/edit',
    loadComponent: () => import('./update/compte-bancaire-update.component').then(m => m.CompteBancaireUpdateComponent),
    resolve: {
      compteBancaire: CompteBancaireResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default compteBancaireRoute;

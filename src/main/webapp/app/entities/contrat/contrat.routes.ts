import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import ContratResolve from './route/contrat-routing-resolve.service';

const contratRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/contrat.component').then(m => m.ContratComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idContrat/view',
    loadComponent: () => import('./detail/contrat-detail.component').then(m => m.ContratDetailComponent),
    resolve: {
      contrat: ContratResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/contrat-update.component').then(m => m.ContratUpdateComponent),
    resolve: {
      contrat: ContratResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idContrat/edit',
    loadComponent: () => import('./update/contrat-update.component').then(m => m.ContratUpdateComponent),
    resolve: {
      contrat: ContratResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default contratRoute;

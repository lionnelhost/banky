import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import DispositifSignatureResolve from './route/dispositif-signature-routing-resolve.service';

const dispositifSignatureRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/dispositif-signature.component').then(m => m.DispositifSignatureComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idDispositif/view',
    loadComponent: () => import('./detail/dispositif-signature-detail.component').then(m => m.DispositifSignatureDetailComponent),
    resolve: {
      dispositifSignature: DispositifSignatureResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/dispositif-signature-update.component').then(m => m.DispositifSignatureUpdateComponent),
    resolve: {
      dispositifSignature: DispositifSignatureResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idDispositif/edit',
    loadComponent: () => import('./update/dispositif-signature-update.component').then(m => m.DispositifSignatureUpdateComponent),
    resolve: {
      dispositifSignature: DispositifSignatureResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default dispositifSignatureRoute;

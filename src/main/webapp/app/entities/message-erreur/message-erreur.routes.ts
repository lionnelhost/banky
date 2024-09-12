import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import MessageErreurResolve from './route/message-erreur-routing-resolve.service';

const messageErreurRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/message-erreur.component').then(m => m.MessageErreurComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idMessageErreur/view',
    loadComponent: () => import('./detail/message-erreur-detail.component').then(m => m.MessageErreurDetailComponent),
    resolve: {
      messageErreur: MessageErreurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/message-erreur-update.component').then(m => m.MessageErreurUpdateComponent),
    resolve: {
      messageErreur: MessageErreurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idMessageErreur/edit',
    loadComponent: () => import('./update/message-erreur-update.component').then(m => m.MessageErreurUpdateComponent),
    resolve: {
      messageErreur: MessageErreurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default messageErreurRoute;

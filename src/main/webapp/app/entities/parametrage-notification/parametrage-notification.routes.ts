import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import ParametrageNotificationResolve from './route/parametrage-notification-routing-resolve.service';

const parametrageNotificationRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/parametrage-notification.component').then(m => m.ParametrageNotificationComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idParamNotif/view',
    loadComponent: () => import('./detail/parametrage-notification-detail.component').then(m => m.ParametrageNotificationDetailComponent),
    resolve: {
      parametrageNotification: ParametrageNotificationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/parametrage-notification-update.component').then(m => m.ParametrageNotificationUpdateComponent),
    resolve: {
      parametrageNotification: ParametrageNotificationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idParamNotif/edit',
    loadComponent: () => import('./update/parametrage-notification-update.component').then(m => m.ParametrageNotificationUpdateComponent),
    resolve: {
      parametrageNotification: ParametrageNotificationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default parametrageNotificationRoute;

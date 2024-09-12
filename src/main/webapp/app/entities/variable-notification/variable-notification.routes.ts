import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import VariableNotificationResolve from './route/variable-notification-routing-resolve.service';

const variableNotificationRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/variable-notification.component').then(m => m.VariableNotificationComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idVarNotification/view',
    loadComponent: () => import('./detail/variable-notification-detail.component').then(m => m.VariableNotificationDetailComponent),
    resolve: {
      variableNotification: VariableNotificationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/variable-notification-update.component').then(m => m.VariableNotificationUpdateComponent),
    resolve: {
      variableNotification: VariableNotificationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':idVarNotification/edit',
    loadComponent: () => import('./update/variable-notification-update.component').then(m => m.VariableNotificationUpdateComponent),
    resolve: {
      variableNotification: VariableNotificationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default variableNotificationRoute;

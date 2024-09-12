import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'bankyApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'abonne',
    data: { pageTitle: 'bankyApp.abonne.home.title' },
    loadChildren: () => import('./abonne/abonne.routes'),
  },
  {
    path: 'agence',
    data: { pageTitle: 'bankyApp.agence.home.title' },
    loadChildren: () => import('./agence/agence.routes'),
  },
  {
    path: 'banque',
    data: { pageTitle: 'bankyApp.banque.home.title' },
    loadChildren: () => import('./banque/banque.routes'),
  },
  {
    path: 'canal',
    data: { pageTitle: 'bankyApp.canal.home.title' },
    loadChildren: () => import('./canal/canal.routes'),
  },
  {
    path: 'client',
    data: { pageTitle: 'bankyApp.client.home.title' },
    loadChildren: () => import('./client/client.routes'),
  },
  {
    path: 'compte-bancaire',
    data: { pageTitle: 'bankyApp.compteBancaire.home.title' },
    loadChildren: () => import('./compte-bancaire/compte-bancaire.routes'),
  },
  {
    path: 'contrat',
    data: { pageTitle: 'bankyApp.contrat.home.title' },
    loadChildren: () => import('./contrat/contrat.routes'),
  },
  {
    path: 'contrat-abonnement',
    data: { pageTitle: 'bankyApp.contratAbonnement.home.title' },
    loadChildren: () => import('./contrat-abonnement/contrat-abonnement.routes'),
  },
  {
    path: 'contrat-abonnement-compte',
    data: { pageTitle: 'bankyApp.contratAbonnementCompte.home.title' },
    loadChildren: () => import('./contrat-abonnement-compte/contrat-abonnement-compte.routes'),
  },
  {
    path: 'dispositif-sercurite',
    data: { pageTitle: 'bankyApp.dispositifSercurite.home.title' },
    loadChildren: () => import('./dispositif-sercurite/dispositif-sercurite.routes'),
  },
  {
    path: 'dispositif-signature',
    data: { pageTitle: 'bankyApp.dispositifSignature.home.title' },
    loadChildren: () => import('./dispositif-signature/dispositif-signature.routes'),
  },
  {
    path: 'jour-ferier',
    data: { pageTitle: 'bankyApp.jourFerier.home.title' },
    loadChildren: () => import('./jour-ferier/jour-ferier.routes'),
  },
  {
    path: 'message-erreur',
    data: { pageTitle: 'bankyApp.messageErreur.home.title' },
    loadChildren: () => import('./message-erreur/message-erreur.routes'),
  },
  {
    path: 'parametrage-global',
    data: { pageTitle: 'bankyApp.parametrageGlobal.home.title' },
    loadChildren: () => import('./parametrage-global/parametrage-global.routes'),
  },
  {
    path: 'parametrage-notification',
    data: { pageTitle: 'bankyApp.parametrageNotification.home.title' },
    loadChildren: () => import('./parametrage-notification/parametrage-notification.routes'),
  },
  {
    path: 'participant',
    data: { pageTitle: 'bankyApp.participant.home.title' },
    loadChildren: () => import('./participant/participant.routes'),
  },
  {
    path: 'type-client',
    data: { pageTitle: 'bankyApp.typeClient.home.title' },
    loadChildren: () => import('./type-client/type-client.routes'),
  },
  {
    path: 'type-contrat',
    data: { pageTitle: 'bankyApp.typeContrat.home.title' },
    loadChildren: () => import('./type-contrat/type-contrat.routes'),
  },
  {
    path: 'type-transaction',
    data: { pageTitle: 'bankyApp.typeTransaction.home.title' },
    loadChildren: () => import('./type-transaction/type-transaction.routes'),
  },
  {
    path: 'variable-notification',
    data: { pageTitle: 'bankyApp.variableNotification.home.title' },
    loadChildren: () => import('./variable-notification/variable-notification.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;

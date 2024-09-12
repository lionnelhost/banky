import { IContratAbonnement, NewContratAbonnement } from './contrat-abonnement.model';

export const sampleWithRequiredData: IContratAbonnement = {
  id: 17741,
};

export const sampleWithPartialData: IContratAbonnement = {
  id: 151,
  idAbonne: 'apte sitôt refaire',
};

export const sampleWithFullData: IContratAbonnement = {
  id: 6332,
  idContrat: 'responsable juriste',
  idAbonne: 'repérer fonctionnaire au dépens de',
};

export const sampleWithNewData: NewContratAbonnement = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

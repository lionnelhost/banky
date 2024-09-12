import { IContratAbonnementCompte, NewContratAbonnementCompte } from './contrat-abonnement-compte.model';

export const sampleWithRequiredData: IContratAbonnementCompte = {
  id: 11739,
};

export const sampleWithPartialData: IContratAbonnementCompte = {
  id: 10293,
  idAbonne: 'dorénavant',
  idCompteBancaire: 'au-dessus de ensemble',
};

export const sampleWithFullData: IContratAbonnementCompte = {
  id: 14457,
  idContrat: 'sombre trop terriblement',
  idAbonne: 'aux environs de',
  idCompteBancaire: 'quant à',
};

export const sampleWithNewData: NewContratAbonnementCompte = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

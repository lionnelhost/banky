import { ICompteBancaire, NewCompteBancaire } from './compte-bancaire.model';

export const sampleWithRequiredData: ICompteBancaire = {
  idCompteBancaire: '75f027c2-1b47-46ee-b2ab-c8025365f799',
};

export const sampleWithPartialData: ICompteBancaire = {
  idCompteBancaire: 'c6797af1-d9a0-4117-832e-0b878056a8bb',
  soldeDisponible: 'jamais assassiner',
  rib: 'bzzz énumérer',
};

export const sampleWithFullData: ICompteBancaire = {
  idCompteBancaire: '765f22d4-9cc5-4bcc-817b-f6040b030995',
  age: 'trop',
  ncp: 'jusqu’à ce que sans que',
  sde: 'cesser',
  sin: 'magenta',
  soldeDisponible: 'au défaut de timide',
  rib: 'au-dessous entièrement simple',
  status: 'INACTIF',
};

export const sampleWithNewData: NewCompteBancaire = {
  idCompteBancaire: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

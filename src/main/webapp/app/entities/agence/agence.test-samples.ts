import { IAgence, NewAgence } from './agence.model';

export const sampleWithRequiredData: IAgence = {
  idAgence: '162f07e8-ba6d-4bd5-a946-923320b8b18b',
};

export const sampleWithPartialData: IAgence = {
  idAgence: 'bf8efa9b-eda1-47c0-b3c1-ceacc7a18af7',
  codeAgence: 'spécialiste préférer mince',
};

export const sampleWithFullData: IAgence = {
  idAgence: '8afefa88-91bd-42ce-a7b5-3fdaa784d1a3',
  codeAgence: 'dring',
  nomAgence: 'puisque simplifier',
};

export const sampleWithNewData: NewAgence = {
  idAgence: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import { ITypeClient, NewTypeClient } from './type-client.model';

export const sampleWithRequiredData: ITypeClient = {
  idTypeClient: '6d2ea74a-de2e-4236-803c-8f5e5aacacf1',
};

export const sampleWithPartialData: ITypeClient = {
  idTypeClient: '116e9b02-9e86-4000-b511-66363b8eba4e',
};

export const sampleWithFullData: ITypeClient = {
  idTypeClient: '0e9bb3bb-a633-4e8b-86e7-890b8396109d',
  libelle: 'rédaction dès',
};

export const sampleWithNewData: NewTypeClient = {
  idTypeClient: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

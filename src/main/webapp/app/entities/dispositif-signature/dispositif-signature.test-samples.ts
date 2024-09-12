import { IDispositifSignature, NewDispositifSignature } from './dispositif-signature.model';

export const sampleWithRequiredData: IDispositifSignature = {
  idDispositif: '032198b9-a4be-479a-9a49-666d49b5c93d',
};

export const sampleWithPartialData: IDispositifSignature = {
  idDispositif: '5e4b74f2-8f8c-4d04-9b69-a412e2f50922',
};

export const sampleWithFullData: IDispositifSignature = {
  idDispositif: '93592f93-3b4b-4935-84a9-03b92ad29f31',
  libelle: 'quasi',
};

export const sampleWithNewData: NewDispositifSignature = {
  idDispositif: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

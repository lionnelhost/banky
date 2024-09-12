import { IMessageErreur, NewMessageErreur } from './message-erreur.model';

export const sampleWithRequiredData: IMessageErreur = {
  idMessageErreur: '47984dfd-6329-4f9d-b103-1d41ade9f604',
};

export const sampleWithPartialData: IMessageErreur = {
  idMessageErreur: '1a4a5d10-4415-476c-afbd-11eaf424592b',
  codeErreur: 'hôte dessous',
  description: 'quasiment',
};

export const sampleWithFullData: IMessageErreur = {
  idMessageErreur: '270552de-8c23-403a-b546-0beaa4aca23b',
  codeErreur: "avare d'après",
  description: 'construire murmurer',
};

export const sampleWithNewData: NewMessageErreur = {
  idMessageErreur: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

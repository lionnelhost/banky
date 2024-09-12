import { IBanque, NewBanque } from './banque.model';

export const sampleWithRequiredData: IBanque = {
  idBanque: '45421cf8-38ec-4689-bc22-18ea9b9fcf8f',
};

export const sampleWithPartialData: IBanque = {
  idBanque: 'd02bc584-fef3-48d4-af02-05be99899324',
  code: 'jusqu’à ce que miam',
  devise: 'XAF',
  langue: 'FR',
  codeSwift: 'déchiffrer vis-à-vie de gâcher',
  cutOffTime: 'badaboum en dehors de en dedans de',
};

export const sampleWithFullData: IBanque = {
  idBanque: 'ef7cf1a5-8b21-4092-b87e-c29366dc04cd',
  code: 'administration ouch',
  devise: 'USD',
  langue: 'FR',
  logo: 'approximativement désagréable',
  codeSwift: 'snob',
  codeIban: 'soutenir',
  codePays: 'à peine bof serviable',
  fuseauHoraire: 'hi gai',
  cutOffTime: 'pourvu que commissionnaire chez',
  codeParticipant: 'ha ha construire',
  libelleParticipant: 'à partir de',
};

export const sampleWithNewData: NewBanque = {
  idBanque: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

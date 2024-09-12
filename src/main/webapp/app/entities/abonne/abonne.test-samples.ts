import { IAbonne, NewAbonne } from './abonne.model';

export const sampleWithRequiredData: IAbonne = {
  idAbonne: '86a9fa63-a501-4dac-bd0b-d47d9121d90d',
};

export const sampleWithPartialData: IAbonne = {
  idAbonne: 'ab862c01-18d8-4825-9d31-5fe830601772',
  typePieceIdentite: 'CNI',
  numPieceIdentite: 'en dépit de envers clac',
  statut: 'DESACTIVE',
};

export const sampleWithFullData: IAbonne = {
  idAbonne: '5c0e074f-9f1f-4367-ad0c-e75fc958769c',
  indiceClient: 'autour vouh trop',
  nomAbonne: 'équipe de recherche',
  prenomAbonne: 'triste figurer avant',
  telephone: '+33 216725940',
  email: 'Stephanie55@gmail.com',
  typePieceIdentite: 'CNI',
  numPieceIdentite: 'secours cependant à la faveur de',
  statut: 'BLOQUE',
  identifiant: 'pater ding',
};

export const sampleWithNewData: NewAbonne = {
  idAbonne: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

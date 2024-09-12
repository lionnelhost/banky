import { IJourFerier, NewJourFerier } from './jour-ferier.model';

export const sampleWithRequiredData: IJourFerier = {
  idJourFerie: 'b9a32c97-7607-4f27-81be-9eb3dd111cc0',
};

export const sampleWithPartialData: IJourFerier = {
  idJourFerie: 'b8094d13-79b9-49d7-be3b-0c821231c488',
  libelle: 'au-dessous de en vérité membre de l’équipe',
};

export const sampleWithFullData: IJourFerier = {
  idJourFerie: '2e9aebf8-7803-4286-aac3-6529fbb387fe',
  libelle: 'parfois',
};

export const sampleWithNewData: NewJourFerier = {
  idJourFerie: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

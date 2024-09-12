import { IParticipant, NewParticipant } from './participant.model';

export const sampleWithRequiredData: IParticipant = {
  idParticipant: '247c2f53-48d3-4417-b62e-82fade1467fc',
};

export const sampleWithPartialData: IParticipant = {
  idParticipant: 'b682c32e-8060-435e-b2c1-6cc871971954',
  codeParticipant: 'aussitôt que',
  codeBanque: 'responsable commis poser',
  nomBanque: 'insolite',
};

export const sampleWithFullData: IParticipant = {
  idParticipant: '253f151e-4430-470e-a8e3-ee2dac07d51a',
  codeParticipant: "ça d'après",
  codeBanque: 'meuh vouh errer',
  nomBanque: 'vaste craindre téméraire',
  libelle: "à l'insu de par suite de",
  pays: 'trop peu par rapport à',
  isActif: true,
};

export const sampleWithNewData: NewParticipant = {
  idParticipant: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

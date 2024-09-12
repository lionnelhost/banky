import { IClient, NewClient } from './client.model';

export const sampleWithRequiredData: IClient = {
  idClient: '66e0850a-a71f-4168-b46b-541a07b8a12a',
};

export const sampleWithPartialData: IClient = {
  idClient: 'd4ea13ab-c661-44d9-8b13-c480f9d06c0c',
  indiceClient: 'soustraire',
  nomClient: 'équipe puiser',
  prenomClient: 'agréable trop',
  telephone: '+33 492857346',
};

export const sampleWithFullData: IClient = {
  idClient: 'b3ef86f4-fff9-4221-b017-865e717c2317',
  indiceClient: 'grossir membre à vie police',
  nomClient: 'tellement',
  prenomClient: 'hé parmi',
  raisonSociale: 'malgré hirsute',
  telephone: '+33 437392842',
  email: 'Armine_Aubert@gmail.com',
};

export const sampleWithNewData: NewClient = {
  idClient: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import { IParametrageNotification, NewParametrageNotification } from './parametrage-notification.model';

export const sampleWithRequiredData: IParametrageNotification = {
  idParamNotif: 'ee91e0c7-99c8-4d4d-ac41-9e2e98afb402',
};

export const sampleWithPartialData: IParametrageNotification = {
  idParamNotif: '51a4967e-a6df-40d0-9b63-f33e421e5c32',
  objetNotif: 'vaste',
  typeNotif: 'dense pendant que',
  contenu: 'ouf devant oups',
};

export const sampleWithFullData: IParametrageNotification = {
  idParamNotif: 'af02ba1f-7113-4e6a-83fe-fbb9e08d694c',
  objetNotif: 'délégation à peu près selon',
  typeNotif: 'chut',
  contenu: 'coller',
};

export const sampleWithNewData: NewParametrageNotification = {
  idParamNotif: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

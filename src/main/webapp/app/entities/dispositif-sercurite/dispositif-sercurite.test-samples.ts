import { IDispositifSercurite, NewDispositifSercurite } from './dispositif-sercurite.model';

export const sampleWithRequiredData: IDispositifSercurite = {
  id: 5751,
};

export const sampleWithPartialData: IDispositifSercurite = {
  id: 30260,
};

export const sampleWithFullData: IDispositifSercurite = {
  id: 30855,
  idCanal: 'à seule fin de glouglou tant que',
  idTypeTransaction: 'moins',
  idDispositif: 'd’autant que parfois',
};

export const sampleWithNewData: NewDispositifSercurite = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

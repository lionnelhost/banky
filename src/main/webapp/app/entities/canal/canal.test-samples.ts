import { ICanal, NewCanal } from './canal.model';

export const sampleWithRequiredData: ICanal = {
  idCanal: '68f5e3c7-f20e-4000-8184-42edf22640e3',
};

export const sampleWithPartialData: ICanal = {
  idCanal: 'da9a9dc5-6474-4236-9bbd-633db73516a5',
  libelle: 'autour de enfin badaboum',
};

export const sampleWithFullData: ICanal = {
  idCanal: 'fb0ff61c-985a-425c-847e-2583a107a667',
  libelle: 'avant que',
};

export const sampleWithNewData: NewCanal = {
  idCanal: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

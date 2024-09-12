import dayjs from 'dayjs/esm';

import { IContrat, NewContrat } from './contrat.model';

export const sampleWithRequiredData: IContrat = {
  idContrat: 'e6dcad9c-fdb9-41da-b502-d83834ffa016',
};

export const sampleWithPartialData: IContrat = {
  idContrat: 'e96d531e-3452-43c2-8794-b8dd2733aaf6',
};

export const sampleWithFullData: IContrat = {
  idContrat: 'e57654b3-bd0c-47ca-ac4b-3607ab93fa5f',
  dateValidite: dayjs('2024-09-11T11:09'),
};

export const sampleWithNewData: NewContrat = {
  idContrat: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

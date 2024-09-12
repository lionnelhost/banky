import { IParametrageGlobal, NewParametrageGlobal } from './parametrage-global.model';

export const sampleWithRequiredData: IParametrageGlobal = {
  idParamGlobal: '87c14354-7fb3-4795-9da5-a2c3996b4fcd',
};

export const sampleWithPartialData: IParametrageGlobal = {
  idParamGlobal: 'dadbc8eb-e352-4c52-8110-75c2cecce7d1',
  typeParam: 'avare encore vanter',
};

export const sampleWithFullData: IParametrageGlobal = {
  idParamGlobal: '98b7f6e4-6bc8-42fa-b9a6-527176063e94',
  codeParam: 'moyennant',
  typeParam: 'commissionnaire pacifique',
  valeur: 'membre titulaire pourvu que soulever',
};

export const sampleWithNewData: NewParametrageGlobal = {
  idParamGlobal: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import { ITypeTransaction, NewTypeTransaction } from './type-transaction.model';

export const sampleWithRequiredData: ITypeTransaction = {
  idTypeTransaction: '4341a1e2-5be8-44fe-a4fc-4735c00c3e04',
};

export const sampleWithPartialData: ITypeTransaction = {
  idTypeTransaction: '09a4efb3-cbd5-4376-88e8-716eff0538c3',
  libelle: 'géométrique déclencher',
};

export const sampleWithFullData: ITypeTransaction = {
  idTypeTransaction: '8a64bc70-ccb2-445e-93ef-36ec5de88de5',
  libelle: 'vaste',
};

export const sampleWithNewData: NewTypeTransaction = {
  idTypeTransaction: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

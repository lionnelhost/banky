import { ITypeContrat, NewTypeContrat } from './type-contrat.model';

export const sampleWithRequiredData: ITypeContrat = {
  idTypeContrat: '2faa64c3-b039-4d30-82f4-46d209d02f4b',
};

export const sampleWithPartialData: ITypeContrat = {
  idTypeContrat: '19acef87-51a7-40d2-b489-f062009375ca',
  libelle: 'conseil municipal hôte',
};

export const sampleWithFullData: ITypeContrat = {
  idTypeContrat: 'bf1f14e6-ba41-4fdf-94d9-985fef97c345',
  libelle: "à l'entour de presque",
};

export const sampleWithNewData: NewTypeContrat = {
  idTypeContrat: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

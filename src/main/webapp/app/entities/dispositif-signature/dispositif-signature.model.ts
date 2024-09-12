export interface IDispositifSignature {
  idDispositif: string;
  libelle?: string | null;
}

export type NewDispositifSignature = Omit<IDispositifSignature, 'idDispositif'> & { idDispositif: null };

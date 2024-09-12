export interface ICanal {
  idCanal: string;
  libelle?: string | null;
}

export type NewCanal = Omit<ICanal, 'idCanal'> & { idCanal: null };

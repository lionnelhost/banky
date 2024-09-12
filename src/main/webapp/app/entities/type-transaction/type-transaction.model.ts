export interface ITypeTransaction {
  idTypeTransaction: string;
  libelle?: string | null;
}

export type NewTypeTransaction = Omit<ITypeTransaction, 'idTypeTransaction'> & { idTypeTransaction: null };

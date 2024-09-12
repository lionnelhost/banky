export interface ITypeClient {
  idTypeClient: string;
  libelle?: string | null;
}

export type NewTypeClient = Omit<ITypeClient, 'idTypeClient'> & { idTypeClient: null };

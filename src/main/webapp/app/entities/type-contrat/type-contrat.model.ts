export interface ITypeContrat {
  idTypeContrat: string;
  libelle?: string | null;
}

export type NewTypeContrat = Omit<ITypeContrat, 'idTypeContrat'> & { idTypeContrat: null };

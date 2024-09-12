export interface IParametrageGlobal {
  idParamGlobal: string;
  codeParam?: string | null;
  typeParam?: string | null;
  valeur?: string | null;
}

export type NewParametrageGlobal = Omit<IParametrageGlobal, 'idParamGlobal'> & { idParamGlobal: null };

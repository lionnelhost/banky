export interface IVariableNotification {
  idVarNotification: string;
  codeVariable?: string | null;
  description?: string | null;
}

export type NewVariableNotification = Omit<IVariableNotification, 'idVarNotification'> & { idVarNotification: null };

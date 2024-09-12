export interface IMessageErreur {
  idMessageErreur: string;
  codeErreur?: string | null;
  description?: string | null;
}

export type NewMessageErreur = Omit<IMessageErreur, 'idMessageErreur'> & { idMessageErreur: null };

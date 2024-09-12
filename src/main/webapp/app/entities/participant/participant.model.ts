export interface IParticipant {
  idParticipant: string;
  codeParticipant?: string | null;
  codeBanque?: string | null;
  nomBanque?: string | null;
  libelle?: string | null;
  pays?: string | null;
  isActif?: boolean | null;
}

export type NewParticipant = Omit<IParticipant, 'idParticipant'> & { idParticipant: null };

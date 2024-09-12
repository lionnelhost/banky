export interface IJourFerier {
  idJourFerie: string;
  libelle?: string | null;
}

export type NewJourFerier = Omit<IJourFerier, 'idJourFerie'> & { idJourFerie: null };

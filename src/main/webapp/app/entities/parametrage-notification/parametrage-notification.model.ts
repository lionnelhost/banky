export interface IParametrageNotification {
  idParamNotif: string;
  objetNotif?: string | null;
  typeNotif?: string | null;
  contenu?: string | null;
}

export type NewParametrageNotification = Omit<IParametrageNotification, 'idParamNotif'> & { idParamNotif: null };

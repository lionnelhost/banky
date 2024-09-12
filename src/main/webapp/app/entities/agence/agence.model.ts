import { IBanque } from 'app/entities/banque/banque.model';

export interface IAgence {
  idAgence: string;
  codeAgence?: string | null;
  nomAgence?: string | null;
  banque?: Pick<IBanque, 'idBanque'> | null;
}

export type NewAgence = Omit<IAgence, 'idAgence'> & { idAgence: null };

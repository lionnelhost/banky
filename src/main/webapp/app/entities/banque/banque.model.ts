import { Devise } from 'app/entities/enumerations/devise.model';
import { Langue } from 'app/entities/enumerations/langue.model';

export interface IBanque {
  idBanque: string;
  code?: string | null;
  devise?: keyof typeof Devise | null;
  langue?: keyof typeof Langue | null;
  logo?: string | null;
  codeSwift?: string | null;
  codeIban?: string | null;
  codePays?: string | null;
  fuseauHoraire?: string | null;
  cutOffTime?: string | null;
  codeParticipant?: string | null;
  libelleParticipant?: string | null;
}

export type NewBanque = Omit<IBanque, 'idBanque'> & { idBanque: null };

import { IContrat } from 'app/entities/contrat/contrat.model';
import { StatutCompteBancaire } from 'app/entities/enumerations/statut-compte-bancaire.model';

export interface ICompteBancaire {
  idCompteBancaire: string;
  age?: string | null;
  ncp?: string | null;
  sde?: string | null;
  sin?: string | null;
  soldeDisponible?: string | null;
  rib?: string | null;
  status?: keyof typeof StatutCompteBancaire | null;
  contrat?: Pick<IContrat, 'idContrat'> | null;
}

export type NewCompteBancaire = Omit<ICompteBancaire, 'idCompteBancaire'> & { idCompteBancaire: null };

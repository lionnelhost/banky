import { IContrat } from 'app/entities/contrat/contrat.model';
import { IAbonne } from 'app/entities/abonne/abonne.model';
import { ICompteBancaire } from 'app/entities/compte-bancaire/compte-bancaire.model';

export interface IContratAbonnementCompte {
  id: number;
  idContrat?: string | null;
  idAbonne?: string | null;
  idCompteBancaire?: string | null;
  contrat?: Pick<IContrat, 'idContrat'> | null;
  abonne?: Pick<IAbonne, 'idAbonne'> | null;
  compteBancaire?: Pick<ICompteBancaire, 'idCompteBancaire'> | null;
}

export type NewContratAbonnementCompte = Omit<IContratAbonnementCompte, 'id'> & { id: null };

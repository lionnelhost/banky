import { IContrat } from 'app/entities/contrat/contrat.model';
import { IAbonne } from 'app/entities/abonne/abonne.model';

export interface IContratAbonnement {
  id: number;
  idContrat?: string | null;
  idAbonne?: string | null;
  contrat?: Pick<IContrat, 'idContrat'> | null;
  abonne?: Pick<IAbonne, 'idAbonne'> | null;
}

export type NewContratAbonnement = Omit<IContratAbonnement, 'id'> & { id: null };

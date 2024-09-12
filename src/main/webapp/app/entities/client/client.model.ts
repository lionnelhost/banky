import { IContrat } from 'app/entities/contrat/contrat.model';
import { ITypeClient } from 'app/entities/type-client/type-client.model';

export interface IClient {
  idClient: string;
  indiceClient?: string | null;
  nomClient?: string | null;
  prenomClient?: string | null;
  raisonSociale?: string | null;
  telephone?: string | null;
  email?: string | null;
  contrat?: Pick<IContrat, 'idContrat'> | null;
  typeClient?: Pick<ITypeClient, 'idTypeClient'> | null;
}

export type NewClient = Omit<IClient, 'idClient'> & { idClient: null };

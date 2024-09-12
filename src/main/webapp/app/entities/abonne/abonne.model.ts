import { TypePieceIdentite } from 'app/entities/enumerations/type-piece-identite.model';
import { StatutAbonne } from 'app/entities/enumerations/statut-abonne.model';

export interface IAbonne {
  idAbonne: string;
  indiceClient?: string | null;
  nomAbonne?: string | null;
  prenomAbonne?: string | null;
  telephone?: string | null;
  email?: string | null;
  typePieceIdentite?: keyof typeof TypePieceIdentite | null;
  numPieceIdentite?: string | null;
  statut?: keyof typeof StatutAbonne | null;
  identifiant?: string | null;
}

export type NewAbonne = Omit<IAbonne, 'idAbonne'> & { idAbonne: null };

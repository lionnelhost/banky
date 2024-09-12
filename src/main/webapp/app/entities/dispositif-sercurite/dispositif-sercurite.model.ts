import { ICanal } from 'app/entities/canal/canal.model';
import { ITypeTransaction } from 'app/entities/type-transaction/type-transaction.model';
import { IDispositifSignature } from 'app/entities/dispositif-signature/dispositif-signature.model';

export interface IDispositifSercurite {
  id: number;
  idCanal?: string | null;
  idTypeTransaction?: string | null;
  idDispositif?: string | null;
  canal?: Pick<ICanal, 'idCanal'> | null;
  typeTransaction?: Pick<ITypeTransaction, 'idTypeTransaction'> | null;
  dispositifSignature?: Pick<IDispositifSignature, 'idDispositif'> | null;
}

export type NewDispositifSercurite = Omit<IDispositifSercurite, 'id'> & { id: null };

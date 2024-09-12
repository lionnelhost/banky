import dayjs from 'dayjs/esm';
import { ITypeContrat } from 'app/entities/type-contrat/type-contrat.model';

export interface IContrat {
  idContrat: string;
  dateValidite?: dayjs.Dayjs | null;
  typeContrat?: Pick<ITypeContrat, 'idTypeContrat'> | null;
}

export type NewContrat = Omit<IContrat, 'idContrat'> & { idContrat: null };

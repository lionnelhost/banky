import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'fea9e975-9349-41db-a995-cc1acacd3ff0',
};

export const sampleWithPartialData: IAuthority = {
  name: '388718a4-ef9f-426a-8776-30ae77a1eb65',
};

export const sampleWithFullData: IAuthority = {
  name: '0ed1e597-15c0-4475-aa05-e5777f2f05a8',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

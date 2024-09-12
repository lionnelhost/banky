import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 'e0a539d2-0080-4756-bada-08b747465361',
  login: '=xqj?U@7yd9\\Z8\\:g\\\\rq\\^3Tp',
};

export const sampleWithPartialData: IUser = {
  id: 'baede336-fd6b-4379-8e94-0a809788db2e',
  login: '?7Xf=}@RKsw12\\R-oa\\9lYJ\\`cQ\\xrsob8w',
};

export const sampleWithFullData: IUser = {
  id: 'aa8a57de-8c4b-47ca-8442-ec4ee16b9611',
  login: '4',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

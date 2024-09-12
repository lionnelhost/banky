import { IVariableNotification, NewVariableNotification } from './variable-notification.model';

export const sampleWithRequiredData: IVariableNotification = {
  idVarNotification: 'b6f06393-f8f1-4fa6-8cc1-05efa214e1b2',
};

export const sampleWithPartialData: IVariableNotification = {
  idVarNotification: 'ced4e58b-2c1a-4fb6-b395-0b5dd6ead2a0',
};

export const sampleWithFullData: IVariableNotification = {
  idVarNotification: 'bfb7f1af-33ea-41da-9b81-6f132f5e3580',
  codeVariable: 'sans que assez hôte',
  description: 'percevoir bientôt',
};

export const sampleWithNewData: NewVariableNotification = {
  idVarNotification: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

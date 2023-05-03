import { IFunding, NewFunding } from './funding.model';

export const sampleWithRequiredData: IFunding = {
  id: 9531,
};

export const sampleWithPartialData: IFunding = {
  id: 48133,
  name: 'Multi-tiered vortals',
  parentId: 97016,
};

export const sampleWithFullData: IFunding = {
  id: 23536,
  name: 'invoice',
  type: 'Steel Credit',
  parentId: 35039,
  description: 'Granite Table redefine',
};

export const sampleWithNewData: NewFunding = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

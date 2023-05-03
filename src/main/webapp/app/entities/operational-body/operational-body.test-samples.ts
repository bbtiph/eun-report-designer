import { IOperationalBody, NewOperationalBody } from './operational-body.model';

export const sampleWithRequiredData: IOperationalBody = {
  id: 91059,
  name: 'Marketing',
};

export const sampleWithPartialData: IOperationalBody = {
  id: 35330,
  name: 'Hat Virginia',
  acronym: 'Fresh',
  status: 'Account',
};

export const sampleWithFullData: IOperationalBody = {
  id: 95132,
  name: 'Uzbekistan wireless circuit',
  acronym: 'Forges Chief',
  description: 'Identity Rubber RSS',
  type: 'plug-and-play',
  status: 'Chair indigo',
};

export const sampleWithNewData: NewOperationalBody = {
  name: 'Togo Accountability',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import { IPersonInOrganization, NewPersonInOrganization } from './person-in-organization.model';

export const sampleWithRequiredData: IPersonInOrganization = {
  id: 56774,
};

export const sampleWithPartialData: IPersonInOrganization = {
  id: 59882,
  roleInOrganization: 'Data national',
};

export const sampleWithFullData: IPersonInOrganization = {
  id: 48271,
  roleInOrganization: 'feed',
};

export const sampleWithNewData: NewPersonInOrganization = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import { IOrganizationInMinistry, NewOrganizationInMinistry } from './organization-in-ministry.model';

export const sampleWithRequiredData: IOrganizationInMinistry = {
  id: 69828,
};

export const sampleWithPartialData: IOrganizationInMinistry = {
  id: 87002,
};

export const sampleWithFullData: IOrganizationInMinistry = {
  id: 69428,
  status: 'enterprise Supervisor',
};

export const sampleWithNewData: NewOrganizationInMinistry = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import dayjs from 'dayjs/esm';

import { IWorkingGroupReferences, NewWorkingGroupReferences } from './working-group-references.model';

export const sampleWithRequiredData: IWorkingGroupReferences = {
  id: 92556,
};

export const sampleWithPartialData: IWorkingGroupReferences = {
  id: 19830,
  countryCode: 'SS',
  countryRepresentativeMail: 'PCI',
  countryRepresentativePosition: 'Industrial Missouri primary',
  countryRepresentativeEndDate: dayjs('2023-07-04'),
  type: 'methodologies',
  createdBy: 'ubiquitous',
  lastModifiedDate: dayjs('2023-07-04'),
};

export const sampleWithFullData: IWorkingGroupReferences = {
  id: 73738,
  countryCode: 'PF',
  countryName: 'repurpose throughput Home',
  countryRepresentativeFirstName: 'Plain Fall Configuration',
  countryRepresentativeLastName: 'engine blue',
  countryRepresentativeMail: 'Credit circuit',
  countryRepresentativePosition: 'COM concept Sleek',
  countryRepresentativeStartDate: dayjs('2023-07-04'),
  countryRepresentativeEndDate: dayjs('2023-07-03'),
  countryRepresentativeMinistry: 'reboot deposit Interactions',
  countryRepresentativeDepartment: 'Colorado',
  contactEunFirstName: 'National Directives aggregate',
  contactEunLastName: 'Salad Fresh auxiliary',
  type: 'Berkshire',
  isActive: true,
  createdBy: 'Strategist grey',
  lastModifiedBy: 'system invoice payment',
  createdDate: dayjs('2023-07-03'),
  lastModifiedDate: dayjs('2023-07-03'),
};

export const sampleWithNewData: NewWorkingGroupReferences = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

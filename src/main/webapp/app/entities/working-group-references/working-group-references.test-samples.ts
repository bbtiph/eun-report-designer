import dayjs from 'dayjs/esm';

import { IWorkingGroupReferences, NewWorkingGroupReferences } from './working-group-references.model';

export const sampleWithRequiredData: IWorkingGroupReferences = {
  id: 92556,
};

export const sampleWithPartialData: IWorkingGroupReferences = {
  id: 83427,
  countryCode: 'AX',
  countryRepresentativeMail: 'benchmark Industrial Missouri',
  countryRepresentativePosition: 'Refined Practical ubiquitous',
  countryRepresentativeEndDate: dayjs('2023-07-04'),
  type: 'Rand Brazilian Response',
  createdBy: 'Frozen',
  lastModifiedDate: dayjs('2023-07-03'),
};

export const sampleWithFullData: IWorkingGroupReferences = {
  id: 78504,
  countryCode: 'JO',
  countryName: 'User-friendly',
  countryRepresentativeFirstName: 'Kansas Dynamic array',
  countryRepresentativeLastName: 'Gloves Account',
  countryRepresentativeMail: 'Money bandwidth',
  countryRepresentativePosition: 'Soap',
  countryRepresentativeStartDate: dayjs('2023-07-03'),
  countryRepresentativeEndDate: dayjs('2023-07-03'),
  countryRepresentativeMinistry: 'deposit Interactions',
  countryRepresentativeDepartment: 'Colorado',
  contactEunFirstName: 'National Directives aggregate',
  contactEunLastName: 'Salad Fresh auxiliary',
  type: 'Berkshire',
  isActive: true,
  createdBy: 'Strategist grey',
  lastModifiedBy: 'system invoice payment',
  createdDate: dayjs('2023-07-03'),
  lastModifiedDate: dayjs('2023-07-03'),
  sheetNum: 48418,
};

export const sampleWithNewData: NewWorkingGroupReferences = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

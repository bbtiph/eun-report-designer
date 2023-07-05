import dayjs from 'dayjs/esm';

import { IWorkingGroupReferences, NewWorkingGroupReferences } from './working-group-references.model';

export const sampleWithRequiredData: IWorkingGroupReferences = {
  id: 92556,
};

export const sampleWithPartialData: IWorkingGroupReferences = {
  id: 96903,
  countryCode: 'ES',
  countryRepresentativeMail: 'Account Tuna',
  countryRepresentativePosition: 'Applications',
  countryRepresentativeEndDate: dayjs('2023-07-04'),
  type: 'primary',
};

export const sampleWithFullData: IWorkingGroupReferences = {
  id: 24009,
  countryCode: 'AX',
  countryName: 'Tactics ubiquitous',
  countryRepresentativeFirstName: 'feed repurpose',
  countryRepresentativeLastName: 'Response',
  countryRepresentativeMail: 'Frozen',
  countryRepresentativePosition: 'online User-friendly',
  countryRepresentativeStartDate: dayjs('2023-07-03'),
  countryRepresentativeEndDate: dayjs('2023-07-03'),
  countryRepresentativeMinistry: 'Interactions',
  countryRepresentativeDepartment: 'array deposit',
  contactEunFirstName: 'Account',
  contactEunLastName: 'Money bandwidth',
  type: 'Soap',
  isActive: true,
};

export const sampleWithNewData: NewWorkingGroupReferences = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

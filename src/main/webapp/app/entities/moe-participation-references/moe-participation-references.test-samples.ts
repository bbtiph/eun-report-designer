import dayjs from 'dayjs/esm';

import { IMOEParticipationReferences, NewMOEParticipationReferences } from './moe-participation-references.model';

export const sampleWithRequiredData: IMOEParticipationReferences = {
  id: 78073,
};

export const sampleWithPartialData: IMOEParticipationReferences = {
  id: 3994,
  name: 'value-added productize Strategist',
  type: 'to',
  startDate: dayjs('2024-03-18'),
  endDate: dayjs('2024-03-17'),
  isActive: false,
  createdBy: 'Kwanza synergize mobile',
  createdDate: dayjs('2024-03-17'),
  lastModifiedDate: dayjs('2024-03-17'),
};

export const sampleWithFullData: IMOEParticipationReferences = {
  id: 53272,
  name: 'parse',
  type: 'USB York',
  startDate: dayjs('2024-03-17'),
  endDate: dayjs('2024-03-18'),
  isActive: true,
  createdBy: 'Fish Avon',
  lastModifiedBy: 'bus Public-key dynamic',
  createdDate: dayjs('2024-03-17'),
  lastModifiedDate: dayjs('2024-03-18'),
};

export const sampleWithNewData: NewMOEParticipationReferences = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

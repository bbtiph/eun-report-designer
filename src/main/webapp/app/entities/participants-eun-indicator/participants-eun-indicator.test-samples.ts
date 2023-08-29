import dayjs from 'dayjs/esm';

import { IParticipantsEunIndicator, NewParticipantsEunIndicator } from './participants-eun-indicator.model';

export const sampleWithRequiredData: IParticipantsEunIndicator = {
  id: 52175,
};

export const sampleWithPartialData: IParticipantsEunIndicator = {
  id: 69058,
  nCount: 78927,
  createdBy: 'Seychelles',
  lastModifiedBy: 'USB',
};

export const sampleWithFullData: IParticipantsEunIndicator = {
  id: 3378,
  period: 66063,
  nCount: 77467,
  courseId: 'Toys Bacon',
  courseName: 'Frozen Baby Dalasi',
  countryCode: 'KW',
  createdBy: 'application',
  lastModifiedBy: 'Internal white Gloves',
  createdDate: dayjs('2023-08-28'),
  lastModifiedDate: dayjs('2023-08-29'),
};

export const sampleWithNewData: NewParticipantsEunIndicator = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

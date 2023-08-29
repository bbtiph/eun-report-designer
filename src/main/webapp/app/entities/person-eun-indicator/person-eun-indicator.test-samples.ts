import dayjs from 'dayjs/esm';

import { IPersonEunIndicator, NewPersonEunIndicator } from './person-eun-indicator.model';

export const sampleWithRequiredData: IPersonEunIndicator = {
  id: 42105,
};

export const sampleWithPartialData: IPersonEunIndicator = {
  id: 62290,
  period: 8211,
  nCount: 84687,
  projectId: 20678,
  projectUrl: 'transform matrix bluetooth',
  projectName: 'channels',
  createdDate: dayjs('2023-08-22'),
};

export const sampleWithFullData: IPersonEunIndicator = {
  id: 85306,
  period: 38546,
  nCount: 23267,
  countryId: 71977,
  projectId: 6730,
  projectUrl: 'Gloves',
  countryName: 'matrix',
  projectName: 'Switchable',
  reportsProjectId: 45503,
  createdBy: 'Directives Fish Ball',
  lastModifiedBy: 'generate integrate',
  createdDate: dayjs('2023-08-21'),
  lastModifiedDate: dayjs('2023-08-22'),
};

export const sampleWithNewData: NewPersonEunIndicator = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

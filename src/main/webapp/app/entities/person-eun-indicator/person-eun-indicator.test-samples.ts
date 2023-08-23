import dayjs from 'dayjs/esm';

import { IPersonEunIndicator, NewPersonEunIndicator } from './person-eun-indicator.model';

export const sampleWithRequiredData: IPersonEunIndicator = {
  id: 42105,
};

export const sampleWithPartialData: IPersonEunIndicator = {
  id: 27713,
  nCount: 62290,
  countryId: 8211,
  projectUrl: 'olive Licensed Factors',
  countryName: 'Brunei Salad',
  reportsProjectId: 85306,
  lastModifiedDate: dayjs('2023-08-22'),
};

export const sampleWithFullData: IPersonEunIndicator = {
  id: 23267,
  nCount: 71977,
  countryId: 6730,
  projectId: 9986,
  projectUrl: 'interactive',
  countryName: 'Brand Switchable Unions',
  projectName: 'deposit',
  reportsProjectId: 64601,
  createdBy: 'Chief',
  lastModifiedBy: 'Card fault-tolerant azure',
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

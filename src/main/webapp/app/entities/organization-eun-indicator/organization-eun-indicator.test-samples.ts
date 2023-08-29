import dayjs from 'dayjs/esm';

import { IOrganizationEunIndicator, NewOrganizationEunIndicator } from './organization-eun-indicator.model';

export const sampleWithRequiredData: IOrganizationEunIndicator = {
  id: 71201,
};

export const sampleWithPartialData: IOrganizationEunIndicator = {
  id: 37949,
  period: 32319,
  nCount: 24023,
  countryId: 89145,
  projectUrl: 'Investor viral stable',
  projectName: 'Beauty Shirt',
  reportsProjectId: 50255,
  lastModifiedBy: 'Avon Comoro Gorgeous',
  createdDate: dayjs('2023-08-21'),
  lastModifiedDate: dayjs('2023-08-21'),
};

export const sampleWithFullData: IOrganizationEunIndicator = {
  id: 59924,
  period: 15753,
  nCount: 76281,
  countryId: 45548,
  projectId: 27664,
  projectUrl: 'customer',
  countryName: 'navigate',
  projectName: 'TCP global',
  reportsProjectId: 33620,
  createdBy: '(EURCO) RSS Cotton',
  lastModifiedBy: 'Zambian scale monitor',
  createdDate: dayjs('2023-08-21'),
  lastModifiedDate: dayjs('2023-08-21'),
};

export const sampleWithNewData: NewOrganizationEunIndicator = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

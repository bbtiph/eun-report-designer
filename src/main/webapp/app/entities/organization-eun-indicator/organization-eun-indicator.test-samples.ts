import dayjs from 'dayjs/esm';

import { IOrganizationEunIndicator, NewOrganizationEunIndicator } from './organization-eun-indicator.model';

export const sampleWithRequiredData: IOrganizationEunIndicator = {
  id: 71201,
};

export const sampleWithPartialData: IOrganizationEunIndicator = {
  id: 87433,
  nCount: 37949,
  countryId: 32319,
  projectId: 24023,
  countryName: 'Composite Director Ball',
  reportsProjectId: 8983,
  createdBy: 'Beauty Shirt',
  createdDate: dayjs('2023-08-21'),
  lastModifiedDate: dayjs('2023-08-21'),
};

export const sampleWithFullData: IOrganizationEunIndicator = {
  id: 49312,
  nCount: 9046,
  countryId: 5644,
  projectId: 69338,
  projectUrl: 'transform Outdoors',
  countryName: 'Cheese asynchronous',
  projectName: 'communities',
  reportsProjectId: 37692,
  createdBy: 'Auto harness Berkshire',
  lastModifiedBy: 'Human Ville models',
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

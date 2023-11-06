import dayjs from 'dayjs/esm';

import { IGeneratedReport, NewGeneratedReport } from './generated-report.model';

export const sampleWithRequiredData: IGeneratedReport = {
  id: 54836,
};

export const sampleWithPartialData: IGeneratedReport = {
  id: 36696,
  description: 'best-of-breed',
  isActive: false,
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
  lastModifiedBy: 'primary zero',
  createdDate: dayjs('2023-11-03'),
  lastModifiedDate: dayjs('2023-11-03'),
};

export const sampleWithFullData: IGeneratedReport = {
  id: 31045,
  name: 'Automated',
  description: 'Missouri paradigms',
  requestBody: 'brand Harbors even-keeled',
  url: 'https://austen.biz',
  isActive: false,
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
  createdBy: 'wireless',
  lastModifiedBy: 'transmitter',
  createdDate: dayjs('2023-11-02'),
  lastModifiedDate: dayjs('2023-11-02'),
};

export const sampleWithNewData: NewGeneratedReport = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

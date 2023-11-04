import dayjs from 'dayjs/esm';

import { IGeneratedReport, NewGeneratedReport } from './generated-report.model';

export const sampleWithRequiredData: IGeneratedReport = {
  id: 54836,
};

export const sampleWithPartialData: IGeneratedReport = {
  id: 66897,
  description: 'grey Forest',
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
  createdBy: 'Ergonomic Architect Incredible',
  createdDate: dayjs('2023-11-02'),
  lastModifiedDate: dayjs('2023-11-02'),
};

export const sampleWithFullData: IGeneratedReport = {
  id: 58279,
  name: 'withdrawal matrix',
  description: 'hub Lari',
  requestBody: 'bypass Small',
  isActive: true,
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
  createdBy: 'policy',
  lastModifiedBy: 'application',
  createdDate: dayjs('2023-11-02'),
  lastModifiedDate: dayjs('2023-11-03'),
};

export const sampleWithNewData: NewGeneratedReport = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

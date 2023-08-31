import dayjs from 'dayjs/esm';

import { IReportTemplate, NewReportTemplate } from './report-template.model';

export const sampleWithRequiredData: IReportTemplate = {
  id: 9779,
};

export const sampleWithPartialData: IReportTemplate = {
  id: 6245,
  name: 'Isle',
  description: 'bandwidth Granite',
  isActive: true,
  createdBy: 'indexing',
  lastModifiedBy: 'revolutionary Officer',
  createdDate: dayjs('2023-08-31'),
  lastModifiedDate: dayjs('2023-08-30'),
};

export const sampleWithFullData: IReportTemplate = {
  id: 6259,
  name: 'withdrawal',
  description: 'Bacon',
  type: 'Generic',
  isActive: false,
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
  createdBy: 'Ergonomic generating',
  lastModifiedBy: 'Moroccan',
  createdDate: dayjs('2023-08-30'),
  lastModifiedDate: dayjs('2023-08-30'),
};

export const sampleWithNewData: NewReportTemplate = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

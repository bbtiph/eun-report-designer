import { IReport, NewReport } from './report.model';

export const sampleWithRequiredData: IReport = {
  id: 35851,
  reportName: 'protocol Liberia expedite',
};

export const sampleWithPartialData: IReport = {
  id: 78426,
  reportName: 'compressing Refined',
  acronym: 'e-business Account extensible',
  description: 'help-desk orange SMS',
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
};

export const sampleWithFullData: IReport = {
  id: 32053,
  reportName: 'Mouse',
  acronym: 'Accounts invoice',
  description: 'New program viral',
  type: 'Canada Licensed services',
  isActive: 'Enhanced',
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
};

export const sampleWithNewData: NewReport = {
  reportName: 'Representative Account',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

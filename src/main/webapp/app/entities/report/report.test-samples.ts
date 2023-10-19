import { IReport, NewReport } from './report.model';

export const sampleWithRequiredData: IReport = {
  id: 35851,
  reportName: 'protocol Liberia expedite',
};

export const sampleWithPartialData: IReport = {
  id: 82970,
  reportName: 'Account',
  acronym: 'array',
  description: 'Account extensible',
  isMinistry: true,
  parentId: 1,
};

export const sampleWithFullData: IReport = {
  id: 44138,
  reportName: 'orange SMS adapter',
  acronym: 'bleeding-edge',
  description: 'Branding defect Account',
  type: 'Pants Rand Pants',
  isActive: true,
  isMinistry: true,
  parentId: 1,
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
};

export const sampleWithNewData: NewReport = {
  reportName: 'copy Ergonomic Representative',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

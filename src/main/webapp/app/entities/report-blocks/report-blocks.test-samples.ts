import { IReportBlocks, NewReportBlocks } from './report-blocks.model';

export const sampleWithRequiredData: IReportBlocks = {
  id: 46278,
};

export const sampleWithPartialData: IReportBlocks = {
  id: 47503,
  isActive: true,
  config: 'envisioneer Personal XSS',
};

export const sampleWithFullData: IReportBlocks = {
  id: 7846,
  name: 'SAS',
  priorityNumber: 42720,
  isActive: false,
  config: 'Account e-business',
};

export const sampleWithNewData: NewReportBlocks = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

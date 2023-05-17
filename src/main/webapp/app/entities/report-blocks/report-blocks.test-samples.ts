import { IReportBlocks, NewReportBlocks } from './report-blocks.model';

export const sampleWithRequiredData: IReportBlocks = {
  id: 46278,
};

export const sampleWithPartialData: IReportBlocks = {
  id: 75101,
  content: 'circuit Functionality',
  isActive: true,
  sqlScript: 'Ergonomic connecting',
};

export const sampleWithFullData: IReportBlocks = {
  id: 41404,
  countryName: 'Account e-business',
  priorityNumber: 96832,
  content: 'deposit hybrid COM',
  isActive: true,
  type: 'efficient Account',
  sqlScript: 'Guyana',
};

export const sampleWithNewData: NewReportBlocks = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

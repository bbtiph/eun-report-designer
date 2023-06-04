import { IReportBlocksContentData, NewReportBlocksContentData } from './report-blocks-content-data.model';

export const sampleWithRequiredData: IReportBlocksContentData = {
  id: 24304,
};

export const sampleWithPartialData: IReportBlocksContentData = {
  id: 72594,
  data: 'demand-driven',
};

export const sampleWithFullData: IReportBlocksContentData = {
  id: 43480,
  data: 'RSS Regional',
};

export const sampleWithNewData: NewReportBlocksContentData = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

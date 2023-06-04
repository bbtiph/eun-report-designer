import { IReportBlocksContent, NewReportBlocksContent } from './report-blocks-content.model';

export const sampleWithRequiredData: IReportBlocksContent = {
  id: 37095,
};

export const sampleWithPartialData: IReportBlocksContent = {
  id: 43189,
  type: 'Ouguiya',
  priorityNumber: 83676,
  template: 'Agent parsing GB',
  isActive: true,
};

export const sampleWithFullData: IReportBlocksContent = {
  id: 66584,
  type: 'Reverse-engineered port',
  priorityNumber: 85931,
  template: 'violet withdrawal',
  isActive: false,
};

export const sampleWithNewData: NewReportBlocksContent = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

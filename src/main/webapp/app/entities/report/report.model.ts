import { IReportBlocks } from '../report-blocks/report-blocks.model';

export interface IReport {
  id: number;
  reportName?: string | null;
  acronym?: string | null;
  description?: string | null;
  type?: string | null;
  isActive?: string | null;
  file?: string | null;
  fileContentType?: string | null;
  reportBlocks: IReportBlocks[] | null;
}

export type NewReport = Omit<IReport, 'id'> & { id: null };

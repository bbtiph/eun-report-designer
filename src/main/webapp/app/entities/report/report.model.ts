import { IReportBlocks } from 'app/entities/report-blocks/report-blocks.model';
import { IReportTemplate } from 'app/entities/report-template/report-template.model';

export interface IReport {
  id: number;
  reportName?: string | null;
  acronym?: string | null;
  description?: string | null;
  type?: string | null;
  isActive?: boolean | null;
  isMinistry?: boolean | null;
  parentId?: number | null;
  file?: string | null;
  fileContentType?: string | null;
  reportBlockIds?: Pick<IReportBlocks, 'id'>[] | null;
  reportTemplate?: Pick<IReportTemplate, 'id'> | null;
}

export type NewReport = Omit<IReport, 'id'> & { id: null };

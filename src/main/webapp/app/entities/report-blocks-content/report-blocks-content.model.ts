import { IReportBlocks } from 'app/entities/report-blocks/report-blocks.model';
import { IReportBlocksContentData } from '../report-blocks-content-data/report-blocks-content-data.model';

export interface IReportBlocksContent {
  id: number;
  type?: string | null;
  priorityNumber?: number | null;
  template?: string | null;
  isActive?: boolean | null;
  reportBlocks?: IReportBlocks | null;
  reportBlocksContentData: IReportBlocksContentData[] | [];
  newContentData: boolean | false;
}

export type NewReportBlocksContent = Omit<IReportBlocksContent, 'id'> & { id: null };

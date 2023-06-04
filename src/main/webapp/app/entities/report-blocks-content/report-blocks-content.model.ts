import { IReportBlocks } from 'app/entities/report-blocks/report-blocks.model';

export interface IReportBlocksContent {
  id: number;
  type?: string | null;
  priorityNumber?: number | null;
  template?: string | null;
  isActive?: boolean | null;
  reportBlocks?: Pick<IReportBlocks, 'id'> | null;
}

export type NewReportBlocksContent = Omit<IReportBlocksContent, 'id'> & { id: null };

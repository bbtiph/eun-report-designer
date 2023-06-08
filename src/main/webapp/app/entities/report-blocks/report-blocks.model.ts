import { ICountries } from 'app/entities/countries/countries.model';
import { IReport } from 'app/entities/report/report.model';
import { IReportBlocksContent } from '../report-blocks-content/report-blocks-content.model';

export interface IReportBlocks {
  id: number;
  name?: string | null;
  priorityNumber?: number | null;
  isActive?: boolean | null;
  config?: string | null;
  countryIds?: Pick<ICountries, 'id'>[] | null;
  report?: Pick<IReport, 'id'> | null;
  reportBlocksContents: IReportBlocksContent[] | null;
}

export interface ITemplate {
  name: string;
  columns: IColumn[];
}

export interface IColumn {
  name: string;
  index: number;
}

export type NewReportBlocks = Omit<IReportBlocks, 'id'> & { id: null };

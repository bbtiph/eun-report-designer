import { ICountries } from 'app/entities/countries/countries.model';
import { IReport } from 'app/entities/report/report.model';
import { IReportBlocksContent } from '../report-blocks-content/report-blocks-content.model';

export interface IReportBlocks {
  id: number;
  name?: string | null;
  priorityNumber?: number | null;
  isActive?: boolean | null;
  config?: string | null;
  countryIds?: ICountries[] | null;
  report?: IReport | null;
  reportBlocksContents: IReportBlocksContent[] | null;
}

export type NewReportBlocks = Omit<IReportBlocks, 'id'> & { id: null };

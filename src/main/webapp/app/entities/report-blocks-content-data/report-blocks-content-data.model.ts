import { IReportBlocksContent } from 'app/entities/report-blocks-content/report-blocks-content.model';
import { ICountries } from 'app/entities/countries/countries.model';

export interface IReportBlocksContentData {
  id: number;
  data?: string | null;
  reportBlocksContent?: IReportBlocksContent | null;
  country?: Pick<ICountries, 'id'> | null;
}

export type NewReportBlocksContentData = Omit<IReportBlocksContentData, 'id'> & { id: null };

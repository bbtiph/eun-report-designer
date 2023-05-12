import { ICountries } from 'app/entities/countries/countries.model';
import { IReport } from 'app/entities/report/report.model';

export interface IReportBlocks {
  id: number;
  countryName?: string | null;
  priorityNumber?: number | null;
  content?: string | null;
  isActive?: boolean | null;
  type?: string | null;
  sqlScript?: string | null;
  countryIds?: Pick<ICountries, 'id'>[] | null;
  reportIds?: Pick<IReport, 'id'>[] | null;
}

export type NewReportBlocks = Omit<IReportBlocks, 'id'> & { id: null };

import { ICountries } from 'app/entities/countries/countries.model';
import { IReport } from 'app/entities/report/report.model';

export interface IReportBlocks {
  id: number;
  name?: string | null;
  priorityNumber?: number | null;
  isActive?: boolean | null;
  config?: string | null;
  countryIds?: Pick<ICountries, 'id'>[] | null;
  report?: Pick<IReport, 'id'> | null;
}

export type NewReportBlocks = Omit<IReportBlocks, 'id'> & { id: null };

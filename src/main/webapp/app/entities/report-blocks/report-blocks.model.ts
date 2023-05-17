import { ICountries } from 'app/entities/countries/countries.model';

export interface IReportBlocks {
  id: number;
  countryName?: string | null;
  priorityNumber?: number | null;
  content?: string | null;
  isActive?: boolean | null;
  type?: string | null;
  sqlScript?: string | null;
  country?: Pick<ICountries, 'id'> | null;
}

export type NewReportBlocks = Omit<IReportBlocks, 'id'> & { id: null };

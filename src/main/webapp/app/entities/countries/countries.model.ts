import { IReportBlocks } from 'app/entities/report-blocks/report-blocks.model';

export interface ICountries {
  id: number;
  countryName?: string | null;
  countryCode?: string | null;
  reportBlockIds?: Pick<IReportBlocks, 'id'>[] | null;
}

export type NewCountries = Omit<ICountries, 'id'> & { id: null };

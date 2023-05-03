import { ICountries } from 'app/entities/countries/countries.model';

export interface IMinistry {
  id: number;
  languages?: string | null;
  formalName?: string | null;
  englishName?: string | null;
  acronym?: string | null;
  description?: string | null;
  website?: string | null;
  steercomMemberName?: string | null;
  steercomMemberEmail?: string | null;
  postalAddressRegion?: string | null;
  postalAddressPostalCode?: string | null;
  postalAddressCity?: string | null;
  postalAddressStreet?: string | null;
  status?: string | null;
  eunContactFirstname?: string | null;
  eunContactLastname?: string | null;
  eunContactEmail?: string | null;
  invoicingAddress?: string | null;
  financialCorrespondingEmail?: string | null;
  country?: Pick<ICountries, 'id'> | null;
}

export type NewMinistry = Omit<IMinistry, 'id'> & { id: null };

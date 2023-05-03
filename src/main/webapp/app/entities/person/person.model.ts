import dayjs from 'dayjs/esm';
import { ICountries } from 'app/entities/countries/countries.model';
import { GdprStatus } from 'app/entities/enumerations/gdpr-status.model';

export interface IPerson {
  id: number;
  eunDbId?: number | null;
  firstname?: string | null;
  lastname?: string | null;
  salutation?: number | null;
  mainContractEmail?: string | null;
  extraContractEmail?: string | null;
  languageMotherTongue?: string | null;
  languageOther?: string | null;
  description?: string | null;
  website?: string | null;
  mobile?: string | null;
  phone?: string | null;
  socialNetworkContacts?: string | null;
  status?: string | null;
  gdprStatus?: GdprStatus | null;
  lastLoginDate?: dayjs.Dayjs | null;
  country?: Pick<ICountries, 'id'> | null;
}

export type NewPerson = Omit<IPerson, 'id'> & { id: null };

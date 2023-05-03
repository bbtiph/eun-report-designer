import dayjs from 'dayjs/esm';
import { ICountries } from 'app/entities/countries/countries.model';

export interface IOperationalBodyMember {
  id: number;
  personId?: number | null;
  position?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  department?: string | null;
  eunContactFirstname?: string | null;
  eunContactLastname?: string | null;
  cooperationField?: string | null;
  status?: string | null;
  country?: Pick<ICountries, 'id'> | null;
}

export type NewOperationalBodyMember = Omit<IOperationalBodyMember, 'id'> & { id: null };

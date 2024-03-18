import dayjs from 'dayjs/esm';
import { ICountries } from 'app/entities/countries/countries.model';

export interface IMOEParticipationReferences {
  id: number;
  name?: string | null;
  type?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  lastModifiedBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  countries?: Pick<ICountries, 'id'>[] | null;
}

export type NewMOEParticipationReferences = Omit<IMOEParticipationReferences, 'id'> & { id: null };

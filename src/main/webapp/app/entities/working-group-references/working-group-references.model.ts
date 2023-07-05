import dayjs from 'dayjs/esm';

export interface IWorkingGroupReferences {
  id: number;
  countryCode?: string | null;
  countryName?: string | null;
  countryRepresentativeFirstName?: string | null;
  countryRepresentativeLastName?: string | null;
  countryRepresentativeMail?: string | null;
  countryRepresentativePosition?: string | null;
  countryRepresentativeStartDate?: dayjs.Dayjs | null;
  countryRepresentativeEndDate?: dayjs.Dayjs | null;
  countryRepresentativeMinistry?: string | null;
  countryRepresentativeDepartment?: string | null;
  contactEunFirstName?: string | null;
  contactEunLastName?: string | null;
  type?: string | null;
  isActive?: boolean | null;
}

export type NewWorkingGroupReferences = Omit<IWorkingGroupReferences, 'id'> & { id: null };

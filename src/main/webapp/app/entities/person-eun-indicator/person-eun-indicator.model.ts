import dayjs from 'dayjs/esm';

export interface IPersonEunIndicator {
  id: number;
  nCount?: number | null;
  countryId?: number | null;
  projectId?: number | null;
  projectUrl?: string | null;
  countryName?: string | null;
  projectName?: string | null;
  reportsProjectId?: number | null;
  createdBy?: string | null;
  lastModifiedBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export type NewPersonEunIndicator = Omit<IPersonEunIndicator, 'id'> & { id: null };

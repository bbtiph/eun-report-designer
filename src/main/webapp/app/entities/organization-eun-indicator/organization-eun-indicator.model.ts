import dayjs from 'dayjs/esm';

export interface IOrganizationEunIndicator {
  id: number;
  period?: number | null;
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

export type NewOrganizationEunIndicator = Omit<IOrganizationEunIndicator, 'id'> & { id: null };

import dayjs from 'dayjs/esm';

export interface IProjectPartner {
  id: number;
  odataEtag?: string | null;
  no?: number | null;
  jobNo?: string | null;
  vendorCode?: string | null;
  vendorName?: string | null;
  countryCode?: string | null;
  countryName?: string | null;
  partnerAmount?: number | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  lastModifiedBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export type NewProjectPartner = Omit<IProjectPartner, 'id'> & { id: null };

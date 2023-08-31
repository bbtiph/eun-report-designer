import dayjs from 'dayjs/esm';

export interface IReportTemplate {
  id: number;
  name?: string | null;
  description?: string | null;
  type?: string | null;
  isActive?: boolean | null;
  file?: string | null;
  fileContentType?: string | null;
  createdBy?: string | null;
  lastModifiedBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export type NewReportTemplate = Omit<IReportTemplate, 'id'> & { id: null };

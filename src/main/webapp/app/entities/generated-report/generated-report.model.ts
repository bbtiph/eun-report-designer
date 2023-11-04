import dayjs from 'dayjs/esm';

export interface IGeneratedReport {
  id: number;
  name?: string | null;
  description?: string | null;
  requestBody?: string | null;
  isActive?: boolean | null;
  file?: string | null;
  fileContentType?: string | null;
  createdBy?: string | null;
  lastModifiedBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export type NewGeneratedReport = Omit<IGeneratedReport, 'id'> & { id: null };

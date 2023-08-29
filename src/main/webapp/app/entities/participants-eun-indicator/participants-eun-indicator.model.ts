import dayjs from 'dayjs/esm';

export interface IParticipantsEunIndicator {
  id: number;
  period?: number | null;
  nCount?: number | null;
  courseId?: string | null;
  courseName?: string | null;
  countryCode?: string | null;
  createdBy?: string | null;
  lastModifiedBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export type NewParticipantsEunIndicator = Omit<IParticipantsEunIndicator, 'id'> & { id: null };

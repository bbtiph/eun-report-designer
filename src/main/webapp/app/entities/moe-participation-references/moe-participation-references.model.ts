import dayjs from 'dayjs/esm';

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
}

export type NewMOEParticipationReferences = Omit<IMOEParticipationReferences, 'id'> & { id: null };

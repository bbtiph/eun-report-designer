import dayjs from 'dayjs/esm';

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
}

export type NewOperationalBodyMember = Omit<IOperationalBodyMember, 'id'> & { id: null };

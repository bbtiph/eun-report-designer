import dayjs from 'dayjs/esm';

export interface IEvent {
  id: number;
  type?: string | null;
  location?: string | null;
  title?: string | null;
  description?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  url?: string | null;
  eunContact?: string | null;
  courseModules?: string | null;
  courseDuration?: number | null;
  courseType?: string | null;
  modules?: number | null;
  status?: string | null;
  engagementRate?: number | null;
  completionRate?: number | null;
  name?: string | null;
}

export type NewEvent = Omit<IEvent, 'id'> & { id: null };

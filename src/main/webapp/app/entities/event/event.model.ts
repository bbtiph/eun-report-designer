import dayjs from 'dayjs/esm';
import { IEventInOrganization } from 'app/entities/event-in-organization/event-in-organization.model';
import { IEventParticipant } from 'app/entities/event-participant/event-participant.model';

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
  eventInOrganization?: Pick<IEventInOrganization, 'id'> | null;
  eventParticipant?: Pick<IEventParticipant, 'id'> | null;
}

export type NewEvent = Omit<IEvent, 'id'> & { id: null };

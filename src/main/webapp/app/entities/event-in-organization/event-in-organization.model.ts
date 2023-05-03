import { IEvent } from 'app/entities/event/event.model';
import { IOrganization } from 'app/entities/organization/organization.model';

export interface IEventInOrganization {
  id: number;
  event?: Pick<IEvent, 'id'> | null;
  organization?: Pick<IOrganization, 'id'> | null;
}

export type NewEventInOrganization = Omit<IEventInOrganization, 'id'> & { id: null };

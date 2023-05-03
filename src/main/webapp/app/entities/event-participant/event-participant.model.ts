import { IEvent } from 'app/entities/event/event.model';
import { IPerson } from 'app/entities/person/person.model';

export interface IEventParticipant {
  id: number;
  type?: string | null;
  event?: Pick<IEvent, 'id'> | null;
  person?: Pick<IPerson, 'id'> | null;
}

export type NewEventParticipant = Omit<IEventParticipant, 'id'> & { id: null };

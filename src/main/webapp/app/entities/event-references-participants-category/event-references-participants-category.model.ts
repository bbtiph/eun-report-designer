import { IEventReferences } from 'app/entities/event-references/event-references.model';

export interface IEventReferencesParticipantsCategory {
  id: number;
  category?: string | null;
  participantsCount?: number | null;
  eventReference?: Pick<IEventReferences, 'id'> | null;
}

export type NewEventReferencesParticipantsCategory = Omit<IEventReferencesParticipantsCategory, 'id'> & { id: null };

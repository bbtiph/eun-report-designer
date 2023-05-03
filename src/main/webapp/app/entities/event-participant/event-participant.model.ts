export interface IEventParticipant {
  id: number;
  type?: string | null;
}

export type NewEventParticipant = Omit<IEventParticipant, 'id'> & { id: null };

export interface IEventReferences {
  id: number;
  name?: string | null;
  type?: string | null;
}

export type NewEventReferences = Omit<IEventReferences, 'id'> & { id: null };

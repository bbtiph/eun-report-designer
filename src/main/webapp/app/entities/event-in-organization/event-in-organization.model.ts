export interface IEventInOrganization {
  id: number;
}

export type NewEventInOrganization = Omit<IEventInOrganization, 'id'> & { id: null };

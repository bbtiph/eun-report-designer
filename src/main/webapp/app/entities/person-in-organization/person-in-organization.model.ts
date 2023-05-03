export interface IPersonInOrganization {
  id: number;
  roleInOrganization?: string | null;
}

export type NewPersonInOrganization = Omit<IPersonInOrganization, 'id'> & { id: null };

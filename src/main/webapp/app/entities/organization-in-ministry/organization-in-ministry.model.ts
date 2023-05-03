export interface IOrganizationInMinistry {
  id: number;
  status?: string | null;
}

export type NewOrganizationInMinistry = Omit<IOrganizationInMinistry, 'id'> & { id: null };

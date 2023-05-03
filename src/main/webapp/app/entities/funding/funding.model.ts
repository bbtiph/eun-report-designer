export interface IFunding {
  id: number;
  name?: string | null;
  type?: string | null;
  parentId?: number | null;
  description?: string | null;
}

export type NewFunding = Omit<IFunding, 'id'> & { id: null };

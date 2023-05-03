export interface IOperationalBody {
  id: number;
  name?: string | null;
  acronym?: string | null;
  description?: string | null;
  type?: string | null;
  status?: string | null;
}

export type NewOperationalBody = Omit<IOperationalBody, 'id'> & { id: null };

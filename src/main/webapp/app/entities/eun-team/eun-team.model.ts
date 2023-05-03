export interface IEunTeam {
  id: number;
  name?: string | null;
  description?: string | null;
}

export type NewEunTeam = Omit<IEunTeam, 'id'> & { id: null };

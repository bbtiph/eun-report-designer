export interface IEunTeamMember {
  id: number;
  role?: string | null;
  status?: string | null;
}

export type NewEunTeamMember = Omit<IEunTeamMember, 'id'> & { id: null };

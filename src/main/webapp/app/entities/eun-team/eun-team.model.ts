import { IEunTeamMember } from 'app/entities/eun-team-member/eun-team-member.model';

export interface IEunTeam {
  id: number;
  name?: string | null;
  description?: string | null;
  eunTeamMember?: Pick<IEunTeamMember, 'id'> | null;
}

export type NewEunTeam = Omit<IEunTeam, 'id'> & { id: null };

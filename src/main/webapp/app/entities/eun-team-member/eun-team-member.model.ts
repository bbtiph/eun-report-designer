import { IEunTeam } from 'app/entities/eun-team/eun-team.model';
import { IPerson } from 'app/entities/person/person.model';

export interface IEunTeamMember {
  id: number;
  role?: string | null;
  status?: string | null;
  team?: Pick<IEunTeam, 'id'> | null;
  person?: Pick<IPerson, 'id'> | null;
}

export type NewEunTeamMember = Omit<IEunTeamMember, 'id'> & { id: null };

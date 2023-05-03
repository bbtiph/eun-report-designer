import dayjs from 'dayjs/esm';
import { IEunTeamMember } from 'app/entities/eun-team-member/eun-team-member.model';
import { IEventParticipant } from 'app/entities/event-participant/event-participant.model';
import { IPersonInOrganization } from 'app/entities/person-in-organization/person-in-organization.model';
import { IPersonInProject } from 'app/entities/person-in-project/person-in-project.model';
import { GdprStatus } from 'app/entities/enumerations/gdpr-status.model';

export interface IPerson {
  id: number;
  eunDbId?: number | null;
  firstname?: string | null;
  lastname?: string | null;
  salutation?: number | null;
  mainContractEmail?: string | null;
  extraContractEmail?: string | null;
  languageMotherTongue?: string | null;
  languageOther?: string | null;
  description?: string | null;
  website?: string | null;
  mobile?: string | null;
  phone?: string | null;
  socialNetworkContacts?: string | null;
  status?: string | null;
  gdprStatus?: GdprStatus | null;
  lastLoginDate?: dayjs.Dayjs | null;
  eunTeamMember?: Pick<IEunTeamMember, 'id'> | null;
  eventParticipant?: Pick<IEventParticipant, 'id'> | null;
  personInOrganization?: Pick<IPersonInOrganization, 'id'> | null;
  personInProject?: Pick<IPersonInProject, 'id'> | null;
}

export type NewPerson = Omit<IPerson, 'id'> & { id: null };

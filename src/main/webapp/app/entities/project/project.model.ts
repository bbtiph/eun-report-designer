import dayjs from 'dayjs/esm';
import { IOrganizationInProject } from 'app/entities/organization-in-project/organization-in-project.model';
import { IPersonInProject } from 'app/entities/person-in-project/person-in-project.model';
import { ProjectStatus } from 'app/entities/enumerations/project-status.model';

export interface IProject {
  id: number;
  status?: ProjectStatus | null;
  supportedCountryIds?: string | null;
  supportedLanguageIds?: string | null;
  name?: string | null;
  acronym?: string | null;
  period?: string | null;
  description?: string | null;
  contactEmail?: string | null;
  contactName?: string | null;
  totalBudget?: number | null;
  totalFunding?: number | null;
  totalBudgetForEun?: number | null;
  totalFundingForEun?: number | null;
  fundingValue?: number | null;
  percentageOfFunding?: number | null;
  percentageOfIndirectCosts?: number | null;
  percentageOfFundingForEun?: number | null;
  percentageOfIndirectCostsForEun?: number | null;
  fundingExtraComment?: string | null;
  programme?: string | null;
  euDg?: string | null;
  roleOfEun?: string | null;
  summary?: string | null;
  mainTasks?: string | null;
  expectedOutcomes?: string | null;
  euCallReference?: string | null;
  euProjectReference?: string | null;
  euCallDeadline?: string | null;
  projectManager?: string | null;
  referenceNumberOfProject?: number | null;
  eunTeam?: string | null;
  sysCreatTimestamp?: dayjs.Dayjs | null;
  sysCreatIpAddress?: string | null;
  sysModifTimestamp?: dayjs.Dayjs | null;
  sysModifIpAddress?: string | null;
  organizationInProject?: Pick<IOrganizationInProject, 'id'> | null;
  personInProject?: Pick<IPersonInProject, 'id'> | null;
}

export type NewProject = Omit<IProject, 'id'> & { id: null };

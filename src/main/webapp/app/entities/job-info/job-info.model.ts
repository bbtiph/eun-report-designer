import dayjs from 'dayjs/esm';

export interface IJobInfo {
  id: number;
  odataEtag?: string | null;
  externalId?: string | null;
  jobNumber?: string | null;
  description?: string | null;
  description2?: string | null;
  billToCustomerNo?: string | null;
  billToName?: string | null;
  billToCountryRegionCode?: string | null;
  sellToContact?: string | null;
  yourReference?: string | null;
  contractNo?: string | null;
  statusProposal?: string | null;
  startingDate?: dayjs.Dayjs | null;
  endingDate?: dayjs.Dayjs | null;
  durationInMonths?: number | null;
  projectManager?: string | null;
  projectManagerMail?: string | null;
  eunRole?: string | null;
  visaNumber?: string | null;
  jobType?: string | null;
  jobTypeText?: string | null;
  jobProgram?: string | null;
  programManager?: string | null;
  budgetEUN?: number | null;
  fundingEUN?: number | null;
  fundingRate?: number | null;
  budgetConsortium?: number | null;
  fundingConsortium?: number | null;
  overheadPerc?: number | null;
}

export type NewJobInfo = Omit<IJobInfo, 'id'> & { id: null };

import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IJobInfo, NewJobInfo } from '../job-info.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IJobInfo for edit and NewJobInfoFormGroupInput for create.
 */
type JobInfoFormGroupInput = IJobInfo | PartialWithRequiredKeyOf<NewJobInfo>;

type JobInfoFormDefaults = Pick<NewJobInfo, 'id'>;

type JobInfoFormGroupContent = {
  id: FormControl<IJobInfo['id'] | NewJobInfo['id']>;
  odataEtag: FormControl<IJobInfo['odataEtag']>;
  externalId: FormControl<IJobInfo['externalId']>;
  jobNumber: FormControl<IJobInfo['jobNumber']>;
  description: FormControl<IJobInfo['description']>;
  description2: FormControl<IJobInfo['description2']>;
  billToCustomerNo: FormControl<IJobInfo['billToCustomerNo']>;
  billToName: FormControl<IJobInfo['billToName']>;
  billToCountryRegionCode: FormControl<IJobInfo['billToCountryRegionCode']>;
  sellToContact: FormControl<IJobInfo['sellToContact']>;
  yourReference: FormControl<IJobInfo['yourReference']>;
  contractNo: FormControl<IJobInfo['contractNo']>;
  statusProposal: FormControl<IJobInfo['statusProposal']>;
  startingDate: FormControl<IJobInfo['startingDate']>;
  endingDate: FormControl<IJobInfo['endingDate']>;
  durationInMonths: FormControl<IJobInfo['durationInMonths']>;
  projectManager: FormControl<IJobInfo['projectManager']>;
  projectManagerMail: FormControl<IJobInfo['projectManagerMail']>;
  eunRole: FormControl<IJobInfo['eunRole']>;
  visaNumber: FormControl<IJobInfo['visaNumber']>;
  jobType: FormControl<IJobInfo['jobType']>;
  jobTypeText: FormControl<IJobInfo['jobTypeText']>;
  jobProgram: FormControl<IJobInfo['jobProgram']>;
  programManager: FormControl<IJobInfo['programManager']>;
  budgetEUN: FormControl<IJobInfo['budgetEUN']>;
  fundingEUN: FormControl<IJobInfo['fundingEUN']>;
  fundingRate: FormControl<IJobInfo['fundingRate']>;
  budgetConsortium: FormControl<IJobInfo['budgetConsortium']>;
  fundingConsortium: FormControl<IJobInfo['fundingConsortium']>;
  overheadPerc: FormControl<IJobInfo['overheadPerc']>;
};

export type JobInfoFormGroup = FormGroup<JobInfoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class JobInfoFormService {
  createJobInfoFormGroup(jobInfo: JobInfoFormGroupInput = { id: null }): JobInfoFormGroup {
    const jobInfoRawValue = {
      ...this.getFormDefaults(),
      ...jobInfo,
    };
    return new FormGroup<JobInfoFormGroupContent>({
      id: new FormControl(
        { value: jobInfoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      odataEtag: new FormControl(jobInfoRawValue.odataEtag),
      externalId: new FormControl(jobInfoRawValue.externalId),
      jobNumber: new FormControl(jobInfoRawValue.jobNumber),
      description: new FormControl(jobInfoRawValue.description),
      description2: new FormControl(jobInfoRawValue.description2),
      billToCustomerNo: new FormControl(jobInfoRawValue.billToCustomerNo),
      billToName: new FormControl(jobInfoRawValue.billToName),
      billToCountryRegionCode: new FormControl(jobInfoRawValue.billToCountryRegionCode),
      sellToContact: new FormControl(jobInfoRawValue.sellToContact),
      yourReference: new FormControl(jobInfoRawValue.yourReference),
      contractNo: new FormControl(jobInfoRawValue.contractNo),
      statusProposal: new FormControl(jobInfoRawValue.statusProposal),
      startingDate: new FormControl(jobInfoRawValue.startingDate),
      endingDate: new FormControl(jobInfoRawValue.endingDate),
      durationInMonths: new FormControl(jobInfoRawValue.durationInMonths),
      projectManager: new FormControl(jobInfoRawValue.projectManager),
      projectManagerMail: new FormControl(jobInfoRawValue.projectManagerMail),
      eunRole: new FormControl(jobInfoRawValue.eunRole),
      visaNumber: new FormControl(jobInfoRawValue.visaNumber),
      jobType: new FormControl(jobInfoRawValue.jobType),
      jobTypeText: new FormControl(jobInfoRawValue.jobTypeText),
      jobProgram: new FormControl(jobInfoRawValue.jobProgram),
      programManager: new FormControl(jobInfoRawValue.programManager),
      budgetEUN: new FormControl(jobInfoRawValue.budgetEUN),
      fundingEUN: new FormControl(jobInfoRawValue.fundingEUN),
      fundingRate: new FormControl(jobInfoRawValue.fundingRate),
      budgetConsortium: new FormControl(jobInfoRawValue.budgetConsortium),
      fundingConsortium: new FormControl(jobInfoRawValue.fundingConsortium),
      overheadPerc: new FormControl(jobInfoRawValue.overheadPerc),
    });
  }

  getJobInfo(form: JobInfoFormGroup): IJobInfo | NewJobInfo {
    return form.getRawValue() as IJobInfo | NewJobInfo;
  }

  resetForm(form: JobInfoFormGroup, jobInfo: JobInfoFormGroupInput): void {
    const jobInfoRawValue = { ...this.getFormDefaults(), ...jobInfo };
    form.reset(
      {
        ...jobInfoRawValue,
        id: { value: jobInfoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): JobInfoFormDefaults {
    return {
      id: null,
    };
  }
}

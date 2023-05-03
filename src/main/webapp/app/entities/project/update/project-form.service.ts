import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProject, NewProject } from '../project.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProject for edit and NewProjectFormGroupInput for create.
 */
type ProjectFormGroupInput = IProject | PartialWithRequiredKeyOf<NewProject>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProject | NewProject> = Omit<T, 'sysCreatTimestamp' | 'sysModifTimestamp'> & {
  sysCreatTimestamp?: string | null;
  sysModifTimestamp?: string | null;
};

type ProjectFormRawValue = FormValueOf<IProject>;

type NewProjectFormRawValue = FormValueOf<NewProject>;

type ProjectFormDefaults = Pick<NewProject, 'id' | 'sysCreatTimestamp' | 'sysModifTimestamp'>;

type ProjectFormGroupContent = {
  id: FormControl<ProjectFormRawValue['id'] | NewProject['id']>;
  status: FormControl<ProjectFormRawValue['status']>;
  supportedCountryIds: FormControl<ProjectFormRawValue['supportedCountryIds']>;
  supportedLanguageIds: FormControl<ProjectFormRawValue['supportedLanguageIds']>;
  name: FormControl<ProjectFormRawValue['name']>;
  acronym: FormControl<ProjectFormRawValue['acronym']>;
  period: FormControl<ProjectFormRawValue['period']>;
  description: FormControl<ProjectFormRawValue['description']>;
  contactEmail: FormControl<ProjectFormRawValue['contactEmail']>;
  contactName: FormControl<ProjectFormRawValue['contactName']>;
  totalBudget: FormControl<ProjectFormRawValue['totalBudget']>;
  totalFunding: FormControl<ProjectFormRawValue['totalFunding']>;
  totalBudgetForEun: FormControl<ProjectFormRawValue['totalBudgetForEun']>;
  totalFundingForEun: FormControl<ProjectFormRawValue['totalFundingForEun']>;
  fundingValue: FormControl<ProjectFormRawValue['fundingValue']>;
  percentageOfFunding: FormControl<ProjectFormRawValue['percentageOfFunding']>;
  percentageOfIndirectCosts: FormControl<ProjectFormRawValue['percentageOfIndirectCosts']>;
  percentageOfFundingForEun: FormControl<ProjectFormRawValue['percentageOfFundingForEun']>;
  percentageOfIndirectCostsForEun: FormControl<ProjectFormRawValue['percentageOfIndirectCostsForEun']>;
  fundingExtraComment: FormControl<ProjectFormRawValue['fundingExtraComment']>;
  programme: FormControl<ProjectFormRawValue['programme']>;
  euDg: FormControl<ProjectFormRawValue['euDg']>;
  roleOfEun: FormControl<ProjectFormRawValue['roleOfEun']>;
  summary: FormControl<ProjectFormRawValue['summary']>;
  mainTasks: FormControl<ProjectFormRawValue['mainTasks']>;
  expectedOutcomes: FormControl<ProjectFormRawValue['expectedOutcomes']>;
  euCallReference: FormControl<ProjectFormRawValue['euCallReference']>;
  euProjectReference: FormControl<ProjectFormRawValue['euProjectReference']>;
  euCallDeadline: FormControl<ProjectFormRawValue['euCallDeadline']>;
  projectManager: FormControl<ProjectFormRawValue['projectManager']>;
  referenceNumberOfProject: FormControl<ProjectFormRawValue['referenceNumberOfProject']>;
  eunTeam: FormControl<ProjectFormRawValue['eunTeam']>;
  sysCreatTimestamp: FormControl<ProjectFormRawValue['sysCreatTimestamp']>;
  sysCreatIpAddress: FormControl<ProjectFormRawValue['sysCreatIpAddress']>;
  sysModifTimestamp: FormControl<ProjectFormRawValue['sysModifTimestamp']>;
  sysModifIpAddress: FormControl<ProjectFormRawValue['sysModifIpAddress']>;
  funding: FormControl<ProjectFormRawValue['funding']>;
};

export type ProjectFormGroup = FormGroup<ProjectFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProjectFormService {
  createProjectFormGroup(project: ProjectFormGroupInput = { id: null }): ProjectFormGroup {
    const projectRawValue = this.convertProjectToProjectRawValue({
      ...this.getFormDefaults(),
      ...project,
    });
    return new FormGroup<ProjectFormGroupContent>({
      id: new FormControl(
        { value: projectRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      status: new FormControl(projectRawValue.status, {
        validators: [Validators.required],
      }),
      supportedCountryIds: new FormControl(projectRawValue.supportedCountryIds),
      supportedLanguageIds: new FormControl(projectRawValue.supportedLanguageIds),
      name: new FormControl(projectRawValue.name, {
        validators: [Validators.required],
      }),
      acronym: new FormControl(projectRawValue.acronym),
      period: new FormControl(projectRawValue.period),
      description: new FormControl(projectRawValue.description, {
        validators: [Validators.required],
      }),
      contactEmail: new FormControl(projectRawValue.contactEmail),
      contactName: new FormControl(projectRawValue.contactName),
      totalBudget: new FormControl(projectRawValue.totalBudget),
      totalFunding: new FormControl(projectRawValue.totalFunding),
      totalBudgetForEun: new FormControl(projectRawValue.totalBudgetForEun),
      totalFundingForEun: new FormControl(projectRawValue.totalFundingForEun),
      fundingValue: new FormControl(projectRawValue.fundingValue),
      percentageOfFunding: new FormControl(projectRawValue.percentageOfFunding),
      percentageOfIndirectCosts: new FormControl(projectRawValue.percentageOfIndirectCosts),
      percentageOfFundingForEun: new FormControl(projectRawValue.percentageOfFundingForEun),
      percentageOfIndirectCostsForEun: new FormControl(projectRawValue.percentageOfIndirectCostsForEun),
      fundingExtraComment: new FormControl(projectRawValue.fundingExtraComment),
      programme: new FormControl(projectRawValue.programme),
      euDg: new FormControl(projectRawValue.euDg),
      roleOfEun: new FormControl(projectRawValue.roleOfEun),
      summary: new FormControl(projectRawValue.summary),
      mainTasks: new FormControl(projectRawValue.mainTasks),
      expectedOutcomes: new FormControl(projectRawValue.expectedOutcomes),
      euCallReference: new FormControl(projectRawValue.euCallReference),
      euProjectReference: new FormControl(projectRawValue.euProjectReference),
      euCallDeadline: new FormControl(projectRawValue.euCallDeadline),
      projectManager: new FormControl(projectRawValue.projectManager),
      referenceNumberOfProject: new FormControl(projectRawValue.referenceNumberOfProject),
      eunTeam: new FormControl(projectRawValue.eunTeam),
      sysCreatTimestamp: new FormControl(projectRawValue.sysCreatTimestamp),
      sysCreatIpAddress: new FormControl(projectRawValue.sysCreatIpAddress),
      sysModifTimestamp: new FormControl(projectRawValue.sysModifTimestamp),
      sysModifIpAddress: new FormControl(projectRawValue.sysModifIpAddress),
      funding: new FormControl(projectRawValue.funding),
    });
  }

  getProject(form: ProjectFormGroup): IProject | NewProject {
    return this.convertProjectRawValueToProject(form.getRawValue() as ProjectFormRawValue | NewProjectFormRawValue);
  }

  resetForm(form: ProjectFormGroup, project: ProjectFormGroupInput): void {
    const projectRawValue = this.convertProjectToProjectRawValue({ ...this.getFormDefaults(), ...project });
    form.reset(
      {
        ...projectRawValue,
        id: { value: projectRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProjectFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      sysCreatTimestamp: currentTime,
      sysModifTimestamp: currentTime,
    };
  }

  private convertProjectRawValueToProject(rawProject: ProjectFormRawValue | NewProjectFormRawValue): IProject | NewProject {
    return {
      ...rawProject,
      sysCreatTimestamp: dayjs(rawProject.sysCreatTimestamp, DATE_TIME_FORMAT),
      sysModifTimestamp: dayjs(rawProject.sysModifTimestamp, DATE_TIME_FORMAT),
    };
  }

  private convertProjectToProjectRawValue(
    project: IProject | (Partial<NewProject> & ProjectFormDefaults)
  ): ProjectFormRawValue | PartialWithRequiredKeyOf<NewProjectFormRawValue> {
    return {
      ...project,
      sysCreatTimestamp: project.sysCreatTimestamp ? project.sysCreatTimestamp.format(DATE_TIME_FORMAT) : undefined,
      sysModifTimestamp: project.sysModifTimestamp ? project.sysModifTimestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}

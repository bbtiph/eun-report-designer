import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOrganizationInProject, NewOrganizationInProject } from '../organization-in-project.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrganizationInProject for edit and NewOrganizationInProjectFormGroupInput for create.
 */
type OrganizationInProjectFormGroupInput = IOrganizationInProject | PartialWithRequiredKeyOf<NewOrganizationInProject>;

type OrganizationInProjectFormDefaults = Pick<
  NewOrganizationInProject,
  | 'id'
  | 'schoolRegistrationPossible'
  | 'teacherParticipationPossible'
  | 'ambassadorsPilotTeachersLeadingTeachersIdentified'
  | 'usersCanRegisterToPortal'
>;

type OrganizationInProjectFormGroupContent = {
  id: FormControl<IOrganizationInProject['id'] | NewOrganizationInProject['id']>;
  status: FormControl<IOrganizationInProject['status']>;
  joinDate: FormControl<IOrganizationInProject['joinDate']>;
  fundingForOrganization: FormControl<IOrganizationInProject['fundingForOrganization']>;
  participationToMatchingFunding: FormControl<IOrganizationInProject['participationToMatchingFunding']>;
  schoolRegistrationPossible: FormControl<IOrganizationInProject['schoolRegistrationPossible']>;
  teacherParticipationPossible: FormControl<IOrganizationInProject['teacherParticipationPossible']>;
  ambassadorsPilotTeachersLeadingTeachersIdentified: FormControl<
    IOrganizationInProject['ambassadorsPilotTeachersLeadingTeachersIdentified']
  >;
  usersCanRegisterToPortal: FormControl<IOrganizationInProject['usersCanRegisterToPortal']>;
  project: FormControl<IOrganizationInProject['project']>;
  organization: FormControl<IOrganizationInProject['organization']>;
};

export type OrganizationInProjectFormGroup = FormGroup<OrganizationInProjectFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrganizationInProjectFormService {
  createOrganizationInProjectFormGroup(
    organizationInProject: OrganizationInProjectFormGroupInput = { id: null }
  ): OrganizationInProjectFormGroup {
    const organizationInProjectRawValue = {
      ...this.getFormDefaults(),
      ...organizationInProject,
    };
    return new FormGroup<OrganizationInProjectFormGroupContent>({
      id: new FormControl(
        { value: organizationInProjectRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      status: new FormControl(organizationInProjectRawValue.status),
      joinDate: new FormControl(organizationInProjectRawValue.joinDate),
      fundingForOrganization: new FormControl(organizationInProjectRawValue.fundingForOrganization),
      participationToMatchingFunding: new FormControl(organizationInProjectRawValue.participationToMatchingFunding),
      schoolRegistrationPossible: new FormControl(organizationInProjectRawValue.schoolRegistrationPossible),
      teacherParticipationPossible: new FormControl(organizationInProjectRawValue.teacherParticipationPossible),
      ambassadorsPilotTeachersLeadingTeachersIdentified: new FormControl(
        organizationInProjectRawValue.ambassadorsPilotTeachersLeadingTeachersIdentified
      ),
      usersCanRegisterToPortal: new FormControl(organizationInProjectRawValue.usersCanRegisterToPortal),
      project: new FormControl(organizationInProjectRawValue.project),
      organization: new FormControl(organizationInProjectRawValue.organization),
    });
  }

  getOrganizationInProject(form: OrganizationInProjectFormGroup): IOrganizationInProject | NewOrganizationInProject {
    return form.getRawValue() as IOrganizationInProject | NewOrganizationInProject;
  }

  resetForm(form: OrganizationInProjectFormGroup, organizationInProject: OrganizationInProjectFormGroupInput): void {
    const organizationInProjectRawValue = { ...this.getFormDefaults(), ...organizationInProject };
    form.reset(
      {
        ...organizationInProjectRawValue,
        id: { value: organizationInProjectRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OrganizationInProjectFormDefaults {
    return {
      id: null,
      schoolRegistrationPossible: false,
      teacherParticipationPossible: false,
      ambassadorsPilotTeachersLeadingTeachersIdentified: false,
      usersCanRegisterToPortal: false,
    };
  }
}

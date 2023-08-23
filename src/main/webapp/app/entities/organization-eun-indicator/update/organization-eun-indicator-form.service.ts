import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOrganizationEunIndicator, NewOrganizationEunIndicator } from '../organization-eun-indicator.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrganizationEunIndicator for edit and NewOrganizationEunIndicatorFormGroupInput for create.
 */
type OrganizationEunIndicatorFormGroupInput = IOrganizationEunIndicator | PartialWithRequiredKeyOf<NewOrganizationEunIndicator>;

type OrganizationEunIndicatorFormDefaults = Pick<NewOrganizationEunIndicator, 'id'>;

type OrganizationEunIndicatorFormGroupContent = {
  id: FormControl<IOrganizationEunIndicator['id'] | NewOrganizationEunIndicator['id']>;
  nCount: FormControl<IOrganizationEunIndicator['nCount']>;
  countryId: FormControl<IOrganizationEunIndicator['countryId']>;
  projectId: FormControl<IOrganizationEunIndicator['projectId']>;
  projectUrl: FormControl<IOrganizationEunIndicator['projectUrl']>;
  countryName: FormControl<IOrganizationEunIndicator['countryName']>;
  projectName: FormControl<IOrganizationEunIndicator['projectName']>;
  reportsProjectId: FormControl<IOrganizationEunIndicator['reportsProjectId']>;
  createdBy: FormControl<IOrganizationEunIndicator['createdBy']>;
  lastModifiedBy: FormControl<IOrganizationEunIndicator['lastModifiedBy']>;
  createdDate: FormControl<IOrganizationEunIndicator['createdDate']>;
  lastModifiedDate: FormControl<IOrganizationEunIndicator['lastModifiedDate']>;
};

export type OrganizationEunIndicatorFormGroup = FormGroup<OrganizationEunIndicatorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrganizationEunIndicatorFormService {
  createOrganizationEunIndicatorFormGroup(
    organizationEunIndicator: OrganizationEunIndicatorFormGroupInput = { id: null }
  ): OrganizationEunIndicatorFormGroup {
    const organizationEunIndicatorRawValue = {
      ...this.getFormDefaults(),
      ...organizationEunIndicator,
    };
    return new FormGroup<OrganizationEunIndicatorFormGroupContent>({
      id: new FormControl(
        { value: organizationEunIndicatorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nCount: new FormControl(organizationEunIndicatorRawValue.nCount),
      countryId: new FormControl(organizationEunIndicatorRawValue.countryId),
      projectId: new FormControl(organizationEunIndicatorRawValue.projectId),
      projectUrl: new FormControl(organizationEunIndicatorRawValue.projectUrl),
      countryName: new FormControl(organizationEunIndicatorRawValue.countryName),
      projectName: new FormControl(organizationEunIndicatorRawValue.projectName),
      reportsProjectId: new FormControl(organizationEunIndicatorRawValue.reportsProjectId),
      createdBy: new FormControl(organizationEunIndicatorRawValue.createdBy),
      lastModifiedBy: new FormControl(organizationEunIndicatorRawValue.lastModifiedBy),
      createdDate: new FormControl(organizationEunIndicatorRawValue.createdDate),
      lastModifiedDate: new FormControl(organizationEunIndicatorRawValue.lastModifiedDate),
    });
  }

  getOrganizationEunIndicator(form: OrganizationEunIndicatorFormGroup): IOrganizationEunIndicator | NewOrganizationEunIndicator {
    return form.getRawValue() as IOrganizationEunIndicator | NewOrganizationEunIndicator;
  }

  resetForm(form: OrganizationEunIndicatorFormGroup, organizationEunIndicator: OrganizationEunIndicatorFormGroupInput): void {
    const organizationEunIndicatorRawValue = { ...this.getFormDefaults(), ...organizationEunIndicator };
    form.reset(
      {
        ...organizationEunIndicatorRawValue,
        id: { value: organizationEunIndicatorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OrganizationEunIndicatorFormDefaults {
    return {
      id: null,
    };
  }
}

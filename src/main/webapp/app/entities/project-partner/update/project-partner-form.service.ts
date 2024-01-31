import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProjectPartner, NewProjectPartner } from '../project-partner.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProjectPartner for edit and NewProjectPartnerFormGroupInput for create.
 */
type ProjectPartnerFormGroupInput = IProjectPartner | PartialWithRequiredKeyOf<NewProjectPartner>;

type ProjectPartnerFormDefaults = Pick<NewProjectPartner, 'id' | 'isActive'>;

type ProjectPartnerFormGroupContent = {
  id: FormControl<IProjectPartner['id'] | NewProjectPartner['id']>;
  odataEtag: FormControl<IProjectPartner['odataEtag']>;
  no: FormControl<IProjectPartner['no']>;
  jobNo: FormControl<IProjectPartner['jobNo']>;
  vendorCode: FormControl<IProjectPartner['vendorCode']>;
  vendorName: FormControl<IProjectPartner['vendorName']>;
  countryCode: FormControl<IProjectPartner['countryCode']>;
  countryName: FormControl<IProjectPartner['countryName']>;
  partnerAmount: FormControl<IProjectPartner['partnerAmount']>;
  isActive: FormControl<IProjectPartner['isActive']>;
  createdBy: FormControl<IProjectPartner['createdBy']>;
  lastModifiedBy: FormControl<IProjectPartner['lastModifiedBy']>;
  createdDate: FormControl<IProjectPartner['createdDate']>;
  lastModifiedDate: FormControl<IProjectPartner['lastModifiedDate']>;
  jobInfo: FormControl<IProjectPartner['jobInfo']>;
};

export type ProjectPartnerFormGroup = FormGroup<ProjectPartnerFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProjectPartnerFormService {
  createProjectPartnerFormGroup(projectPartner: ProjectPartnerFormGroupInput = { id: null }): ProjectPartnerFormGroup {
    const projectPartnerRawValue = {
      ...this.getFormDefaults(),
      ...projectPartner,
    };
    return new FormGroup<ProjectPartnerFormGroupContent>({
      id: new FormControl(
        { value: projectPartnerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      odataEtag: new FormControl(projectPartnerRawValue.odataEtag),
      no: new FormControl(projectPartnerRawValue.no),
      jobNo: new FormControl(projectPartnerRawValue.jobNo),
      vendorCode: new FormControl(projectPartnerRawValue.vendorCode),
      vendorName: new FormControl(projectPartnerRawValue.vendorName),
      countryCode: new FormControl(projectPartnerRawValue.countryCode),
      countryName: new FormControl(projectPartnerRawValue.countryName),
      partnerAmount: new FormControl(projectPartnerRawValue.partnerAmount),
      isActive: new FormControl(projectPartnerRawValue.isActive),
      createdBy: new FormControl(projectPartnerRawValue.createdBy),
      lastModifiedBy: new FormControl(projectPartnerRawValue.lastModifiedBy),
      createdDate: new FormControl(projectPartnerRawValue.createdDate),
      lastModifiedDate: new FormControl(projectPartnerRawValue.lastModifiedDate),
      jobInfo: new FormControl(projectPartnerRawValue.jobInfo),
    });
  }

  getProjectPartner(form: ProjectPartnerFormGroup): IProjectPartner | NewProjectPartner {
    return form.getRawValue() as IProjectPartner | NewProjectPartner;
  }

  resetForm(form: ProjectPartnerFormGroup, projectPartner: ProjectPartnerFormGroupInput): void {
    const projectPartnerRawValue = { ...this.getFormDefaults(), ...projectPartner };
    form.reset(
      {
        ...projectPartnerRawValue,
        id: { value: projectPartnerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProjectPartnerFormDefaults {
    return {
      id: null,
      isActive: false,
    };
  }
}

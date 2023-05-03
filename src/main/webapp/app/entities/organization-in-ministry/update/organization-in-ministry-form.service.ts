import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOrganizationInMinistry, NewOrganizationInMinistry } from '../organization-in-ministry.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrganizationInMinistry for edit and NewOrganizationInMinistryFormGroupInput for create.
 */
type OrganizationInMinistryFormGroupInput = IOrganizationInMinistry | PartialWithRequiredKeyOf<NewOrganizationInMinistry>;

type OrganizationInMinistryFormDefaults = Pick<NewOrganizationInMinistry, 'id'>;

type OrganizationInMinistryFormGroupContent = {
  id: FormControl<IOrganizationInMinistry['id'] | NewOrganizationInMinistry['id']>;
  status: FormControl<IOrganizationInMinistry['status']>;
  ministry: FormControl<IOrganizationInMinistry['ministry']>;
  organization: FormControl<IOrganizationInMinistry['organization']>;
};

export type OrganizationInMinistryFormGroup = FormGroup<OrganizationInMinistryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrganizationInMinistryFormService {
  createOrganizationInMinistryFormGroup(
    organizationInMinistry: OrganizationInMinistryFormGroupInput = { id: null }
  ): OrganizationInMinistryFormGroup {
    const organizationInMinistryRawValue = {
      ...this.getFormDefaults(),
      ...organizationInMinistry,
    };
    return new FormGroup<OrganizationInMinistryFormGroupContent>({
      id: new FormControl(
        { value: organizationInMinistryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      status: new FormControl(organizationInMinistryRawValue.status),
      ministry: new FormControl(organizationInMinistryRawValue.ministry),
      organization: new FormControl(organizationInMinistryRawValue.organization),
    });
  }

  getOrganizationInMinistry(form: OrganizationInMinistryFormGroup): IOrganizationInMinistry | NewOrganizationInMinistry {
    return form.getRawValue() as IOrganizationInMinistry | NewOrganizationInMinistry;
  }

  resetForm(form: OrganizationInMinistryFormGroup, organizationInMinistry: OrganizationInMinistryFormGroupInput): void {
    const organizationInMinistryRawValue = { ...this.getFormDefaults(), ...organizationInMinistry };
    form.reset(
      {
        ...organizationInMinistryRawValue,
        id: { value: organizationInMinistryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OrganizationInMinistryFormDefaults {
    return {
      id: null,
    };
  }
}

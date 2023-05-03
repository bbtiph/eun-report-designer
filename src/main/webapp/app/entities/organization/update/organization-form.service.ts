import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOrganization, NewOrganization } from '../organization.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrganization for edit and NewOrganizationFormGroupInput for create.
 */
type OrganizationFormGroupInput = IOrganization | PartialWithRequiredKeyOf<NewOrganization>;

type OrganizationFormDefaults = Pick<NewOrganization, 'id' | 'hardwareUsed' | 'ictUsed'>;

type OrganizationFormGroupContent = {
  id: FormControl<IOrganization['id'] | NewOrganization['id']>;
  eunDbId: FormControl<IOrganization['eunDbId']>;
  status: FormControl<IOrganization['status']>;
  officialName: FormControl<IOrganization['officialName']>;
  description: FormControl<IOrganization['description']>;
  type: FormControl<IOrganization['type']>;
  address: FormControl<IOrganization['address']>;
  latitude: FormControl<IOrganization['latitude']>;
  longitude: FormControl<IOrganization['longitude']>;
  maxAge: FormControl<IOrganization['maxAge']>;
  minAge: FormControl<IOrganization['minAge']>;
  area: FormControl<IOrganization['area']>;
  specialization: FormControl<IOrganization['specialization']>;
  numberOfStudents: FormControl<IOrganization['numberOfStudents']>;
  hardwareUsed: FormControl<IOrganization['hardwareUsed']>;
  ictUsed: FormControl<IOrganization['ictUsed']>;
  website: FormControl<IOrganization['website']>;
  image: FormControl<IOrganization['image']>;
  imageContentType: FormControl<IOrganization['imageContentType']>;
  telephone: FormControl<IOrganization['telephone']>;
  fax: FormControl<IOrganization['fax']>;
  email: FormControl<IOrganization['email']>;
  organisationNumber: FormControl<IOrganization['organisationNumber']>;
  country: FormControl<IOrganization['country']>;
};

export type OrganizationFormGroup = FormGroup<OrganizationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrganizationFormService {
  createOrganizationFormGroup(organization: OrganizationFormGroupInput = { id: null }): OrganizationFormGroup {
    const organizationRawValue = {
      ...this.getFormDefaults(),
      ...organization,
    };
    return new FormGroup<OrganizationFormGroupContent>({
      id: new FormControl(
        { value: organizationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      eunDbId: new FormControl(organizationRawValue.eunDbId),
      status: new FormControl(organizationRawValue.status, {
        validators: [Validators.required],
      }),
      officialName: new FormControl(organizationRawValue.officialName, {
        validators: [Validators.required],
      }),
      description: new FormControl(organizationRawValue.description),
      type: new FormControl(organizationRawValue.type),
      address: new FormControl(organizationRawValue.address),
      latitude: new FormControl(organizationRawValue.latitude),
      longitude: new FormControl(organizationRawValue.longitude),
      maxAge: new FormControl(organizationRawValue.maxAge),
      minAge: new FormControl(organizationRawValue.minAge),
      area: new FormControl(organizationRawValue.area),
      specialization: new FormControl(organizationRawValue.specialization),
      numberOfStudents: new FormControl(organizationRawValue.numberOfStudents),
      hardwareUsed: new FormControl(organizationRawValue.hardwareUsed),
      ictUsed: new FormControl(organizationRawValue.ictUsed),
      website: new FormControl(organizationRawValue.website),
      image: new FormControl(organizationRawValue.image),
      imageContentType: new FormControl(organizationRawValue.imageContentType),
      telephone: new FormControl(organizationRawValue.telephone),
      fax: new FormControl(organizationRawValue.fax),
      email: new FormControl(organizationRawValue.email),
      organisationNumber: new FormControl(organizationRawValue.organisationNumber),
      country: new FormControl(organizationRawValue.country),
    });
  }

  getOrganization(form: OrganizationFormGroup): IOrganization | NewOrganization {
    return form.getRawValue() as IOrganization | NewOrganization;
  }

  resetForm(form: OrganizationFormGroup, organization: OrganizationFormGroupInput): void {
    const organizationRawValue = { ...this.getFormDefaults(), ...organization };
    form.reset(
      {
        ...organizationRawValue,
        id: { value: organizationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OrganizationFormDefaults {
    return {
      id: null,
      hardwareUsed: false,
      ictUsed: false,
    };
  }
}

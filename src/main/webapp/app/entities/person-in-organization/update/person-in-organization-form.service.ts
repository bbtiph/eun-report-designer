import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPersonInOrganization, NewPersonInOrganization } from '../person-in-organization.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPersonInOrganization for edit and NewPersonInOrganizationFormGroupInput for create.
 */
type PersonInOrganizationFormGroupInput = IPersonInOrganization | PartialWithRequiredKeyOf<NewPersonInOrganization>;

type PersonInOrganizationFormDefaults = Pick<NewPersonInOrganization, 'id'>;

type PersonInOrganizationFormGroupContent = {
  id: FormControl<IPersonInOrganization['id'] | NewPersonInOrganization['id']>;
  roleInOrganization: FormControl<IPersonInOrganization['roleInOrganization']>;
};

export type PersonInOrganizationFormGroup = FormGroup<PersonInOrganizationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PersonInOrganizationFormService {
  createPersonInOrganizationFormGroup(
    personInOrganization: PersonInOrganizationFormGroupInput = { id: null }
  ): PersonInOrganizationFormGroup {
    const personInOrganizationRawValue = {
      ...this.getFormDefaults(),
      ...personInOrganization,
    };
    return new FormGroup<PersonInOrganizationFormGroupContent>({
      id: new FormControl(
        { value: personInOrganizationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      roleInOrganization: new FormControl(personInOrganizationRawValue.roleInOrganization),
    });
  }

  getPersonInOrganization(form: PersonInOrganizationFormGroup): IPersonInOrganization | NewPersonInOrganization {
    return form.getRawValue() as IPersonInOrganization | NewPersonInOrganization;
  }

  resetForm(form: PersonInOrganizationFormGroup, personInOrganization: PersonInOrganizationFormGroupInput): void {
    const personInOrganizationRawValue = { ...this.getFormDefaults(), ...personInOrganization };
    form.reset(
      {
        ...personInOrganizationRawValue,
        id: { value: personInOrganizationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PersonInOrganizationFormDefaults {
    return {
      id: null,
    };
  }
}

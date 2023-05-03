import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPerson, NewPerson } from '../person.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPerson for edit and NewPersonFormGroupInput for create.
 */
type PersonFormGroupInput = IPerson | PartialWithRequiredKeyOf<NewPerson>;

type PersonFormDefaults = Pick<NewPerson, 'id'>;

type PersonFormGroupContent = {
  id: FormControl<IPerson['id'] | NewPerson['id']>;
  eunDbId: FormControl<IPerson['eunDbId']>;
  firstname: FormControl<IPerson['firstname']>;
  lastname: FormControl<IPerson['lastname']>;
  salutation: FormControl<IPerson['salutation']>;
  mainContractEmail: FormControl<IPerson['mainContractEmail']>;
  extraContractEmail: FormControl<IPerson['extraContractEmail']>;
  languageMotherTongue: FormControl<IPerson['languageMotherTongue']>;
  languageOther: FormControl<IPerson['languageOther']>;
  description: FormControl<IPerson['description']>;
  website: FormControl<IPerson['website']>;
  mobile: FormControl<IPerson['mobile']>;
  phone: FormControl<IPerson['phone']>;
  socialNetworkContacts: FormControl<IPerson['socialNetworkContacts']>;
  status: FormControl<IPerson['status']>;
  gdprStatus: FormControl<IPerson['gdprStatus']>;
  lastLoginDate: FormControl<IPerson['lastLoginDate']>;
  country: FormControl<IPerson['country']>;
};

export type PersonFormGroup = FormGroup<PersonFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PersonFormService {
  createPersonFormGroup(person: PersonFormGroupInput = { id: null }): PersonFormGroup {
    const personRawValue = {
      ...this.getFormDefaults(),
      ...person,
    };
    return new FormGroup<PersonFormGroupContent>({
      id: new FormControl(
        { value: personRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      eunDbId: new FormControl(personRawValue.eunDbId),
      firstname: new FormControl(personRawValue.firstname),
      lastname: new FormControl(personRawValue.lastname),
      salutation: new FormControl(personRawValue.salutation),
      mainContractEmail: new FormControl(personRawValue.mainContractEmail),
      extraContractEmail: new FormControl(personRawValue.extraContractEmail),
      languageMotherTongue: new FormControl(personRawValue.languageMotherTongue),
      languageOther: new FormControl(personRawValue.languageOther),
      description: new FormControl(personRawValue.description),
      website: new FormControl(personRawValue.website),
      mobile: new FormControl(personRawValue.mobile),
      phone: new FormControl(personRawValue.phone),
      socialNetworkContacts: new FormControl(personRawValue.socialNetworkContacts),
      status: new FormControl(personRawValue.status),
      gdprStatus: new FormControl(personRawValue.gdprStatus),
      lastLoginDate: new FormControl(personRawValue.lastLoginDate),
      country: new FormControl(personRawValue.country),
    });
  }

  getPerson(form: PersonFormGroup): IPerson | NewPerson {
    return form.getRawValue() as IPerson | NewPerson;
  }

  resetForm(form: PersonFormGroup, person: PersonFormGroupInput): void {
    const personRawValue = { ...this.getFormDefaults(), ...person };
    form.reset(
      {
        ...personRawValue,
        id: { value: personRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PersonFormDefaults {
    return {
      id: null,
    };
  }
}

import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICountry, NewCountry } from '../country.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICountry for edit and NewCountryFormGroupInput for create.
 */
type CountryFormGroupInput = ICountry | PartialWithRequiredKeyOf<NewCountry>;

type CountryFormDefaults = Pick<NewCountry, 'id'>;

type CountryFormGroupContent = {
  id: FormControl<ICountry['id'] | NewCountry['id']>;
  countryName: FormControl<ICountry['countryName']>;
  ministry: FormControl<ICountry['ministry']>;
  operationalBodyMember: FormControl<ICountry['operationalBodyMember']>;
  organization: FormControl<ICountry['organization']>;
  person: FormControl<ICountry['person']>;
};

export type CountryFormGroup = FormGroup<CountryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CountryFormService {
  createCountryFormGroup(country: CountryFormGroupInput = { id: null }): CountryFormGroup {
    const countryRawValue = {
      ...this.getFormDefaults(),
      ...country,
    };
    return new FormGroup<CountryFormGroupContent>({
      id: new FormControl(
        { value: countryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      countryName: new FormControl(countryRawValue.countryName),
      ministry: new FormControl(countryRawValue.ministry),
      operationalBodyMember: new FormControl(countryRawValue.operationalBodyMember),
      organization: new FormControl(countryRawValue.organization),
      person: new FormControl(countryRawValue.person),
    });
  }

  getCountry(form: CountryFormGroup): ICountry | NewCountry {
    return form.getRawValue() as ICountry | NewCountry;
  }

  resetForm(form: CountryFormGroup, country: CountryFormGroupInput): void {
    const countryRawValue = { ...this.getFormDefaults(), ...country };
    form.reset(
      {
        ...countryRawValue,
        id: { value: countryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CountryFormDefaults {
    return {
      id: null,
    };
  }
}

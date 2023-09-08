import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICountries, NewCountries } from '../countries.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICountries for edit and NewCountriesFormGroupInput for create.
 */
type CountriesFormGroupInput = ICountries | PartialWithRequiredKeyOf<NewCountries>;

type CountriesFormDefaults = Pick<NewCountries, 'id' | 'reportBlockIds'>;

type CountriesFormGroupContent = {
  id: FormControl<ICountries['id'] | NewCountries['id']>;
  countryName: FormControl<ICountries['countryName']>;
  countryCode: FormControl<ICountries['countryCode']>;
  reportBlockIds: FormControl<ICountries['reportBlockIds']>;
};

export type CountriesFormGroup = FormGroup<CountriesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CountriesFormService {
  createCountriesFormGroup(countries: CountriesFormGroupInput = { id: null }): CountriesFormGroup {
    const countriesRawValue = {
      ...this.getFormDefaults(),
      ...countries,
    };
    return new FormGroup<CountriesFormGroupContent>({
      id: new FormControl(
        { value: countriesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      countryName: new FormControl(countriesRawValue.countryName, {
        validators: [Validators.required],
      }),
      countryCode: new FormControl(countriesRawValue.countryCode, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(2)],
      }),
      reportBlockIds: new FormControl(countriesRawValue.reportBlockIds ?? []),
    });
  }

  getCountries(form: CountriesFormGroup): ICountries | NewCountries {
    return form.getRawValue() as ICountries | NewCountries;
  }

  resetForm(form: CountriesFormGroup, countries: CountriesFormGroupInput): void {
    const countriesRawValue = { ...this.getFormDefaults(), ...countries };
    form.reset(
      {
        ...countriesRawValue,
        id: { value: countriesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CountriesFormDefaults {
    return {
      id: null,
      reportBlockIds: [],
    };
  }
}

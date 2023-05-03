import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFunding, NewFunding } from '../funding.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFunding for edit and NewFundingFormGroupInput for create.
 */
type FundingFormGroupInput = IFunding | PartialWithRequiredKeyOf<NewFunding>;

type FundingFormDefaults = Pick<NewFunding, 'id'>;

type FundingFormGroupContent = {
  id: FormControl<IFunding['id'] | NewFunding['id']>;
  name: FormControl<IFunding['name']>;
  type: FormControl<IFunding['type']>;
  parentId: FormControl<IFunding['parentId']>;
  description: FormControl<IFunding['description']>;
  project: FormControl<IFunding['project']>;
};

export type FundingFormGroup = FormGroup<FundingFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FundingFormService {
  createFundingFormGroup(funding: FundingFormGroupInput = { id: null }): FundingFormGroup {
    const fundingRawValue = {
      ...this.getFormDefaults(),
      ...funding,
    };
    return new FormGroup<FundingFormGroupContent>({
      id: new FormControl(
        { value: fundingRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(fundingRawValue.name),
      type: new FormControl(fundingRawValue.type),
      parentId: new FormControl(fundingRawValue.parentId),
      description: new FormControl(fundingRawValue.description),
      project: new FormControl(fundingRawValue.project),
    });
  }

  getFunding(form: FundingFormGroup): IFunding | NewFunding {
    return form.getRawValue() as IFunding | NewFunding;
  }

  resetForm(form: FundingFormGroup, funding: FundingFormGroupInput): void {
    const fundingRawValue = { ...this.getFormDefaults(), ...funding };
    form.reset(
      {
        ...fundingRawValue,
        id: { value: fundingRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FundingFormDefaults {
    return {
      id: null,
    };
  }
}

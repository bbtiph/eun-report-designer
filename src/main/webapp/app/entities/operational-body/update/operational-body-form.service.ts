import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOperationalBody, NewOperationalBody } from '../operational-body.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOperationalBody for edit and NewOperationalBodyFormGroupInput for create.
 */
type OperationalBodyFormGroupInput = IOperationalBody | PartialWithRequiredKeyOf<NewOperationalBody>;

type OperationalBodyFormDefaults = Pick<NewOperationalBody, 'id'>;

type OperationalBodyFormGroupContent = {
  id: FormControl<IOperationalBody['id'] | NewOperationalBody['id']>;
  name: FormControl<IOperationalBody['name']>;
  acronym: FormControl<IOperationalBody['acronym']>;
  description: FormControl<IOperationalBody['description']>;
  type: FormControl<IOperationalBody['type']>;
  status: FormControl<IOperationalBody['status']>;
};

export type OperationalBodyFormGroup = FormGroup<OperationalBodyFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OperationalBodyFormService {
  createOperationalBodyFormGroup(operationalBody: OperationalBodyFormGroupInput = { id: null }): OperationalBodyFormGroup {
    const operationalBodyRawValue = {
      ...this.getFormDefaults(),
      ...operationalBody,
    };
    return new FormGroup<OperationalBodyFormGroupContent>({
      id: new FormControl(
        { value: operationalBodyRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(operationalBodyRawValue.name, {
        validators: [Validators.required],
      }),
      acronym: new FormControl(operationalBodyRawValue.acronym),
      description: new FormControl(operationalBodyRawValue.description),
      type: new FormControl(operationalBodyRawValue.type),
      status: new FormControl(operationalBodyRawValue.status),
    });
  }

  getOperationalBody(form: OperationalBodyFormGroup): IOperationalBody | NewOperationalBody {
    return form.getRawValue() as IOperationalBody | NewOperationalBody;
  }

  resetForm(form: OperationalBodyFormGroup, operationalBody: OperationalBodyFormGroupInput): void {
    const operationalBodyRawValue = { ...this.getFormDefaults(), ...operationalBody };
    form.reset(
      {
        ...operationalBodyRawValue,
        id: { value: operationalBodyRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OperationalBodyFormDefaults {
    return {
      id: null,
    };
  }
}

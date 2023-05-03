import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOperationalBodyMember, NewOperationalBodyMember } from '../operational-body-member.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOperationalBodyMember for edit and NewOperationalBodyMemberFormGroupInput for create.
 */
type OperationalBodyMemberFormGroupInput = IOperationalBodyMember | PartialWithRequiredKeyOf<NewOperationalBodyMember>;

type OperationalBodyMemberFormDefaults = Pick<NewOperationalBodyMember, 'id'>;

type OperationalBodyMemberFormGroupContent = {
  id: FormControl<IOperationalBodyMember['id'] | NewOperationalBodyMember['id']>;
  personId: FormControl<IOperationalBodyMember['personId']>;
  position: FormControl<IOperationalBodyMember['position']>;
  startDate: FormControl<IOperationalBodyMember['startDate']>;
  endDate: FormControl<IOperationalBodyMember['endDate']>;
  department: FormControl<IOperationalBodyMember['department']>;
  eunContactFirstname: FormControl<IOperationalBodyMember['eunContactFirstname']>;
  eunContactLastname: FormControl<IOperationalBodyMember['eunContactLastname']>;
  cooperationField: FormControl<IOperationalBodyMember['cooperationField']>;
  status: FormControl<IOperationalBodyMember['status']>;
};

export type OperationalBodyMemberFormGroup = FormGroup<OperationalBodyMemberFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OperationalBodyMemberFormService {
  createOperationalBodyMemberFormGroup(
    operationalBodyMember: OperationalBodyMemberFormGroupInput = { id: null }
  ): OperationalBodyMemberFormGroup {
    const operationalBodyMemberRawValue = {
      ...this.getFormDefaults(),
      ...operationalBodyMember,
    };
    return new FormGroup<OperationalBodyMemberFormGroupContent>({
      id: new FormControl(
        { value: operationalBodyMemberRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      personId: new FormControl(operationalBodyMemberRawValue.personId),
      position: new FormControl(operationalBodyMemberRawValue.position),
      startDate: new FormControl(operationalBodyMemberRawValue.startDate),
      endDate: new FormControl(operationalBodyMemberRawValue.endDate),
      department: new FormControl(operationalBodyMemberRawValue.department),
      eunContactFirstname: new FormControl(operationalBodyMemberRawValue.eunContactFirstname),
      eunContactLastname: new FormControl(operationalBodyMemberRawValue.eunContactLastname),
      cooperationField: new FormControl(operationalBodyMemberRawValue.cooperationField),
      status: new FormControl(operationalBodyMemberRawValue.status),
    });
  }

  getOperationalBodyMember(form: OperationalBodyMemberFormGroup): IOperationalBodyMember | NewOperationalBodyMember {
    return form.getRawValue() as IOperationalBodyMember | NewOperationalBodyMember;
  }

  resetForm(form: OperationalBodyMemberFormGroup, operationalBodyMember: OperationalBodyMemberFormGroupInput): void {
    const operationalBodyMemberRawValue = { ...this.getFormDefaults(), ...operationalBodyMember };
    form.reset(
      {
        ...operationalBodyMemberRawValue,
        id: { value: operationalBodyMemberRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OperationalBodyMemberFormDefaults {
    return {
      id: null,
    };
  }
}

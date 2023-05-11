import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPrivilege, NewPrivilege } from '../privilege.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPrivilege for edit and NewPrivilegeFormGroupInput for create.
 */
type PrivilegeFormGroupInput = IPrivilege | PartialWithRequiredKeyOf<NewPrivilege>;

type PrivilegeFormDefaults = Pick<NewPrivilege, 'id' | 'roles'>;

type PrivilegeFormGroupContent = {
  id: FormControl<IPrivilege['id'] | NewPrivilege['id']>;
  name: FormControl<IPrivilege['name']>;
  roles: FormControl<IPrivilege['roles']>;
};

export type PrivilegeFormGroup = FormGroup<PrivilegeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PrivilegeFormService {
  createPrivilegeFormGroup(privilege: PrivilegeFormGroupInput = { id: null }): PrivilegeFormGroup {
    const privilegeRawValue = {
      ...this.getFormDefaults(),
      ...privilege,
    };
    return new FormGroup<PrivilegeFormGroupContent>({
      id: new FormControl(
        { value: privilegeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(privilegeRawValue.name),
      roles: new FormControl(privilegeRawValue.roles ?? []),
    });
  }

  getPrivilege(form: PrivilegeFormGroup): IPrivilege | NewPrivilege {
    return form.getRawValue() as IPrivilege | NewPrivilege;
  }

  resetForm(form: PrivilegeFormGroup, privilege: PrivilegeFormGroupInput): void {
    const privilegeRawValue = { ...this.getFormDefaults(), ...privilege };
    form.reset(
      {
        ...privilegeRawValue,
        id: { value: privilegeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PrivilegeFormDefaults {
    return {
      id: null,
      roles: [],
    };
  }
}

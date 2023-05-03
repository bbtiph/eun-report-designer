import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEunTeamMember, NewEunTeamMember } from '../eun-team-member.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEunTeamMember for edit and NewEunTeamMemberFormGroupInput for create.
 */
type EunTeamMemberFormGroupInput = IEunTeamMember | PartialWithRequiredKeyOf<NewEunTeamMember>;

type EunTeamMemberFormDefaults = Pick<NewEunTeamMember, 'id'>;

type EunTeamMemberFormGroupContent = {
  id: FormControl<IEunTeamMember['id'] | NewEunTeamMember['id']>;
  role: FormControl<IEunTeamMember['role']>;
  status: FormControl<IEunTeamMember['status']>;
  team: FormControl<IEunTeamMember['team']>;
  person: FormControl<IEunTeamMember['person']>;
};

export type EunTeamMemberFormGroup = FormGroup<EunTeamMemberFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EunTeamMemberFormService {
  createEunTeamMemberFormGroup(eunTeamMember: EunTeamMemberFormGroupInput = { id: null }): EunTeamMemberFormGroup {
    const eunTeamMemberRawValue = {
      ...this.getFormDefaults(),
      ...eunTeamMember,
    };
    return new FormGroup<EunTeamMemberFormGroupContent>({
      id: new FormControl(
        { value: eunTeamMemberRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      role: new FormControl(eunTeamMemberRawValue.role),
      status: new FormControl(eunTeamMemberRawValue.status),
      team: new FormControl(eunTeamMemberRawValue.team),
      person: new FormControl(eunTeamMemberRawValue.person),
    });
  }

  getEunTeamMember(form: EunTeamMemberFormGroup): IEunTeamMember | NewEunTeamMember {
    return form.getRawValue() as IEunTeamMember | NewEunTeamMember;
  }

  resetForm(form: EunTeamMemberFormGroup, eunTeamMember: EunTeamMemberFormGroupInput): void {
    const eunTeamMemberRawValue = { ...this.getFormDefaults(), ...eunTeamMember };
    form.reset(
      {
        ...eunTeamMemberRawValue,
        id: { value: eunTeamMemberRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EunTeamMemberFormDefaults {
    return {
      id: null,
    };
  }
}

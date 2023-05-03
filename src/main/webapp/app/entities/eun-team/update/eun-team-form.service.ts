import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEunTeam, NewEunTeam } from '../eun-team.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEunTeam for edit and NewEunTeamFormGroupInput for create.
 */
type EunTeamFormGroupInput = IEunTeam | PartialWithRequiredKeyOf<NewEunTeam>;

type EunTeamFormDefaults = Pick<NewEunTeam, 'id'>;

type EunTeamFormGroupContent = {
  id: FormControl<IEunTeam['id'] | NewEunTeam['id']>;
  name: FormControl<IEunTeam['name']>;
  description: FormControl<IEunTeam['description']>;
};

export type EunTeamFormGroup = FormGroup<EunTeamFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EunTeamFormService {
  createEunTeamFormGroup(eunTeam: EunTeamFormGroupInput = { id: null }): EunTeamFormGroup {
    const eunTeamRawValue = {
      ...this.getFormDefaults(),
      ...eunTeam,
    };
    return new FormGroup<EunTeamFormGroupContent>({
      id: new FormControl(
        { value: eunTeamRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(eunTeamRawValue.name),
      description: new FormControl(eunTeamRawValue.description),
    });
  }

  getEunTeam(form: EunTeamFormGroup): IEunTeam | NewEunTeam {
    return form.getRawValue() as IEunTeam | NewEunTeam;
  }

  resetForm(form: EunTeamFormGroup, eunTeam: EunTeamFormGroupInput): void {
    const eunTeamRawValue = { ...this.getFormDefaults(), ...eunTeam };
    form.reset(
      {
        ...eunTeamRawValue,
        id: { value: eunTeamRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EunTeamFormDefaults {
    return {
      id: null,
    };
  }
}

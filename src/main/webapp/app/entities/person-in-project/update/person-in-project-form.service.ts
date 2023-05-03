import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPersonInProject, NewPersonInProject } from '../person-in-project.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPersonInProject for edit and NewPersonInProjectFormGroupInput for create.
 */
type PersonInProjectFormGroupInput = IPersonInProject | PartialWithRequiredKeyOf<NewPersonInProject>;

type PersonInProjectFormDefaults = Pick<NewPersonInProject, 'id'>;

type PersonInProjectFormGroupContent = {
  id: FormControl<IPersonInProject['id'] | NewPersonInProject['id']>;
  roleInProject: FormControl<IPersonInProject['roleInProject']>;
};

export type PersonInProjectFormGroup = FormGroup<PersonInProjectFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PersonInProjectFormService {
  createPersonInProjectFormGroup(personInProject: PersonInProjectFormGroupInput = { id: null }): PersonInProjectFormGroup {
    const personInProjectRawValue = {
      ...this.getFormDefaults(),
      ...personInProject,
    };
    return new FormGroup<PersonInProjectFormGroupContent>({
      id: new FormControl(
        { value: personInProjectRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      roleInProject: new FormControl(personInProjectRawValue.roleInProject),
    });
  }

  getPersonInProject(form: PersonInProjectFormGroup): IPersonInProject | NewPersonInProject {
    return form.getRawValue() as IPersonInProject | NewPersonInProject;
  }

  resetForm(form: PersonInProjectFormGroup, personInProject: PersonInProjectFormGroupInput): void {
    const personInProjectRawValue = { ...this.getFormDefaults(), ...personInProject };
    form.reset(
      {
        ...personInProjectRawValue,
        id: { value: personInProjectRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PersonInProjectFormDefaults {
    return {
      id: null,
    };
  }
}

import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEventInOrganization, NewEventInOrganization } from '../event-in-organization.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEventInOrganization for edit and NewEventInOrganizationFormGroupInput for create.
 */
type EventInOrganizationFormGroupInput = IEventInOrganization | PartialWithRequiredKeyOf<NewEventInOrganization>;

type EventInOrganizationFormDefaults = Pick<NewEventInOrganization, 'id'>;

type EventInOrganizationFormGroupContent = {
  id: FormControl<IEventInOrganization['id'] | NewEventInOrganization['id']>;
  event: FormControl<IEventInOrganization['event']>;
  organization: FormControl<IEventInOrganization['organization']>;
};

export type EventInOrganizationFormGroup = FormGroup<EventInOrganizationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventInOrganizationFormService {
  createEventInOrganizationFormGroup(eventInOrganization: EventInOrganizationFormGroupInput = { id: null }): EventInOrganizationFormGroup {
    const eventInOrganizationRawValue = {
      ...this.getFormDefaults(),
      ...eventInOrganization,
    };
    return new FormGroup<EventInOrganizationFormGroupContent>({
      id: new FormControl(
        { value: eventInOrganizationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      event: new FormControl(eventInOrganizationRawValue.event),
      organization: new FormControl(eventInOrganizationRawValue.organization),
    });
  }

  getEventInOrganization(form: EventInOrganizationFormGroup): IEventInOrganization | NewEventInOrganization {
    return form.getRawValue() as IEventInOrganization | NewEventInOrganization;
  }

  resetForm(form: EventInOrganizationFormGroup, eventInOrganization: EventInOrganizationFormGroupInput): void {
    const eventInOrganizationRawValue = { ...this.getFormDefaults(), ...eventInOrganization };
    form.reset(
      {
        ...eventInOrganizationRawValue,
        id: { value: eventInOrganizationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventInOrganizationFormDefaults {
    return {
      id: null,
    };
  }
}

import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEvent, NewEvent } from '../event.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEvent for edit and NewEventFormGroupInput for create.
 */
type EventFormGroupInput = IEvent | PartialWithRequiredKeyOf<NewEvent>;

type EventFormDefaults = Pick<NewEvent, 'id'>;

type EventFormGroupContent = {
  id: FormControl<IEvent['id'] | NewEvent['id']>;
  type: FormControl<IEvent['type']>;
  location: FormControl<IEvent['location']>;
  title: FormControl<IEvent['title']>;
  description: FormControl<IEvent['description']>;
  startDate: FormControl<IEvent['startDate']>;
  endDate: FormControl<IEvent['endDate']>;
  url: FormControl<IEvent['url']>;
  eunContact: FormControl<IEvent['eunContact']>;
  courseModules: FormControl<IEvent['courseModules']>;
  courseDuration: FormControl<IEvent['courseDuration']>;
  courseType: FormControl<IEvent['courseType']>;
  modules: FormControl<IEvent['modules']>;
  status: FormControl<IEvent['status']>;
  engagementRate: FormControl<IEvent['engagementRate']>;
  completionRate: FormControl<IEvent['completionRate']>;
  name: FormControl<IEvent['name']>;
  eventInOrganization: FormControl<IEvent['eventInOrganization']>;
  eventParticipant: FormControl<IEvent['eventParticipant']>;
};

export type EventFormGroup = FormGroup<EventFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventFormService {
  createEventFormGroup(event: EventFormGroupInput = { id: null }): EventFormGroup {
    const eventRawValue = {
      ...this.getFormDefaults(),
      ...event,
    };
    return new FormGroup<EventFormGroupContent>({
      id: new FormControl(
        { value: eventRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(eventRawValue.type),
      location: new FormControl(eventRawValue.location),
      title: new FormControl(eventRawValue.title),
      description: new FormControl(eventRawValue.description),
      startDate: new FormControl(eventRawValue.startDate),
      endDate: new FormControl(eventRawValue.endDate),
      url: new FormControl(eventRawValue.url),
      eunContact: new FormControl(eventRawValue.eunContact),
      courseModules: new FormControl(eventRawValue.courseModules),
      courseDuration: new FormControl(eventRawValue.courseDuration),
      courseType: new FormControl(eventRawValue.courseType),
      modules: new FormControl(eventRawValue.modules),
      status: new FormControl(eventRawValue.status),
      engagementRate: new FormControl(eventRawValue.engagementRate),
      completionRate: new FormControl(eventRawValue.completionRate),
      name: new FormControl(eventRawValue.name),
      eventInOrganization: new FormControl(eventRawValue.eventInOrganization),
      eventParticipant: new FormControl(eventRawValue.eventParticipant),
    });
  }

  getEvent(form: EventFormGroup): IEvent | NewEvent {
    return form.getRawValue() as IEvent | NewEvent;
  }

  resetForm(form: EventFormGroup, event: EventFormGroupInput): void {
    const eventRawValue = { ...this.getFormDefaults(), ...event };
    form.reset(
      {
        ...eventRawValue,
        id: { value: eventRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventFormDefaults {
    return {
      id: null,
    };
  }
}

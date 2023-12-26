import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEventReferences, NewEventReferences } from '../event-references.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEventReferences for edit and NewEventReferencesFormGroupInput for create.
 */
type EventReferencesFormGroupInput = IEventReferences | PartialWithRequiredKeyOf<NewEventReferences>;

type EventReferencesFormDefaults = Pick<NewEventReferences, 'id' | 'isActive' | 'countries'>;

type EventReferencesFormGroupContent = {
  id: FormControl<IEventReferences['id'] | NewEventReferences['id']>;
  name: FormControl<IEventReferences['name']>;
  type: FormControl<IEventReferences['type']>;
  isActive: FormControl<IEventReferences['isActive']>;
  countries: FormControl<IEventReferences['countries']>;
};

export type EventReferencesFormGroup = FormGroup<EventReferencesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventReferencesFormService {
  createEventReferencesFormGroup(eventReferences: EventReferencesFormGroupInput = { id: null }): EventReferencesFormGroup {
    const eventReferencesRawValue = {
      ...this.getFormDefaults(),
      ...eventReferences,
    };
    return new FormGroup<EventReferencesFormGroupContent>({
      id: new FormControl(
        { value: eventReferencesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(eventReferencesRawValue.name),
      type: new FormControl(eventReferencesRawValue.type),
      isActive: new FormControl(eventReferencesRawValue.isActive),
      countries: new FormControl(eventReferencesRawValue.countries ?? []),
    });
  }

  getEventReferences(form: EventReferencesFormGroup): IEventReferences | NewEventReferences {
    return form.getRawValue() as IEventReferences | NewEventReferences;
  }

  resetForm(form: EventReferencesFormGroup, eventReferences: EventReferencesFormGroupInput): void {
    const eventReferencesRawValue = { ...this.getFormDefaults(), ...eventReferences };
    form.reset(
      {
        ...eventReferencesRawValue,
        id: { value: eventReferencesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventReferencesFormDefaults {
    return {
      id: null,
      isActive: false,
      countries: [],
    };
  }
}

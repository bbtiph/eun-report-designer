import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEventParticipant, NewEventParticipant } from '../event-participant.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEventParticipant for edit and NewEventParticipantFormGroupInput for create.
 */
type EventParticipantFormGroupInput = IEventParticipant | PartialWithRequiredKeyOf<NewEventParticipant>;

type EventParticipantFormDefaults = Pick<NewEventParticipant, 'id'>;

type EventParticipantFormGroupContent = {
  id: FormControl<IEventParticipant['id'] | NewEventParticipant['id']>;
  type: FormControl<IEventParticipant['type']>;
  event: FormControl<IEventParticipant['event']>;
  person: FormControl<IEventParticipant['person']>;
};

export type EventParticipantFormGroup = FormGroup<EventParticipantFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventParticipantFormService {
  createEventParticipantFormGroup(eventParticipant: EventParticipantFormGroupInput = { id: null }): EventParticipantFormGroup {
    const eventParticipantRawValue = {
      ...this.getFormDefaults(),
      ...eventParticipant,
    };
    return new FormGroup<EventParticipantFormGroupContent>({
      id: new FormControl(
        { value: eventParticipantRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(eventParticipantRawValue.type),
      event: new FormControl(eventParticipantRawValue.event),
      person: new FormControl(eventParticipantRawValue.person),
    });
  }

  getEventParticipant(form: EventParticipantFormGroup): IEventParticipant | NewEventParticipant {
    return form.getRawValue() as IEventParticipant | NewEventParticipant;
  }

  resetForm(form: EventParticipantFormGroup, eventParticipant: EventParticipantFormGroupInput): void {
    const eventParticipantRawValue = { ...this.getFormDefaults(), ...eventParticipant };
    form.reset(
      {
        ...eventParticipantRawValue,
        id: { value: eventParticipantRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventParticipantFormDefaults {
    return {
      id: null,
    };
  }
}

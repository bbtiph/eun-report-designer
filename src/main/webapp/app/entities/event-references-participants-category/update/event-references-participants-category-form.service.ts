import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import {
  IEventReferencesParticipantsCategory,
  NewEventReferencesParticipantsCategory,
} from '../event-references-participants-category.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEventReferencesParticipantsCategory for edit and NewEventReferencesParticipantsCategoryFormGroupInput for create.
 */
type EventReferencesParticipantsCategoryFormGroupInput =
  | IEventReferencesParticipantsCategory
  | PartialWithRequiredKeyOf<NewEventReferencesParticipantsCategory>;

type EventReferencesParticipantsCategoryFormDefaults = Pick<NewEventReferencesParticipantsCategory, 'id'>;

type EventReferencesParticipantsCategoryFormGroupContent = {
  id: FormControl<IEventReferencesParticipantsCategory['id'] | NewEventReferencesParticipantsCategory['id']>;
  category: FormControl<IEventReferencesParticipantsCategory['category']>;
  participantsCount: FormControl<IEventReferencesParticipantsCategory['participantsCount']>;
  eventReference: FormControl<IEventReferencesParticipantsCategory['eventReference']>;
};

export type EventReferencesParticipantsCategoryFormGroup = FormGroup<EventReferencesParticipantsCategoryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventReferencesParticipantsCategoryFormService {
  createEventReferencesParticipantsCategoryFormGroup(
    eventReferencesParticipantsCategory: EventReferencesParticipantsCategoryFormGroupInput = { id: null }
  ): EventReferencesParticipantsCategoryFormGroup {
    const eventReferencesParticipantsCategoryRawValue = {
      ...this.getFormDefaults(),
      ...eventReferencesParticipantsCategory,
    };
    return new FormGroup<EventReferencesParticipantsCategoryFormGroupContent>({
      id: new FormControl(
        { value: eventReferencesParticipantsCategoryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      category: new FormControl(eventReferencesParticipantsCategoryRawValue.category),
      participantsCount: new FormControl(eventReferencesParticipantsCategoryRawValue.participantsCount),
      eventReference: new FormControl(eventReferencesParticipantsCategoryRawValue.eventReference),
    });
  }

  getEventReferencesParticipantsCategory(
    form: EventReferencesParticipantsCategoryFormGroup
  ): IEventReferencesParticipantsCategory | NewEventReferencesParticipantsCategory {
    return form.getRawValue() as IEventReferencesParticipantsCategory | NewEventReferencesParticipantsCategory;
  }

  resetForm(
    form: EventReferencesParticipantsCategoryFormGroup,
    eventReferencesParticipantsCategory: EventReferencesParticipantsCategoryFormGroupInput
  ): void {
    const eventReferencesParticipantsCategoryRawValue = { ...this.getFormDefaults(), ...eventReferencesParticipantsCategory };
    form.reset(
      {
        ...eventReferencesParticipantsCategoryRawValue,
        id: { value: eventReferencesParticipantsCategoryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventReferencesParticipantsCategoryFormDefaults {
    return {
      id: null,
    };
  }
}

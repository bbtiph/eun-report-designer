import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../event-references-participants-category.test-samples';

import { EventReferencesParticipantsCategoryFormService } from './event-references-participants-category-form.service';

describe('EventReferencesParticipantsCategory Form Service', () => {
  let service: EventReferencesParticipantsCategoryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventReferencesParticipantsCategoryFormService);
  });

  describe('Service methods', () => {
    describe('createEventReferencesParticipantsCategoryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEventReferencesParticipantsCategoryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            category: expect.any(Object),
            participantsCount: expect.any(Object),
            eventReference: expect.any(Object),
          })
        );
      });

      it('passing IEventReferencesParticipantsCategory should create a new form with FormGroup', () => {
        const formGroup = service.createEventReferencesParticipantsCategoryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            category: expect.any(Object),
            participantsCount: expect.any(Object),
            eventReference: expect.any(Object),
          })
        );
      });
    });

    describe('getEventReferencesParticipantsCategory', () => {
      it('should return NewEventReferencesParticipantsCategory for default EventReferencesParticipantsCategory initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEventReferencesParticipantsCategoryFormGroup(sampleWithNewData);

        const eventReferencesParticipantsCategory = service.getEventReferencesParticipantsCategory(formGroup) as any;

        expect(eventReferencesParticipantsCategory).toMatchObject(sampleWithNewData);
      });

      it('should return NewEventReferencesParticipantsCategory for empty EventReferencesParticipantsCategory initial value', () => {
        const formGroup = service.createEventReferencesParticipantsCategoryFormGroup();

        const eventReferencesParticipantsCategory = service.getEventReferencesParticipantsCategory(formGroup) as any;

        expect(eventReferencesParticipantsCategory).toMatchObject({});
      });

      it('should return IEventReferencesParticipantsCategory', () => {
        const formGroup = service.createEventReferencesParticipantsCategoryFormGroup(sampleWithRequiredData);

        const eventReferencesParticipantsCategory = service.getEventReferencesParticipantsCategory(formGroup) as any;

        expect(eventReferencesParticipantsCategory).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEventReferencesParticipantsCategory should not enable id FormControl', () => {
        const formGroup = service.createEventReferencesParticipantsCategoryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEventReferencesParticipantsCategory should disable id FormControl', () => {
        const formGroup = service.createEventReferencesParticipantsCategoryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

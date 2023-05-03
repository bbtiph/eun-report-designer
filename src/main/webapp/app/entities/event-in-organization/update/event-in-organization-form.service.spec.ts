import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../event-in-organization.test-samples';

import { EventInOrganizationFormService } from './event-in-organization-form.service';

describe('EventInOrganization Form Service', () => {
  let service: EventInOrganizationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventInOrganizationFormService);
  });

  describe('Service methods', () => {
    describe('createEventInOrganizationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEventInOrganizationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            event: expect.any(Object),
            organization: expect.any(Object),
          })
        );
      });

      it('passing IEventInOrganization should create a new form with FormGroup', () => {
        const formGroup = service.createEventInOrganizationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            event: expect.any(Object),
            organization: expect.any(Object),
          })
        );
      });
    });

    describe('getEventInOrganization', () => {
      it('should return NewEventInOrganization for default EventInOrganization initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEventInOrganizationFormGroup(sampleWithNewData);

        const eventInOrganization = service.getEventInOrganization(formGroup) as any;

        expect(eventInOrganization).toMatchObject(sampleWithNewData);
      });

      it('should return NewEventInOrganization for empty EventInOrganization initial value', () => {
        const formGroup = service.createEventInOrganizationFormGroup();

        const eventInOrganization = service.getEventInOrganization(formGroup) as any;

        expect(eventInOrganization).toMatchObject({});
      });

      it('should return IEventInOrganization', () => {
        const formGroup = service.createEventInOrganizationFormGroup(sampleWithRequiredData);

        const eventInOrganization = service.getEventInOrganization(formGroup) as any;

        expect(eventInOrganization).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEventInOrganization should not enable id FormControl', () => {
        const formGroup = service.createEventInOrganizationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEventInOrganization should disable id FormControl', () => {
        const formGroup = service.createEventInOrganizationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

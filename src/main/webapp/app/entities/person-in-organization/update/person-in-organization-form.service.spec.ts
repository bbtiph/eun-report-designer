import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../person-in-organization.test-samples';

import { PersonInOrganizationFormService } from './person-in-organization-form.service';

describe('PersonInOrganization Form Service', () => {
  let service: PersonInOrganizationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonInOrganizationFormService);
  });

  describe('Service methods', () => {
    describe('createPersonInOrganizationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPersonInOrganizationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            roleInOrganization: expect.any(Object),
          })
        );
      });

      it('passing IPersonInOrganization should create a new form with FormGroup', () => {
        const formGroup = service.createPersonInOrganizationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            roleInOrganization: expect.any(Object),
          })
        );
      });
    });

    describe('getPersonInOrganization', () => {
      it('should return NewPersonInOrganization for default PersonInOrganization initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPersonInOrganizationFormGroup(sampleWithNewData);

        const personInOrganization = service.getPersonInOrganization(formGroup) as any;

        expect(personInOrganization).toMatchObject(sampleWithNewData);
      });

      it('should return NewPersonInOrganization for empty PersonInOrganization initial value', () => {
        const formGroup = service.createPersonInOrganizationFormGroup();

        const personInOrganization = service.getPersonInOrganization(formGroup) as any;

        expect(personInOrganization).toMatchObject({});
      });

      it('should return IPersonInOrganization', () => {
        const formGroup = service.createPersonInOrganizationFormGroup(sampleWithRequiredData);

        const personInOrganization = service.getPersonInOrganization(formGroup) as any;

        expect(personInOrganization).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPersonInOrganization should not enable id FormControl', () => {
        const formGroup = service.createPersonInOrganizationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPersonInOrganization should disable id FormControl', () => {
        const formGroup = service.createPersonInOrganizationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

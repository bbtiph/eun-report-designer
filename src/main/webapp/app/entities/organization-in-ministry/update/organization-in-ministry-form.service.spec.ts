import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../organization-in-ministry.test-samples';

import { OrganizationInMinistryFormService } from './organization-in-ministry-form.service';

describe('OrganizationInMinistry Form Service', () => {
  let service: OrganizationInMinistryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganizationInMinistryFormService);
  });

  describe('Service methods', () => {
    describe('createOrganizationInMinistryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrganizationInMinistryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });

      it('passing IOrganizationInMinistry should create a new form with FormGroup', () => {
        const formGroup = service.createOrganizationInMinistryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });
    });

    describe('getOrganizationInMinistry', () => {
      it('should return NewOrganizationInMinistry for default OrganizationInMinistry initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOrganizationInMinistryFormGroup(sampleWithNewData);

        const organizationInMinistry = service.getOrganizationInMinistry(formGroup) as any;

        expect(organizationInMinistry).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrganizationInMinistry for empty OrganizationInMinistry initial value', () => {
        const formGroup = service.createOrganizationInMinistryFormGroup();

        const organizationInMinistry = service.getOrganizationInMinistry(formGroup) as any;

        expect(organizationInMinistry).toMatchObject({});
      });

      it('should return IOrganizationInMinistry', () => {
        const formGroup = service.createOrganizationInMinistryFormGroup(sampleWithRequiredData);

        const organizationInMinistry = service.getOrganizationInMinistry(formGroup) as any;

        expect(organizationInMinistry).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrganizationInMinistry should not enable id FormControl', () => {
        const formGroup = service.createOrganizationInMinistryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrganizationInMinistry should disable id FormControl', () => {
        const formGroup = service.createOrganizationInMinistryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

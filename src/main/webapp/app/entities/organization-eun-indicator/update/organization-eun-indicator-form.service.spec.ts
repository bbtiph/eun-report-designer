import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../organization-eun-indicator.test-samples';

import { OrganizationEunIndicatorFormService } from './organization-eun-indicator-form.service';

describe('OrganizationEunIndicator Form Service', () => {
  let service: OrganizationEunIndicatorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganizationEunIndicatorFormService);
  });

  describe('Service methods', () => {
    describe('createOrganizationEunIndicatorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrganizationEunIndicatorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nCount: expect.any(Object),
            countryId: expect.any(Object),
            projectId: expect.any(Object),
            projectUrl: expect.any(Object),
            countryName: expect.any(Object),
            projectName: expect.any(Object),
            reportsProjectId: expect.any(Object),
            createdBy: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });

      it('passing IOrganizationEunIndicator should create a new form with FormGroup', () => {
        const formGroup = service.createOrganizationEunIndicatorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nCount: expect.any(Object),
            countryId: expect.any(Object),
            projectId: expect.any(Object),
            projectUrl: expect.any(Object),
            countryName: expect.any(Object),
            projectName: expect.any(Object),
            reportsProjectId: expect.any(Object),
            createdBy: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });
    });

    describe('getOrganizationEunIndicator', () => {
      it('should return NewOrganizationEunIndicator for default OrganizationEunIndicator initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOrganizationEunIndicatorFormGroup(sampleWithNewData);

        const organizationEunIndicator = service.getOrganizationEunIndicator(formGroup) as any;

        expect(organizationEunIndicator).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrganizationEunIndicator for empty OrganizationEunIndicator initial value', () => {
        const formGroup = service.createOrganizationEunIndicatorFormGroup();

        const organizationEunIndicator = service.getOrganizationEunIndicator(formGroup) as any;

        expect(organizationEunIndicator).toMatchObject({});
      });

      it('should return IOrganizationEunIndicator', () => {
        const formGroup = service.createOrganizationEunIndicatorFormGroup(sampleWithRequiredData);

        const organizationEunIndicator = service.getOrganizationEunIndicator(formGroup) as any;

        expect(organizationEunIndicator).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrganizationEunIndicator should not enable id FormControl', () => {
        const formGroup = service.createOrganizationEunIndicatorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrganizationEunIndicator should disable id FormControl', () => {
        const formGroup = service.createOrganizationEunIndicatorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

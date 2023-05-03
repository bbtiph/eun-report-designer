import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../organization-in-project.test-samples';

import { OrganizationInProjectFormService } from './organization-in-project-form.service';

describe('OrganizationInProject Form Service', () => {
  let service: OrganizationInProjectFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganizationInProjectFormService);
  });

  describe('Service methods', () => {
    describe('createOrganizationInProjectFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrganizationInProjectFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            status: expect.any(Object),
            joinDate: expect.any(Object),
            fundingForOrganization: expect.any(Object),
            participationToMatchingFunding: expect.any(Object),
            schoolRegistrationPossible: expect.any(Object),
            teacherParticipationPossible: expect.any(Object),
            ambassadorsPilotTeachersLeadingTeachersIdentified: expect.any(Object),
            usersCanRegisterToPortal: expect.any(Object),
          })
        );
      });

      it('passing IOrganizationInProject should create a new form with FormGroup', () => {
        const formGroup = service.createOrganizationInProjectFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            status: expect.any(Object),
            joinDate: expect.any(Object),
            fundingForOrganization: expect.any(Object),
            participationToMatchingFunding: expect.any(Object),
            schoolRegistrationPossible: expect.any(Object),
            teacherParticipationPossible: expect.any(Object),
            ambassadorsPilotTeachersLeadingTeachersIdentified: expect.any(Object),
            usersCanRegisterToPortal: expect.any(Object),
          })
        );
      });
    });

    describe('getOrganizationInProject', () => {
      it('should return NewOrganizationInProject for default OrganizationInProject initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOrganizationInProjectFormGroup(sampleWithNewData);

        const organizationInProject = service.getOrganizationInProject(formGroup) as any;

        expect(organizationInProject).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrganizationInProject for empty OrganizationInProject initial value', () => {
        const formGroup = service.createOrganizationInProjectFormGroup();

        const organizationInProject = service.getOrganizationInProject(formGroup) as any;

        expect(organizationInProject).toMatchObject({});
      });

      it('should return IOrganizationInProject', () => {
        const formGroup = service.createOrganizationInProjectFormGroup(sampleWithRequiredData);

        const organizationInProject = service.getOrganizationInProject(formGroup) as any;

        expect(organizationInProject).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrganizationInProject should not enable id FormControl', () => {
        const formGroup = service.createOrganizationInProjectFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrganizationInProject should disable id FormControl', () => {
        const formGroup = service.createOrganizationInProjectFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

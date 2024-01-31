import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../project-partner.test-samples';

import { ProjectPartnerFormService } from './project-partner-form.service';

describe('ProjectPartner Form Service', () => {
  let service: ProjectPartnerFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectPartnerFormService);
  });

  describe('Service methods', () => {
    describe('createProjectPartnerFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProjectPartnerFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            odataEtag: expect.any(Object),
            no: expect.any(Object),
            jobNo: expect.any(Object),
            vendorCode: expect.any(Object),
            vendorName: expect.any(Object),
            countryCode: expect.any(Object),
            countryName: expect.any(Object),
            partnerAmount: expect.any(Object),
            isActive: expect.any(Object),
            createdBy: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });

      it('passing IProjectPartner should create a new form with FormGroup', () => {
        const formGroup = service.createProjectPartnerFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            odataEtag: expect.any(Object),
            no: expect.any(Object),
            jobNo: expect.any(Object),
            vendorCode: expect.any(Object),
            vendorName: expect.any(Object),
            countryCode: expect.any(Object),
            countryName: expect.any(Object),
            partnerAmount: expect.any(Object),
            isActive: expect.any(Object),
            createdBy: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });
    });

    describe('getProjectPartner', () => {
      it('should return NewProjectPartner for default ProjectPartner initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProjectPartnerFormGroup(sampleWithNewData);

        const projectPartner = service.getProjectPartner(formGroup) as any;

        expect(projectPartner).toMatchObject(sampleWithNewData);
      });

      it('should return NewProjectPartner for empty ProjectPartner initial value', () => {
        const formGroup = service.createProjectPartnerFormGroup();

        const projectPartner = service.getProjectPartner(formGroup) as any;

        expect(projectPartner).toMatchObject({});
      });

      it('should return IProjectPartner', () => {
        const formGroup = service.createProjectPartnerFormGroup(sampleWithRequiredData);

        const projectPartner = service.getProjectPartner(formGroup) as any;

        expect(projectPartner).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProjectPartner should not enable id FormControl', () => {
        const formGroup = service.createProjectPartnerFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProjectPartner should disable id FormControl', () => {
        const formGroup = service.createProjectPartnerFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

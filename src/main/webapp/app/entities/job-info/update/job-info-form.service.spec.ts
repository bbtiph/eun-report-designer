import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../job-info.test-samples';

import { JobInfoFormService } from './job-info-form.service';

describe('JobInfo Form Service', () => {
  let service: JobInfoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JobInfoFormService);
  });

  describe('Service methods', () => {
    describe('createJobInfoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createJobInfoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            odataEtag: expect.any(Object),
            externalId: expect.any(Object),
            jobNumber: expect.any(Object),
            description: expect.any(Object),
            description2: expect.any(Object),
            billToCustomerNo: expect.any(Object),
            billToName: expect.any(Object),
            billToCountryRegionCode: expect.any(Object),
            sellToContact: expect.any(Object),
            yourReference: expect.any(Object),
            contractNo: expect.any(Object),
            statusProposal: expect.any(Object),
            startingDate: expect.any(Object),
            endingDate: expect.any(Object),
            durationInMonths: expect.any(Object),
            projectManager: expect.any(Object),
            projectManagerMail: expect.any(Object),
            eunRole: expect.any(Object),
            visaNumber: expect.any(Object),
            jobType: expect.any(Object),
            jobTypeText: expect.any(Object),
            jobProgram: expect.any(Object),
            programManager: expect.any(Object),
            budgetEUN: expect.any(Object),
            fundingEUN: expect.any(Object),
            fundingRate: expect.any(Object),
            budgetConsortium: expect.any(Object),
            fundingConsortium: expect.any(Object),
            overheadPerc: expect.any(Object),
          })
        );
      });

      it('passing IJobInfo should create a new form with FormGroup', () => {
        const formGroup = service.createJobInfoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            odataEtag: expect.any(Object),
            externalId: expect.any(Object),
            jobNumber: expect.any(Object),
            description: expect.any(Object),
            description2: expect.any(Object),
            billToCustomerNo: expect.any(Object),
            billToName: expect.any(Object),
            billToCountryRegionCode: expect.any(Object),
            sellToContact: expect.any(Object),
            yourReference: expect.any(Object),
            contractNo: expect.any(Object),
            statusProposal: expect.any(Object),
            startingDate: expect.any(Object),
            endingDate: expect.any(Object),
            durationInMonths: expect.any(Object),
            projectManager: expect.any(Object),
            projectManagerMail: expect.any(Object),
            eunRole: expect.any(Object),
            visaNumber: expect.any(Object),
            jobType: expect.any(Object),
            jobTypeText: expect.any(Object),
            jobProgram: expect.any(Object),
            programManager: expect.any(Object),
            budgetEUN: expect.any(Object),
            fundingEUN: expect.any(Object),
            fundingRate: expect.any(Object),
            budgetConsortium: expect.any(Object),
            fundingConsortium: expect.any(Object),
            overheadPerc: expect.any(Object),
          })
        );
      });
    });

    describe('getJobInfo', () => {
      it('should return NewJobInfo for default JobInfo initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createJobInfoFormGroup(sampleWithNewData);

        const jobInfo = service.getJobInfo(formGroup) as any;

        expect(jobInfo).toMatchObject(sampleWithNewData);
      });

      it('should return NewJobInfo for empty JobInfo initial value', () => {
        const formGroup = service.createJobInfoFormGroup();

        const jobInfo = service.getJobInfo(formGroup) as any;

        expect(jobInfo).toMatchObject({});
      });

      it('should return IJobInfo', () => {
        const formGroup = service.createJobInfoFormGroup(sampleWithRequiredData);

        const jobInfo = service.getJobInfo(formGroup) as any;

        expect(jobInfo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IJobInfo should not enable id FormControl', () => {
        const formGroup = service.createJobInfoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewJobInfo should disable id FormControl', () => {
        const formGroup = service.createJobInfoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

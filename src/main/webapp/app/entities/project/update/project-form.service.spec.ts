import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../project.test-samples';

import { ProjectFormService } from './project-form.service';

describe('Project Form Service', () => {
  let service: ProjectFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectFormService);
  });

  describe('Service methods', () => {
    describe('createProjectFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProjectFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            status: expect.any(Object),
            supportedCountryIds: expect.any(Object),
            supportedLanguageIds: expect.any(Object),
            name: expect.any(Object),
            acronym: expect.any(Object),
            period: expect.any(Object),
            description: expect.any(Object),
            contactEmail: expect.any(Object),
            contactName: expect.any(Object),
            totalBudget: expect.any(Object),
            totalFunding: expect.any(Object),
            totalBudgetForEun: expect.any(Object),
            totalFundingForEun: expect.any(Object),
            fundingValue: expect.any(Object),
            percentageOfFunding: expect.any(Object),
            percentageOfIndirectCosts: expect.any(Object),
            percentageOfFundingForEun: expect.any(Object),
            percentageOfIndirectCostsForEun: expect.any(Object),
            fundingExtraComment: expect.any(Object),
            programme: expect.any(Object),
            euDg: expect.any(Object),
            roleOfEun: expect.any(Object),
            summary: expect.any(Object),
            mainTasks: expect.any(Object),
            expectedOutcomes: expect.any(Object),
            euCallReference: expect.any(Object),
            euProjectReference: expect.any(Object),
            euCallDeadline: expect.any(Object),
            projectManager: expect.any(Object),
            referenceNumberOfProject: expect.any(Object),
            eunTeam: expect.any(Object),
            sysCreatTimestamp: expect.any(Object),
            sysCreatIpAddress: expect.any(Object),
            sysModifTimestamp: expect.any(Object),
            sysModifIpAddress: expect.any(Object),
            organizationInProject: expect.any(Object),
            personInProject: expect.any(Object),
          })
        );
      });

      it('passing IProject should create a new form with FormGroup', () => {
        const formGroup = service.createProjectFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            status: expect.any(Object),
            supportedCountryIds: expect.any(Object),
            supportedLanguageIds: expect.any(Object),
            name: expect.any(Object),
            acronym: expect.any(Object),
            period: expect.any(Object),
            description: expect.any(Object),
            contactEmail: expect.any(Object),
            contactName: expect.any(Object),
            totalBudget: expect.any(Object),
            totalFunding: expect.any(Object),
            totalBudgetForEun: expect.any(Object),
            totalFundingForEun: expect.any(Object),
            fundingValue: expect.any(Object),
            percentageOfFunding: expect.any(Object),
            percentageOfIndirectCosts: expect.any(Object),
            percentageOfFundingForEun: expect.any(Object),
            percentageOfIndirectCostsForEun: expect.any(Object),
            fundingExtraComment: expect.any(Object),
            programme: expect.any(Object),
            euDg: expect.any(Object),
            roleOfEun: expect.any(Object),
            summary: expect.any(Object),
            mainTasks: expect.any(Object),
            expectedOutcomes: expect.any(Object),
            euCallReference: expect.any(Object),
            euProjectReference: expect.any(Object),
            euCallDeadline: expect.any(Object),
            projectManager: expect.any(Object),
            referenceNumberOfProject: expect.any(Object),
            eunTeam: expect.any(Object),
            sysCreatTimestamp: expect.any(Object),
            sysCreatIpAddress: expect.any(Object),
            sysModifTimestamp: expect.any(Object),
            sysModifIpAddress: expect.any(Object),
            organizationInProject: expect.any(Object),
            personInProject: expect.any(Object),
          })
        );
      });
    });

    describe('getProject', () => {
      it('should return NewProject for default Project initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProjectFormGroup(sampleWithNewData);

        const project = service.getProject(formGroup) as any;

        expect(project).toMatchObject(sampleWithNewData);
      });

      it('should return NewProject for empty Project initial value', () => {
        const formGroup = service.createProjectFormGroup();

        const project = service.getProject(formGroup) as any;

        expect(project).toMatchObject({});
      });

      it('should return IProject', () => {
        const formGroup = service.createProjectFormGroup(sampleWithRequiredData);

        const project = service.getProject(formGroup) as any;

        expect(project).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProject should not enable id FormControl', () => {
        const formGroup = service.createProjectFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProject should disable id FormControl', () => {
        const formGroup = service.createProjectFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

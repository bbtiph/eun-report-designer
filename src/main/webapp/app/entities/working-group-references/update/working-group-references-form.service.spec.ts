import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../working-group-references.test-samples';

import { WorkingGroupReferencesFormService } from './working-group-references-form.service';

describe('WorkingGroupReferences Form Service', () => {
  let service: WorkingGroupReferencesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WorkingGroupReferencesFormService);
  });

  describe('Service methods', () => {
    describe('createWorkingGroupReferencesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createWorkingGroupReferencesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            countryCode: expect.any(Object),
            countryName: expect.any(Object),
            countryRepresentativeFirstName: expect.any(Object),
            countryRepresentativeLastName: expect.any(Object),
            countryRepresentativeMail: expect.any(Object),
            countryRepresentativePosition: expect.any(Object),
            countryRepresentativeStartDate: expect.any(Object),
            countryRepresentativeEndDate: expect.any(Object),
            countryRepresentativeMinistry: expect.any(Object),
            countryRepresentativeDepartment: expect.any(Object),
            contactEunFirstName: expect.any(Object),
            contactEunLastName: expect.any(Object),
            type: expect.any(Object),
            isActive: expect.any(Object),
          })
        );
      });

      it('passing IWorkingGroupReferences should create a new form with FormGroup', () => {
        const formGroup = service.createWorkingGroupReferencesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            countryCode: expect.any(Object),
            countryName: expect.any(Object),
            countryRepresentativeFirstName: expect.any(Object),
            countryRepresentativeLastName: expect.any(Object),
            countryRepresentativeMail: expect.any(Object),
            countryRepresentativePosition: expect.any(Object),
            countryRepresentativeStartDate: expect.any(Object),
            countryRepresentativeEndDate: expect.any(Object),
            countryRepresentativeMinistry: expect.any(Object),
            countryRepresentativeDepartment: expect.any(Object),
            contactEunFirstName: expect.any(Object),
            contactEunLastName: expect.any(Object),
            type: expect.any(Object),
            isActive: expect.any(Object),
          })
        );
      });
    });

    describe('getWorkingGroupReferences', () => {
      it('should return NewWorkingGroupReferences for default WorkingGroupReferences initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createWorkingGroupReferencesFormGroup(sampleWithNewData);

        const workingGroupReferences = service.getWorkingGroupReferences(formGroup) as any;

        expect(workingGroupReferences).toMatchObject(sampleWithNewData);
      });

      it('should return NewWorkingGroupReferences for empty WorkingGroupReferences initial value', () => {
        const formGroup = service.createWorkingGroupReferencesFormGroup();

        const workingGroupReferences = service.getWorkingGroupReferences(formGroup) as any;

        expect(workingGroupReferences).toMatchObject({});
      });

      it('should return IWorkingGroupReferences', () => {
        const formGroup = service.createWorkingGroupReferencesFormGroup(sampleWithRequiredData);

        const workingGroupReferences = service.getWorkingGroupReferences(formGroup) as any;

        expect(workingGroupReferences).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IWorkingGroupReferences should not enable id FormControl', () => {
        const formGroup = service.createWorkingGroupReferencesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewWorkingGroupReferences should disable id FormControl', () => {
        const formGroup = service.createWorkingGroupReferencesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

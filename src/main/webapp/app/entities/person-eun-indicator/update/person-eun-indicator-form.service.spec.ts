import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../person-eun-indicator.test-samples';

import { PersonEunIndicatorFormService } from './person-eun-indicator-form.service';

describe('PersonEunIndicator Form Service', () => {
  let service: PersonEunIndicatorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonEunIndicatorFormService);
  });

  describe('Service methods', () => {
    describe('createPersonEunIndicatorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPersonEunIndicatorFormGroup();

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

      it('passing IPersonEunIndicator should create a new form with FormGroup', () => {
        const formGroup = service.createPersonEunIndicatorFormGroup(sampleWithRequiredData);

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

    describe('getPersonEunIndicator', () => {
      it('should return NewPersonEunIndicator for default PersonEunIndicator initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPersonEunIndicatorFormGroup(sampleWithNewData);

        const personEunIndicator = service.getPersonEunIndicator(formGroup) as any;

        expect(personEunIndicator).toMatchObject(sampleWithNewData);
      });

      it('should return NewPersonEunIndicator for empty PersonEunIndicator initial value', () => {
        const formGroup = service.createPersonEunIndicatorFormGroup();

        const personEunIndicator = service.getPersonEunIndicator(formGroup) as any;

        expect(personEunIndicator).toMatchObject({});
      });

      it('should return IPersonEunIndicator', () => {
        const formGroup = service.createPersonEunIndicatorFormGroup(sampleWithRequiredData);

        const personEunIndicator = service.getPersonEunIndicator(formGroup) as any;

        expect(personEunIndicator).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPersonEunIndicator should not enable id FormControl', () => {
        const formGroup = service.createPersonEunIndicatorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPersonEunIndicator should disable id FormControl', () => {
        const formGroup = service.createPersonEunIndicatorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

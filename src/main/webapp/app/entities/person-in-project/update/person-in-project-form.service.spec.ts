import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../person-in-project.test-samples';

import { PersonInProjectFormService } from './person-in-project-form.service';

describe('PersonInProject Form Service', () => {
  let service: PersonInProjectFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonInProjectFormService);
  });

  describe('Service methods', () => {
    describe('createPersonInProjectFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPersonInProjectFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            roleInProject: expect.any(Object),
          })
        );
      });

      it('passing IPersonInProject should create a new form with FormGroup', () => {
        const formGroup = service.createPersonInProjectFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            roleInProject: expect.any(Object),
          })
        );
      });
    });

    describe('getPersonInProject', () => {
      it('should return NewPersonInProject for default PersonInProject initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPersonInProjectFormGroup(sampleWithNewData);

        const personInProject = service.getPersonInProject(formGroup) as any;

        expect(personInProject).toMatchObject(sampleWithNewData);
      });

      it('should return NewPersonInProject for empty PersonInProject initial value', () => {
        const formGroup = service.createPersonInProjectFormGroup();

        const personInProject = service.getPersonInProject(formGroup) as any;

        expect(personInProject).toMatchObject({});
      });

      it('should return IPersonInProject', () => {
        const formGroup = service.createPersonInProjectFormGroup(sampleWithRequiredData);

        const personInProject = service.getPersonInProject(formGroup) as any;

        expect(personInProject).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPersonInProject should not enable id FormControl', () => {
        const formGroup = service.createPersonInProjectFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPersonInProject should disable id FormControl', () => {
        const formGroup = service.createPersonInProjectFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

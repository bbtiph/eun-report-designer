import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../eun-team.test-samples';

import { EunTeamFormService } from './eun-team-form.service';

describe('EunTeam Form Service', () => {
  let service: EunTeamFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EunTeamFormService);
  });

  describe('Service methods', () => {
    describe('createEunTeamFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEunTeamFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            eunTeamMember: expect.any(Object),
          })
        );
      });

      it('passing IEunTeam should create a new form with FormGroup', () => {
        const formGroup = service.createEunTeamFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            eunTeamMember: expect.any(Object),
          })
        );
      });
    });

    describe('getEunTeam', () => {
      it('should return NewEunTeam for default EunTeam initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEunTeamFormGroup(sampleWithNewData);

        const eunTeam = service.getEunTeam(formGroup) as any;

        expect(eunTeam).toMatchObject(sampleWithNewData);
      });

      it('should return NewEunTeam for empty EunTeam initial value', () => {
        const formGroup = service.createEunTeamFormGroup();

        const eunTeam = service.getEunTeam(formGroup) as any;

        expect(eunTeam).toMatchObject({});
      });

      it('should return IEunTeam', () => {
        const formGroup = service.createEunTeamFormGroup(sampleWithRequiredData);

        const eunTeam = service.getEunTeam(formGroup) as any;

        expect(eunTeam).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEunTeam should not enable id FormControl', () => {
        const formGroup = service.createEunTeamFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEunTeam should disable id FormControl', () => {
        const formGroup = service.createEunTeamFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

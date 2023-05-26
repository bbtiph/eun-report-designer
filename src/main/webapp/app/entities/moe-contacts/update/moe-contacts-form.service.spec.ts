import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../moe-contacts.test-samples';

import { MoeContactsFormService } from './moe-contacts-form.service';

describe('MoeContacts Form Service', () => {
  let service: MoeContactsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MoeContactsFormService);
  });

  describe('Service methods', () => {
    describe('createMoeContactsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMoeContactsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            countryCode: expect.any(Object),
            countryName: expect.any(Object),
            priorityNumber: expect.any(Object),
            ministryName: expect.any(Object),
            ministryEnglishName: expect.any(Object),
            postalAddress: expect.any(Object),
            invoicingAddress: expect.any(Object),
            shippingAddress: expect.any(Object),
            contactEunFirstName: expect.any(Object),
            contactEunLastName: expect.any(Object),
          })
        );
      });

      it('passing IMoeContacts should create a new form with FormGroup', () => {
        const formGroup = service.createMoeContactsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            countryCode: expect.any(Object),
            countryName: expect.any(Object),
            priorityNumber: expect.any(Object),
            ministryName: expect.any(Object),
            ministryEnglishName: expect.any(Object),
            postalAddress: expect.any(Object),
            invoicingAddress: expect.any(Object),
            shippingAddress: expect.any(Object),
            contactEunFirstName: expect.any(Object),
            contactEunLastName: expect.any(Object),
          })
        );
      });
    });

    describe('getMoeContacts', () => {
      it('should return NewMoeContacts for default MoeContacts initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMoeContactsFormGroup(sampleWithNewData);

        const moeContacts = service.getMoeContacts(formGroup) as any;

        expect(moeContacts).toMatchObject(sampleWithNewData);
      });

      it('should return NewMoeContacts for empty MoeContacts initial value', () => {
        const formGroup = service.createMoeContactsFormGroup();

        const moeContacts = service.getMoeContacts(formGroup) as any;

        expect(moeContacts).toMatchObject({});
      });

      it('should return IMoeContacts', () => {
        const formGroup = service.createMoeContactsFormGroup(sampleWithRequiredData);

        const moeContacts = service.getMoeContacts(formGroup) as any;

        expect(moeContacts).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMoeContacts should not enable id FormControl', () => {
        const formGroup = service.createMoeContactsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMoeContacts should disable id FormControl', () => {
        const formGroup = service.createMoeContactsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

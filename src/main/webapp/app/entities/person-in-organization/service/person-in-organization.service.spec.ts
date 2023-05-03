import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPersonInOrganization } from '../person-in-organization.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../person-in-organization.test-samples';

import { PersonInOrganizationService } from './person-in-organization.service';

const requireRestSample: IPersonInOrganization = {
  ...sampleWithRequiredData,
};

describe('PersonInOrganization Service', () => {
  let service: PersonInOrganizationService;
  let httpMock: HttpTestingController;
  let expectedResult: IPersonInOrganization | IPersonInOrganization[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PersonInOrganizationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a PersonInOrganization', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const personInOrganization = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(personInOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PersonInOrganization', () => {
      const personInOrganization = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(personInOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PersonInOrganization', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PersonInOrganization', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PersonInOrganization', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPersonInOrganizationToCollectionIfMissing', () => {
      it('should add a PersonInOrganization to an empty array', () => {
        const personInOrganization: IPersonInOrganization = sampleWithRequiredData;
        expectedResult = service.addPersonInOrganizationToCollectionIfMissing([], personInOrganization);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personInOrganization);
      });

      it('should not add a PersonInOrganization to an array that contains it', () => {
        const personInOrganization: IPersonInOrganization = sampleWithRequiredData;
        const personInOrganizationCollection: IPersonInOrganization[] = [
          {
            ...personInOrganization,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPersonInOrganizationToCollectionIfMissing(personInOrganizationCollection, personInOrganization);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PersonInOrganization to an array that doesn't contain it", () => {
        const personInOrganization: IPersonInOrganization = sampleWithRequiredData;
        const personInOrganizationCollection: IPersonInOrganization[] = [sampleWithPartialData];
        expectedResult = service.addPersonInOrganizationToCollectionIfMissing(personInOrganizationCollection, personInOrganization);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personInOrganization);
      });

      it('should add only unique PersonInOrganization to an array', () => {
        const personInOrganizationArray: IPersonInOrganization[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const personInOrganizationCollection: IPersonInOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addPersonInOrganizationToCollectionIfMissing(personInOrganizationCollection, ...personInOrganizationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const personInOrganization: IPersonInOrganization = sampleWithRequiredData;
        const personInOrganization2: IPersonInOrganization = sampleWithPartialData;
        expectedResult = service.addPersonInOrganizationToCollectionIfMissing([], personInOrganization, personInOrganization2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personInOrganization);
        expect(expectedResult).toContain(personInOrganization2);
      });

      it('should accept null and undefined values', () => {
        const personInOrganization: IPersonInOrganization = sampleWithRequiredData;
        expectedResult = service.addPersonInOrganizationToCollectionIfMissing([], null, personInOrganization, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personInOrganization);
      });

      it('should return initial array if no PersonInOrganization is added', () => {
        const personInOrganizationCollection: IPersonInOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addPersonInOrganizationToCollectionIfMissing(personInOrganizationCollection, undefined, null);
        expect(expectedResult).toEqual(personInOrganizationCollection);
      });
    });

    describe('comparePersonInOrganization', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePersonInOrganization(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePersonInOrganization(entity1, entity2);
        const compareResult2 = service.comparePersonInOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePersonInOrganization(entity1, entity2);
        const compareResult2 = service.comparePersonInOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePersonInOrganization(entity1, entity2);
        const compareResult2 = service.comparePersonInOrganization(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

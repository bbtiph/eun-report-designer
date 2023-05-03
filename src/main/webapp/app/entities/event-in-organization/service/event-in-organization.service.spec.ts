import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEventInOrganization } from '../event-in-organization.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../event-in-organization.test-samples';

import { EventInOrganizationService } from './event-in-organization.service';

const requireRestSample: IEventInOrganization = {
  ...sampleWithRequiredData,
};

describe('EventInOrganization Service', () => {
  let service: EventInOrganizationService;
  let httpMock: HttpTestingController;
  let expectedResult: IEventInOrganization | IEventInOrganization[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EventInOrganizationService);
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

    it('should create a EventInOrganization', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const eventInOrganization = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(eventInOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EventInOrganization', () => {
      const eventInOrganization = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(eventInOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EventInOrganization', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EventInOrganization', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EventInOrganization', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEventInOrganizationToCollectionIfMissing', () => {
      it('should add a EventInOrganization to an empty array', () => {
        const eventInOrganization: IEventInOrganization = sampleWithRequiredData;
        expectedResult = service.addEventInOrganizationToCollectionIfMissing([], eventInOrganization);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventInOrganization);
      });

      it('should not add a EventInOrganization to an array that contains it', () => {
        const eventInOrganization: IEventInOrganization = sampleWithRequiredData;
        const eventInOrganizationCollection: IEventInOrganization[] = [
          {
            ...eventInOrganization,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEventInOrganizationToCollectionIfMissing(eventInOrganizationCollection, eventInOrganization);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EventInOrganization to an array that doesn't contain it", () => {
        const eventInOrganization: IEventInOrganization = sampleWithRequiredData;
        const eventInOrganizationCollection: IEventInOrganization[] = [sampleWithPartialData];
        expectedResult = service.addEventInOrganizationToCollectionIfMissing(eventInOrganizationCollection, eventInOrganization);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventInOrganization);
      });

      it('should add only unique EventInOrganization to an array', () => {
        const eventInOrganizationArray: IEventInOrganization[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const eventInOrganizationCollection: IEventInOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addEventInOrganizationToCollectionIfMissing(eventInOrganizationCollection, ...eventInOrganizationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const eventInOrganization: IEventInOrganization = sampleWithRequiredData;
        const eventInOrganization2: IEventInOrganization = sampleWithPartialData;
        expectedResult = service.addEventInOrganizationToCollectionIfMissing([], eventInOrganization, eventInOrganization2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventInOrganization);
        expect(expectedResult).toContain(eventInOrganization2);
      });

      it('should accept null and undefined values', () => {
        const eventInOrganization: IEventInOrganization = sampleWithRequiredData;
        expectedResult = service.addEventInOrganizationToCollectionIfMissing([], null, eventInOrganization, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventInOrganization);
      });

      it('should return initial array if no EventInOrganization is added', () => {
        const eventInOrganizationCollection: IEventInOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addEventInOrganizationToCollectionIfMissing(eventInOrganizationCollection, undefined, null);
        expect(expectedResult).toEqual(eventInOrganizationCollection);
      });
    });

    describe('compareEventInOrganization', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEventInOrganization(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEventInOrganization(entity1, entity2);
        const compareResult2 = service.compareEventInOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEventInOrganization(entity1, entity2);
        const compareResult2 = service.compareEventInOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEventInOrganization(entity1, entity2);
        const compareResult2 = service.compareEventInOrganization(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IOperationalBodyMember } from '../operational-body-member.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../operational-body-member.test-samples';

import { OperationalBodyMemberService, RestOperationalBodyMember } from './operational-body-member.service';

const requireRestSample: RestOperationalBodyMember = {
  ...sampleWithRequiredData,
  startDate: sampleWithRequiredData.startDate?.format(DATE_FORMAT),
  endDate: sampleWithRequiredData.endDate?.format(DATE_FORMAT),
};

describe('OperationalBodyMember Service', () => {
  let service: OperationalBodyMemberService;
  let httpMock: HttpTestingController;
  let expectedResult: IOperationalBodyMember | IOperationalBodyMember[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OperationalBodyMemberService);
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

    it('should create a OperationalBodyMember', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const operationalBodyMember = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(operationalBodyMember).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OperationalBodyMember', () => {
      const operationalBodyMember = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(operationalBodyMember).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OperationalBodyMember', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OperationalBodyMember', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OperationalBodyMember', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOperationalBodyMemberToCollectionIfMissing', () => {
      it('should add a OperationalBodyMember to an empty array', () => {
        const operationalBodyMember: IOperationalBodyMember = sampleWithRequiredData;
        expectedResult = service.addOperationalBodyMemberToCollectionIfMissing([], operationalBodyMember);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(operationalBodyMember);
      });

      it('should not add a OperationalBodyMember to an array that contains it', () => {
        const operationalBodyMember: IOperationalBodyMember = sampleWithRequiredData;
        const operationalBodyMemberCollection: IOperationalBodyMember[] = [
          {
            ...operationalBodyMember,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOperationalBodyMemberToCollectionIfMissing(operationalBodyMemberCollection, operationalBodyMember);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OperationalBodyMember to an array that doesn't contain it", () => {
        const operationalBodyMember: IOperationalBodyMember = sampleWithRequiredData;
        const operationalBodyMemberCollection: IOperationalBodyMember[] = [sampleWithPartialData];
        expectedResult = service.addOperationalBodyMemberToCollectionIfMissing(operationalBodyMemberCollection, operationalBodyMember);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(operationalBodyMember);
      });

      it('should add only unique OperationalBodyMember to an array', () => {
        const operationalBodyMemberArray: IOperationalBodyMember[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const operationalBodyMemberCollection: IOperationalBodyMember[] = [sampleWithRequiredData];
        expectedResult = service.addOperationalBodyMemberToCollectionIfMissing(
          operationalBodyMemberCollection,
          ...operationalBodyMemberArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const operationalBodyMember: IOperationalBodyMember = sampleWithRequiredData;
        const operationalBodyMember2: IOperationalBodyMember = sampleWithPartialData;
        expectedResult = service.addOperationalBodyMemberToCollectionIfMissing([], operationalBodyMember, operationalBodyMember2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(operationalBodyMember);
        expect(expectedResult).toContain(operationalBodyMember2);
      });

      it('should accept null and undefined values', () => {
        const operationalBodyMember: IOperationalBodyMember = sampleWithRequiredData;
        expectedResult = service.addOperationalBodyMemberToCollectionIfMissing([], null, operationalBodyMember, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(operationalBodyMember);
      });

      it('should return initial array if no OperationalBodyMember is added', () => {
        const operationalBodyMemberCollection: IOperationalBodyMember[] = [sampleWithRequiredData];
        expectedResult = service.addOperationalBodyMemberToCollectionIfMissing(operationalBodyMemberCollection, undefined, null);
        expect(expectedResult).toEqual(operationalBodyMemberCollection);
      });
    });

    describe('compareOperationalBodyMember', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOperationalBodyMember(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOperationalBodyMember(entity1, entity2);
        const compareResult2 = service.compareOperationalBodyMember(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareOperationalBodyMember(entity1, entity2);
        const compareResult2 = service.compareOperationalBodyMember(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareOperationalBodyMember(entity1, entity2);
        const compareResult2 = service.compareOperationalBodyMember(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

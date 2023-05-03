import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEunTeamMember } from '../eun-team-member.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../eun-team-member.test-samples';

import { EunTeamMemberService } from './eun-team-member.service';

const requireRestSample: IEunTeamMember = {
  ...sampleWithRequiredData,
};

describe('EunTeamMember Service', () => {
  let service: EunTeamMemberService;
  let httpMock: HttpTestingController;
  let expectedResult: IEunTeamMember | IEunTeamMember[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EunTeamMemberService);
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

    it('should create a EunTeamMember', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const eunTeamMember = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(eunTeamMember).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EunTeamMember', () => {
      const eunTeamMember = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(eunTeamMember).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EunTeamMember', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EunTeamMember', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EunTeamMember', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEunTeamMemberToCollectionIfMissing', () => {
      it('should add a EunTeamMember to an empty array', () => {
        const eunTeamMember: IEunTeamMember = sampleWithRequiredData;
        expectedResult = service.addEunTeamMemberToCollectionIfMissing([], eunTeamMember);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eunTeamMember);
      });

      it('should not add a EunTeamMember to an array that contains it', () => {
        const eunTeamMember: IEunTeamMember = sampleWithRequiredData;
        const eunTeamMemberCollection: IEunTeamMember[] = [
          {
            ...eunTeamMember,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEunTeamMemberToCollectionIfMissing(eunTeamMemberCollection, eunTeamMember);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EunTeamMember to an array that doesn't contain it", () => {
        const eunTeamMember: IEunTeamMember = sampleWithRequiredData;
        const eunTeamMemberCollection: IEunTeamMember[] = [sampleWithPartialData];
        expectedResult = service.addEunTeamMemberToCollectionIfMissing(eunTeamMemberCollection, eunTeamMember);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eunTeamMember);
      });

      it('should add only unique EunTeamMember to an array', () => {
        const eunTeamMemberArray: IEunTeamMember[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const eunTeamMemberCollection: IEunTeamMember[] = [sampleWithRequiredData];
        expectedResult = service.addEunTeamMemberToCollectionIfMissing(eunTeamMemberCollection, ...eunTeamMemberArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const eunTeamMember: IEunTeamMember = sampleWithRequiredData;
        const eunTeamMember2: IEunTeamMember = sampleWithPartialData;
        expectedResult = service.addEunTeamMemberToCollectionIfMissing([], eunTeamMember, eunTeamMember2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eunTeamMember);
        expect(expectedResult).toContain(eunTeamMember2);
      });

      it('should accept null and undefined values', () => {
        const eunTeamMember: IEunTeamMember = sampleWithRequiredData;
        expectedResult = service.addEunTeamMemberToCollectionIfMissing([], null, eunTeamMember, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eunTeamMember);
      });

      it('should return initial array if no EunTeamMember is added', () => {
        const eunTeamMemberCollection: IEunTeamMember[] = [sampleWithRequiredData];
        expectedResult = service.addEunTeamMemberToCollectionIfMissing(eunTeamMemberCollection, undefined, null);
        expect(expectedResult).toEqual(eunTeamMemberCollection);
      });
    });

    describe('compareEunTeamMember', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEunTeamMember(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEunTeamMember(entity1, entity2);
        const compareResult2 = service.compareEunTeamMember(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEunTeamMember(entity1, entity2);
        const compareResult2 = service.compareEunTeamMember(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEunTeamMember(entity1, entity2);
        const compareResult2 = service.compareEunTeamMember(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

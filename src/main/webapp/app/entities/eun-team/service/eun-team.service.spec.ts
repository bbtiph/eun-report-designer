import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEunTeam } from '../eun-team.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../eun-team.test-samples';

import { EunTeamService } from './eun-team.service';

const requireRestSample: IEunTeam = {
  ...sampleWithRequiredData,
};

describe('EunTeam Service', () => {
  let service: EunTeamService;
  let httpMock: HttpTestingController;
  let expectedResult: IEunTeam | IEunTeam[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EunTeamService);
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

    it('should create a EunTeam', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const eunTeam = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(eunTeam).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EunTeam', () => {
      const eunTeam = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(eunTeam).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EunTeam', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EunTeam', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EunTeam', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEunTeamToCollectionIfMissing', () => {
      it('should add a EunTeam to an empty array', () => {
        const eunTeam: IEunTeam = sampleWithRequiredData;
        expectedResult = service.addEunTeamToCollectionIfMissing([], eunTeam);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eunTeam);
      });

      it('should not add a EunTeam to an array that contains it', () => {
        const eunTeam: IEunTeam = sampleWithRequiredData;
        const eunTeamCollection: IEunTeam[] = [
          {
            ...eunTeam,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEunTeamToCollectionIfMissing(eunTeamCollection, eunTeam);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EunTeam to an array that doesn't contain it", () => {
        const eunTeam: IEunTeam = sampleWithRequiredData;
        const eunTeamCollection: IEunTeam[] = [sampleWithPartialData];
        expectedResult = service.addEunTeamToCollectionIfMissing(eunTeamCollection, eunTeam);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eunTeam);
      });

      it('should add only unique EunTeam to an array', () => {
        const eunTeamArray: IEunTeam[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const eunTeamCollection: IEunTeam[] = [sampleWithRequiredData];
        expectedResult = service.addEunTeamToCollectionIfMissing(eunTeamCollection, ...eunTeamArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const eunTeam: IEunTeam = sampleWithRequiredData;
        const eunTeam2: IEunTeam = sampleWithPartialData;
        expectedResult = service.addEunTeamToCollectionIfMissing([], eunTeam, eunTeam2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eunTeam);
        expect(expectedResult).toContain(eunTeam2);
      });

      it('should accept null and undefined values', () => {
        const eunTeam: IEunTeam = sampleWithRequiredData;
        expectedResult = service.addEunTeamToCollectionIfMissing([], null, eunTeam, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eunTeam);
      });

      it('should return initial array if no EunTeam is added', () => {
        const eunTeamCollection: IEunTeam[] = [sampleWithRequiredData];
        expectedResult = service.addEunTeamToCollectionIfMissing(eunTeamCollection, undefined, null);
        expect(expectedResult).toEqual(eunTeamCollection);
      });
    });

    describe('compareEunTeam', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEunTeam(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEunTeam(entity1, entity2);
        const compareResult2 = service.compareEunTeam(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEunTeam(entity1, entity2);
        const compareResult2 = service.compareEunTeam(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEunTeam(entity1, entity2);
        const compareResult2 = service.compareEunTeam(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

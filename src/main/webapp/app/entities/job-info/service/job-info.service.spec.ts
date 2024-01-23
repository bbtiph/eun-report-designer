import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IJobInfo } from '../job-info.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../job-info.test-samples';

import { JobInfoService, RestJobInfo } from './job-info.service';

const requireRestSample: RestJobInfo = {
  ...sampleWithRequiredData,
  startingDate: sampleWithRequiredData.startingDate?.format(DATE_FORMAT),
  endingDate: sampleWithRequiredData.endingDate?.format(DATE_FORMAT),
};

describe('JobInfo Service', () => {
  let service: JobInfoService;
  let httpMock: HttpTestingController;
  let expectedResult: IJobInfo | IJobInfo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(JobInfoService);
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

    it('should create a JobInfo', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const jobInfo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(jobInfo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a JobInfo', () => {
      const jobInfo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(jobInfo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a JobInfo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of JobInfo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a JobInfo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addJobInfoToCollectionIfMissing', () => {
      it('should add a JobInfo to an empty array', () => {
        const jobInfo: IJobInfo = sampleWithRequiredData;
        expectedResult = service.addJobInfoToCollectionIfMissing([], jobInfo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(jobInfo);
      });

      it('should not add a JobInfo to an array that contains it', () => {
        const jobInfo: IJobInfo = sampleWithRequiredData;
        const jobInfoCollection: IJobInfo[] = [
          {
            ...jobInfo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addJobInfoToCollectionIfMissing(jobInfoCollection, jobInfo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a JobInfo to an array that doesn't contain it", () => {
        const jobInfo: IJobInfo = sampleWithRequiredData;
        const jobInfoCollection: IJobInfo[] = [sampleWithPartialData];
        expectedResult = service.addJobInfoToCollectionIfMissing(jobInfoCollection, jobInfo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(jobInfo);
      });

      it('should add only unique JobInfo to an array', () => {
        const jobInfoArray: IJobInfo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const jobInfoCollection: IJobInfo[] = [sampleWithRequiredData];
        expectedResult = service.addJobInfoToCollectionIfMissing(jobInfoCollection, ...jobInfoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const jobInfo: IJobInfo = sampleWithRequiredData;
        const jobInfo2: IJobInfo = sampleWithPartialData;
        expectedResult = service.addJobInfoToCollectionIfMissing([], jobInfo, jobInfo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(jobInfo);
        expect(expectedResult).toContain(jobInfo2);
      });

      it('should accept null and undefined values', () => {
        const jobInfo: IJobInfo = sampleWithRequiredData;
        expectedResult = service.addJobInfoToCollectionIfMissing([], null, jobInfo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(jobInfo);
      });

      it('should return initial array if no JobInfo is added', () => {
        const jobInfoCollection: IJobInfo[] = [sampleWithRequiredData];
        expectedResult = service.addJobInfoToCollectionIfMissing(jobInfoCollection, undefined, null);
        expect(expectedResult).toEqual(jobInfoCollection);
      });
    });

    describe('compareJobInfo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareJobInfo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareJobInfo(entity1, entity2);
        const compareResult2 = service.compareJobInfo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareJobInfo(entity1, entity2);
        const compareResult2 = service.compareJobInfo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareJobInfo(entity1, entity2);
        const compareResult2 = service.compareJobInfo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

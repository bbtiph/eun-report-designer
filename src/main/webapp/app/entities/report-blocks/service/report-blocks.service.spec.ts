import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IReportBlocks } from '../report-blocks.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../report-blocks.test-samples';

import { ReportBlocksService } from './report-blocks.service';

const requireRestSample: IReportBlocks = {
  ...sampleWithRequiredData,
};

describe('ReportBlocks Service', () => {
  let service: ReportBlocksService;
  let httpMock: HttpTestingController;
  let expectedResult: IReportBlocks | IReportBlocks[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ReportBlocksService);
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

    it('should create a ReportBlocks', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const reportBlocks = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(reportBlocks).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ReportBlocks', () => {
      const reportBlocks = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(reportBlocks).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ReportBlocks', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ReportBlocks', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ReportBlocks', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addReportBlocksToCollectionIfMissing', () => {
      it('should add a ReportBlocks to an empty array', () => {
        const reportBlocks: IReportBlocks = sampleWithRequiredData;
        expectedResult = service.addReportBlocksToCollectionIfMissing([], reportBlocks);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(reportBlocks);
      });

      it('should not add a ReportBlocks to an array that contains it', () => {
        const reportBlocks: IReportBlocks = sampleWithRequiredData;
        const reportBlocksCollection: IReportBlocks[] = [
          {
            ...reportBlocks,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addReportBlocksToCollectionIfMissing(reportBlocksCollection, reportBlocks);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ReportBlocks to an array that doesn't contain it", () => {
        const reportBlocks: IReportBlocks = sampleWithRequiredData;
        const reportBlocksCollection: IReportBlocks[] = [sampleWithPartialData];
        expectedResult = service.addReportBlocksToCollectionIfMissing(reportBlocksCollection, reportBlocks);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(reportBlocks);
      });

      it('should add only unique ReportBlocks to an array', () => {
        const reportBlocksArray: IReportBlocks[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const reportBlocksCollection: IReportBlocks[] = [sampleWithRequiredData];
        expectedResult = service.addReportBlocksToCollectionIfMissing(reportBlocksCollection, ...reportBlocksArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const reportBlocks: IReportBlocks = sampleWithRequiredData;
        const reportBlocks2: IReportBlocks = sampleWithPartialData;
        expectedResult = service.addReportBlocksToCollectionIfMissing([], reportBlocks, reportBlocks2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(reportBlocks);
        expect(expectedResult).toContain(reportBlocks2);
      });

      it('should accept null and undefined values', () => {
        const reportBlocks: IReportBlocks = sampleWithRequiredData;
        expectedResult = service.addReportBlocksToCollectionIfMissing([], null, reportBlocks, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(reportBlocks);
      });

      it('should return initial array if no ReportBlocks is added', () => {
        const reportBlocksCollection: IReportBlocks[] = [sampleWithRequiredData];
        expectedResult = service.addReportBlocksToCollectionIfMissing(reportBlocksCollection, undefined, null);
        expect(expectedResult).toEqual(reportBlocksCollection);
      });
    });

    describe('compareReportBlocks', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareReportBlocks(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareReportBlocks(entity1, entity2);
        const compareResult2 = service.compareReportBlocks(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareReportBlocks(entity1, entity2);
        const compareResult2 = service.compareReportBlocks(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareReportBlocks(entity1, entity2);
        const compareResult2 = service.compareReportBlocks(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

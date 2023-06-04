import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IReportBlocksContent } from '../report-blocks-content.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../report-blocks-content.test-samples';

import { ReportBlocksContentService } from './report-blocks-content.service';

const requireRestSample: IReportBlocksContent = {
  ...sampleWithRequiredData,
};

describe('ReportBlocksContent Service', () => {
  let service: ReportBlocksContentService;
  let httpMock: HttpTestingController;
  let expectedResult: IReportBlocksContent | IReportBlocksContent[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ReportBlocksContentService);
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

    it('should create a ReportBlocksContent', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const reportBlocksContent = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(reportBlocksContent).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ReportBlocksContent', () => {
      const reportBlocksContent = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(reportBlocksContent).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ReportBlocksContent', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ReportBlocksContent', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ReportBlocksContent', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addReportBlocksContentToCollectionIfMissing', () => {
      it('should add a ReportBlocksContent to an empty array', () => {
        const reportBlocksContent: IReportBlocksContent = sampleWithRequiredData;
        expectedResult = service.addReportBlocksContentToCollectionIfMissing([], reportBlocksContent);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(reportBlocksContent);
      });

      it('should not add a ReportBlocksContent to an array that contains it', () => {
        const reportBlocksContent: IReportBlocksContent = sampleWithRequiredData;
        const reportBlocksContentCollection: IReportBlocksContent[] = [
          {
            ...reportBlocksContent,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addReportBlocksContentToCollectionIfMissing(reportBlocksContentCollection, reportBlocksContent);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ReportBlocksContent to an array that doesn't contain it", () => {
        const reportBlocksContent: IReportBlocksContent = sampleWithRequiredData;
        const reportBlocksContentCollection: IReportBlocksContent[] = [sampleWithPartialData];
        expectedResult = service.addReportBlocksContentToCollectionIfMissing(reportBlocksContentCollection, reportBlocksContent);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(reportBlocksContent);
      });

      it('should add only unique ReportBlocksContent to an array', () => {
        const reportBlocksContentArray: IReportBlocksContent[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const reportBlocksContentCollection: IReportBlocksContent[] = [sampleWithRequiredData];
        expectedResult = service.addReportBlocksContentToCollectionIfMissing(reportBlocksContentCollection, ...reportBlocksContentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const reportBlocksContent: IReportBlocksContent = sampleWithRequiredData;
        const reportBlocksContent2: IReportBlocksContent = sampleWithPartialData;
        expectedResult = service.addReportBlocksContentToCollectionIfMissing([], reportBlocksContent, reportBlocksContent2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(reportBlocksContent);
        expect(expectedResult).toContain(reportBlocksContent2);
      });

      it('should accept null and undefined values', () => {
        const reportBlocksContent: IReportBlocksContent = sampleWithRequiredData;
        expectedResult = service.addReportBlocksContentToCollectionIfMissing([], null, reportBlocksContent, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(reportBlocksContent);
      });

      it('should return initial array if no ReportBlocksContent is added', () => {
        const reportBlocksContentCollection: IReportBlocksContent[] = [sampleWithRequiredData];
        expectedResult = service.addReportBlocksContentToCollectionIfMissing(reportBlocksContentCollection, undefined, null);
        expect(expectedResult).toEqual(reportBlocksContentCollection);
      });
    });

    describe('compareReportBlocksContent', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareReportBlocksContent(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareReportBlocksContent(entity1, entity2);
        const compareResult2 = service.compareReportBlocksContent(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareReportBlocksContent(entity1, entity2);
        const compareResult2 = service.compareReportBlocksContent(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareReportBlocksContent(entity1, entity2);
        const compareResult2 = service.compareReportBlocksContent(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

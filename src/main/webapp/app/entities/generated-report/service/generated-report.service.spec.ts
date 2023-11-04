import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IGeneratedReport } from '../generated-report.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../generated-report.test-samples';

import { GeneratedReportService, RestGeneratedReport } from './generated-report.service';

const requireRestSample: RestGeneratedReport = {
  ...sampleWithRequiredData,
  createdDate: sampleWithRequiredData.createdDate?.format(DATE_FORMAT),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.format(DATE_FORMAT),
};

describe('GeneratedReport Service', () => {
  let service: GeneratedReportService;
  let httpMock: HttpTestingController;
  let expectedResult: IGeneratedReport | IGeneratedReport[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GeneratedReportService);
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

    it('should create a GeneratedReport', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const generatedReport = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(generatedReport).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GeneratedReport', () => {
      const generatedReport = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(generatedReport).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GeneratedReport', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GeneratedReport', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GeneratedReport', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGeneratedReportToCollectionIfMissing', () => {
      it('should add a GeneratedReport to an empty array', () => {
        const generatedReport: IGeneratedReport = sampleWithRequiredData;
        expectedResult = service.addGeneratedReportToCollectionIfMissing([], generatedReport);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(generatedReport);
      });

      it('should not add a GeneratedReport to an array that contains it', () => {
        const generatedReport: IGeneratedReport = sampleWithRequiredData;
        const generatedReportCollection: IGeneratedReport[] = [
          {
            ...generatedReport,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGeneratedReportToCollectionIfMissing(generatedReportCollection, generatedReport);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GeneratedReport to an array that doesn't contain it", () => {
        const generatedReport: IGeneratedReport = sampleWithRequiredData;
        const generatedReportCollection: IGeneratedReport[] = [sampleWithPartialData];
        expectedResult = service.addGeneratedReportToCollectionIfMissing(generatedReportCollection, generatedReport);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(generatedReport);
      });

      it('should add only unique GeneratedReport to an array', () => {
        const generatedReportArray: IGeneratedReport[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const generatedReportCollection: IGeneratedReport[] = [sampleWithRequiredData];
        expectedResult = service.addGeneratedReportToCollectionIfMissing(generatedReportCollection, ...generatedReportArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const generatedReport: IGeneratedReport = sampleWithRequiredData;
        const generatedReport2: IGeneratedReport = sampleWithPartialData;
        expectedResult = service.addGeneratedReportToCollectionIfMissing([], generatedReport, generatedReport2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(generatedReport);
        expect(expectedResult).toContain(generatedReport2);
      });

      it('should accept null and undefined values', () => {
        const generatedReport: IGeneratedReport = sampleWithRequiredData;
        expectedResult = service.addGeneratedReportToCollectionIfMissing([], null, generatedReport, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(generatedReport);
      });

      it('should return initial array if no GeneratedReport is added', () => {
        const generatedReportCollection: IGeneratedReport[] = [sampleWithRequiredData];
        expectedResult = service.addGeneratedReportToCollectionIfMissing(generatedReportCollection, undefined, null);
        expect(expectedResult).toEqual(generatedReportCollection);
      });
    });

    describe('compareGeneratedReport', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGeneratedReport(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGeneratedReport(entity1, entity2);
        const compareResult2 = service.compareGeneratedReport(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGeneratedReport(entity1, entity2);
        const compareResult2 = service.compareGeneratedReport(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGeneratedReport(entity1, entity2);
        const compareResult2 = service.compareGeneratedReport(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IParticipantsEunIndicator } from '../participants-eun-indicator.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../participants-eun-indicator.test-samples';

import { ParticipantsEunIndicatorService, RestParticipantsEunIndicator } from './participants-eun-indicator.service';

const requireRestSample: RestParticipantsEunIndicator = {
  ...sampleWithRequiredData,
  createdDate: sampleWithRequiredData.createdDate?.format(DATE_FORMAT),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.format(DATE_FORMAT),
};

describe('ParticipantsEunIndicator Service', () => {
  let service: ParticipantsEunIndicatorService;
  let httpMock: HttpTestingController;
  let expectedResult: IParticipantsEunIndicator | IParticipantsEunIndicator[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ParticipantsEunIndicatorService);
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

    it('should create a ParticipantsEunIndicator', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const participantsEunIndicator = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(participantsEunIndicator).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ParticipantsEunIndicator', () => {
      const participantsEunIndicator = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(participantsEunIndicator).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ParticipantsEunIndicator', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ParticipantsEunIndicator', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ParticipantsEunIndicator', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addParticipantsEunIndicatorToCollectionIfMissing', () => {
      it('should add a ParticipantsEunIndicator to an empty array', () => {
        const participantsEunIndicator: IParticipantsEunIndicator = sampleWithRequiredData;
        expectedResult = service.addParticipantsEunIndicatorToCollectionIfMissing([], participantsEunIndicator);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(participantsEunIndicator);
      });

      it('should not add a ParticipantsEunIndicator to an array that contains it', () => {
        const participantsEunIndicator: IParticipantsEunIndicator = sampleWithRequiredData;
        const participantsEunIndicatorCollection: IParticipantsEunIndicator[] = [
          {
            ...participantsEunIndicator,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addParticipantsEunIndicatorToCollectionIfMissing(
          participantsEunIndicatorCollection,
          participantsEunIndicator
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ParticipantsEunIndicator to an array that doesn't contain it", () => {
        const participantsEunIndicator: IParticipantsEunIndicator = sampleWithRequiredData;
        const participantsEunIndicatorCollection: IParticipantsEunIndicator[] = [sampleWithPartialData];
        expectedResult = service.addParticipantsEunIndicatorToCollectionIfMissing(
          participantsEunIndicatorCollection,
          participantsEunIndicator
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(participantsEunIndicator);
      });

      it('should add only unique ParticipantsEunIndicator to an array', () => {
        const participantsEunIndicatorArray: IParticipantsEunIndicator[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const participantsEunIndicatorCollection: IParticipantsEunIndicator[] = [sampleWithRequiredData];
        expectedResult = service.addParticipantsEunIndicatorToCollectionIfMissing(
          participantsEunIndicatorCollection,
          ...participantsEunIndicatorArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const participantsEunIndicator: IParticipantsEunIndicator = sampleWithRequiredData;
        const participantsEunIndicator2: IParticipantsEunIndicator = sampleWithPartialData;
        expectedResult = service.addParticipantsEunIndicatorToCollectionIfMissing([], participantsEunIndicator, participantsEunIndicator2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(participantsEunIndicator);
        expect(expectedResult).toContain(participantsEunIndicator2);
      });

      it('should accept null and undefined values', () => {
        const participantsEunIndicator: IParticipantsEunIndicator = sampleWithRequiredData;
        expectedResult = service.addParticipantsEunIndicatorToCollectionIfMissing([], null, participantsEunIndicator, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(participantsEunIndicator);
      });

      it('should return initial array if no ParticipantsEunIndicator is added', () => {
        const participantsEunIndicatorCollection: IParticipantsEunIndicator[] = [sampleWithRequiredData];
        expectedResult = service.addParticipantsEunIndicatorToCollectionIfMissing(participantsEunIndicatorCollection, undefined, null);
        expect(expectedResult).toEqual(participantsEunIndicatorCollection);
      });
    });

    describe('compareParticipantsEunIndicator', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareParticipantsEunIndicator(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareParticipantsEunIndicator(entity1, entity2);
        const compareResult2 = service.compareParticipantsEunIndicator(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareParticipantsEunIndicator(entity1, entity2);
        const compareResult2 = service.compareParticipantsEunIndicator(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareParticipantsEunIndicator(entity1, entity2);
        const compareResult2 = service.compareParticipantsEunIndicator(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

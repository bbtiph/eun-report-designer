import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IProjectPartner } from '../project-partner.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../project-partner.test-samples';

import { ProjectPartnerService, RestProjectPartner } from './project-partner.service';

const requireRestSample: RestProjectPartner = {
  ...sampleWithRequiredData,
  createdDate: sampleWithRequiredData.createdDate?.format(DATE_FORMAT),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.format(DATE_FORMAT),
};

describe('ProjectPartner Service', () => {
  let service: ProjectPartnerService;
  let httpMock: HttpTestingController;
  let expectedResult: IProjectPartner | IProjectPartner[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProjectPartnerService);
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

    it('should create a ProjectPartner', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const projectPartner = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(projectPartner).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProjectPartner', () => {
      const projectPartner = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(projectPartner).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProjectPartner', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProjectPartner', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProjectPartner', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProjectPartnerToCollectionIfMissing', () => {
      it('should add a ProjectPartner to an empty array', () => {
        const projectPartner: IProjectPartner = sampleWithRequiredData;
        expectedResult = service.addProjectPartnerToCollectionIfMissing([], projectPartner);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projectPartner);
      });

      it('should not add a ProjectPartner to an array that contains it', () => {
        const projectPartner: IProjectPartner = sampleWithRequiredData;
        const projectPartnerCollection: IProjectPartner[] = [
          {
            ...projectPartner,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProjectPartnerToCollectionIfMissing(projectPartnerCollection, projectPartner);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProjectPartner to an array that doesn't contain it", () => {
        const projectPartner: IProjectPartner = sampleWithRequiredData;
        const projectPartnerCollection: IProjectPartner[] = [sampleWithPartialData];
        expectedResult = service.addProjectPartnerToCollectionIfMissing(projectPartnerCollection, projectPartner);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projectPartner);
      });

      it('should add only unique ProjectPartner to an array', () => {
        const projectPartnerArray: IProjectPartner[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const projectPartnerCollection: IProjectPartner[] = [sampleWithRequiredData];
        expectedResult = service.addProjectPartnerToCollectionIfMissing(projectPartnerCollection, ...projectPartnerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const projectPartner: IProjectPartner = sampleWithRequiredData;
        const projectPartner2: IProjectPartner = sampleWithPartialData;
        expectedResult = service.addProjectPartnerToCollectionIfMissing([], projectPartner, projectPartner2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projectPartner);
        expect(expectedResult).toContain(projectPartner2);
      });

      it('should accept null and undefined values', () => {
        const projectPartner: IProjectPartner = sampleWithRequiredData;
        expectedResult = service.addProjectPartnerToCollectionIfMissing([], null, projectPartner, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projectPartner);
      });

      it('should return initial array if no ProjectPartner is added', () => {
        const projectPartnerCollection: IProjectPartner[] = [sampleWithRequiredData];
        expectedResult = service.addProjectPartnerToCollectionIfMissing(projectPartnerCollection, undefined, null);
        expect(expectedResult).toEqual(projectPartnerCollection);
      });
    });

    describe('compareProjectPartner', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProjectPartner(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProjectPartner(entity1, entity2);
        const compareResult2 = service.compareProjectPartner(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProjectPartner(entity1, entity2);
        const compareResult2 = service.compareProjectPartner(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProjectPartner(entity1, entity2);
        const compareResult2 = service.compareProjectPartner(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

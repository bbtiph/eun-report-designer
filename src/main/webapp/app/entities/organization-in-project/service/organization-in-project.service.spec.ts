import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IOrganizationInProject } from '../organization-in-project.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../organization-in-project.test-samples';

import { OrganizationInProjectService, RestOrganizationInProject } from './organization-in-project.service';

const requireRestSample: RestOrganizationInProject = {
  ...sampleWithRequiredData,
  joinDate: sampleWithRequiredData.joinDate?.format(DATE_FORMAT),
};

describe('OrganizationInProject Service', () => {
  let service: OrganizationInProjectService;
  let httpMock: HttpTestingController;
  let expectedResult: IOrganizationInProject | IOrganizationInProject[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrganizationInProjectService);
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

    it('should create a OrganizationInProject', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const organizationInProject = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(organizationInProject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrganizationInProject', () => {
      const organizationInProject = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(organizationInProject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrganizationInProject', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrganizationInProject', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OrganizationInProject', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOrganizationInProjectToCollectionIfMissing', () => {
      it('should add a OrganizationInProject to an empty array', () => {
        const organizationInProject: IOrganizationInProject = sampleWithRequiredData;
        expectedResult = service.addOrganizationInProjectToCollectionIfMissing([], organizationInProject);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organizationInProject);
      });

      it('should not add a OrganizationInProject to an array that contains it', () => {
        const organizationInProject: IOrganizationInProject = sampleWithRequiredData;
        const organizationInProjectCollection: IOrganizationInProject[] = [
          {
            ...organizationInProject,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOrganizationInProjectToCollectionIfMissing(organizationInProjectCollection, organizationInProject);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrganizationInProject to an array that doesn't contain it", () => {
        const organizationInProject: IOrganizationInProject = sampleWithRequiredData;
        const organizationInProjectCollection: IOrganizationInProject[] = [sampleWithPartialData];
        expectedResult = service.addOrganizationInProjectToCollectionIfMissing(organizationInProjectCollection, organizationInProject);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organizationInProject);
      });

      it('should add only unique OrganizationInProject to an array', () => {
        const organizationInProjectArray: IOrganizationInProject[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const organizationInProjectCollection: IOrganizationInProject[] = [sampleWithRequiredData];
        expectedResult = service.addOrganizationInProjectToCollectionIfMissing(
          organizationInProjectCollection,
          ...organizationInProjectArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const organizationInProject: IOrganizationInProject = sampleWithRequiredData;
        const organizationInProject2: IOrganizationInProject = sampleWithPartialData;
        expectedResult = service.addOrganizationInProjectToCollectionIfMissing([], organizationInProject, organizationInProject2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organizationInProject);
        expect(expectedResult).toContain(organizationInProject2);
      });

      it('should accept null and undefined values', () => {
        const organizationInProject: IOrganizationInProject = sampleWithRequiredData;
        expectedResult = service.addOrganizationInProjectToCollectionIfMissing([], null, organizationInProject, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organizationInProject);
      });

      it('should return initial array if no OrganizationInProject is added', () => {
        const organizationInProjectCollection: IOrganizationInProject[] = [sampleWithRequiredData];
        expectedResult = service.addOrganizationInProjectToCollectionIfMissing(organizationInProjectCollection, undefined, null);
        expect(expectedResult).toEqual(organizationInProjectCollection);
      });
    });

    describe('compareOrganizationInProject', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOrganizationInProject(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOrganizationInProject(entity1, entity2);
        const compareResult2 = service.compareOrganizationInProject(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareOrganizationInProject(entity1, entity2);
        const compareResult2 = service.compareOrganizationInProject(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareOrganizationInProject(entity1, entity2);
        const compareResult2 = service.compareOrganizationInProject(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

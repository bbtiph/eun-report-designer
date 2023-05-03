import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrganizationInProject, NewOrganizationInProject } from '../organization-in-project.model';

export type PartialUpdateOrganizationInProject = Partial<IOrganizationInProject> & Pick<IOrganizationInProject, 'id'>;

type RestOf<T extends IOrganizationInProject | NewOrganizationInProject> = Omit<T, 'joinDate'> & {
  joinDate?: string | null;
};

export type RestOrganizationInProject = RestOf<IOrganizationInProject>;

export type NewRestOrganizationInProject = RestOf<NewOrganizationInProject>;

export type PartialUpdateRestOrganizationInProject = RestOf<PartialUpdateOrganizationInProject>;

export type EntityResponseType = HttpResponse<IOrganizationInProject>;
export type EntityArrayResponseType = HttpResponse<IOrganizationInProject[]>;

@Injectable({ providedIn: 'root' })
export class OrganizationInProjectService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/organization-in-projects');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(organizationInProject: NewOrganizationInProject): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organizationInProject);
    return this.http
      .post<RestOrganizationInProject>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(organizationInProject: IOrganizationInProject): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organizationInProject);
    return this.http
      .put<RestOrganizationInProject>(`${this.resourceUrl}/${this.getOrganizationInProjectIdentifier(organizationInProject)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(organizationInProject: PartialUpdateOrganizationInProject): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organizationInProject);
    return this.http
      .patch<RestOrganizationInProject>(`${this.resourceUrl}/${this.getOrganizationInProjectIdentifier(organizationInProject)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestOrganizationInProject>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestOrganizationInProject[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrganizationInProjectIdentifier(organizationInProject: Pick<IOrganizationInProject, 'id'>): number {
    return organizationInProject.id;
  }

  compareOrganizationInProject(o1: Pick<IOrganizationInProject, 'id'> | null, o2: Pick<IOrganizationInProject, 'id'> | null): boolean {
    return o1 && o2 ? this.getOrganizationInProjectIdentifier(o1) === this.getOrganizationInProjectIdentifier(o2) : o1 === o2;
  }

  addOrganizationInProjectToCollectionIfMissing<Type extends Pick<IOrganizationInProject, 'id'>>(
    organizationInProjectCollection: Type[],
    ...organizationInProjectsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const organizationInProjects: Type[] = organizationInProjectsToCheck.filter(isPresent);
    if (organizationInProjects.length > 0) {
      const organizationInProjectCollectionIdentifiers = organizationInProjectCollection.map(
        organizationInProjectItem => this.getOrganizationInProjectIdentifier(organizationInProjectItem)!
      );
      const organizationInProjectsToAdd = organizationInProjects.filter(organizationInProjectItem => {
        const organizationInProjectIdentifier = this.getOrganizationInProjectIdentifier(organizationInProjectItem);
        if (organizationInProjectCollectionIdentifiers.includes(organizationInProjectIdentifier)) {
          return false;
        }
        organizationInProjectCollectionIdentifiers.push(organizationInProjectIdentifier);
        return true;
      });
      return [...organizationInProjectsToAdd, ...organizationInProjectCollection];
    }
    return organizationInProjectCollection;
  }

  protected convertDateFromClient<T extends IOrganizationInProject | NewOrganizationInProject | PartialUpdateOrganizationInProject>(
    organizationInProject: T
  ): RestOf<T> {
    return {
      ...organizationInProject,
      joinDate: organizationInProject.joinDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restOrganizationInProject: RestOrganizationInProject): IOrganizationInProject {
    return {
      ...restOrganizationInProject,
      joinDate: restOrganizationInProject.joinDate ? dayjs(restOrganizationInProject.joinDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestOrganizationInProject>): HttpResponse<IOrganizationInProject> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestOrganizationInProject[]>): HttpResponse<IOrganizationInProject[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

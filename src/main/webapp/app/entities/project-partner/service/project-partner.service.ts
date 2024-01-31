import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProjectPartner, NewProjectPartner } from '../project-partner.model';

export type PartialUpdateProjectPartner = Partial<IProjectPartner> & Pick<IProjectPartner, 'id'>;

type RestOf<T extends IProjectPartner | NewProjectPartner> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestProjectPartner = RestOf<IProjectPartner>;

export type NewRestProjectPartner = RestOf<NewProjectPartner>;

export type PartialUpdateRestProjectPartner = RestOf<PartialUpdateProjectPartner>;

export type EntityResponseType = HttpResponse<IProjectPartner>;
export type EntityArrayResponseType = HttpResponse<IProjectPartner[]>;

@Injectable({ providedIn: 'root' })
export class ProjectPartnerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/project-partners');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(projectPartner: NewProjectPartner): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(projectPartner);
    return this.http
      .post<RestProjectPartner>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(projectPartner: IProjectPartner): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(projectPartner);
    return this.http
      .put<RestProjectPartner>(`${this.resourceUrl}/${this.getProjectPartnerIdentifier(projectPartner)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(projectPartner: PartialUpdateProjectPartner): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(projectPartner);
    return this.http
      .patch<RestProjectPartner>(`${this.resourceUrl}/${this.getProjectPartnerIdentifier(projectPartner)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestProjectPartner>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProjectPartner[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProjectPartnerIdentifier(projectPartner: Pick<IProjectPartner, 'id'>): number {
    return projectPartner.id;
  }

  compareProjectPartner(o1: Pick<IProjectPartner, 'id'> | null, o2: Pick<IProjectPartner, 'id'> | null): boolean {
    return o1 && o2 ? this.getProjectPartnerIdentifier(o1) === this.getProjectPartnerIdentifier(o2) : o1 === o2;
  }

  addProjectPartnerToCollectionIfMissing<Type extends Pick<IProjectPartner, 'id'>>(
    projectPartnerCollection: Type[],
    ...projectPartnersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const projectPartners: Type[] = projectPartnersToCheck.filter(isPresent);
    if (projectPartners.length > 0) {
      const projectPartnerCollectionIdentifiers = projectPartnerCollection.map(
        projectPartnerItem => this.getProjectPartnerIdentifier(projectPartnerItem)!
      );
      const projectPartnersToAdd = projectPartners.filter(projectPartnerItem => {
        const projectPartnerIdentifier = this.getProjectPartnerIdentifier(projectPartnerItem);
        if (projectPartnerCollectionIdentifiers.includes(projectPartnerIdentifier)) {
          return false;
        }
        projectPartnerCollectionIdentifiers.push(projectPartnerIdentifier);
        return true;
      });
      return [...projectPartnersToAdd, ...projectPartnerCollection];
    }
    return projectPartnerCollection;
  }

  protected convertDateFromClient<T extends IProjectPartner | NewProjectPartner | PartialUpdateProjectPartner>(
    projectPartner: T
  ): RestOf<T> {
    return {
      ...projectPartner,
      createdDate: projectPartner.createdDate?.format(DATE_FORMAT) ?? null,
      lastModifiedDate: projectPartner.lastModifiedDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restProjectPartner: RestProjectPartner): IProjectPartner {
    return {
      ...restProjectPartner,
      createdDate: restProjectPartner.createdDate ? dayjs(restProjectPartner.createdDate) : undefined,
      lastModifiedDate: restProjectPartner.lastModifiedDate ? dayjs(restProjectPartner.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestProjectPartner>): HttpResponse<IProjectPartner> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestProjectPartner[]>): HttpResponse<IProjectPartner[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

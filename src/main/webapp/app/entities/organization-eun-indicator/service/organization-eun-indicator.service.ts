import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrganizationEunIndicator, NewOrganizationEunIndicator } from '../organization-eun-indicator.model';

export type PartialUpdateOrganizationEunIndicator = Partial<IOrganizationEunIndicator> & Pick<IOrganizationEunIndicator, 'id'>;

type RestOf<T extends IOrganizationEunIndicator | NewOrganizationEunIndicator> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestOrganizationEunIndicator = RestOf<IOrganizationEunIndicator>;

export type NewRestOrganizationEunIndicator = RestOf<NewOrganizationEunIndicator>;

export type PartialUpdateRestOrganizationEunIndicator = RestOf<PartialUpdateOrganizationEunIndicator>;

export type EntityResponseType = HttpResponse<IOrganizationEunIndicator>;
export type EntityArrayResponseType = HttpResponse<IOrganizationEunIndicator[]>;

@Injectable({ providedIn: 'root' })
export class OrganizationEunIndicatorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/organization-eun-indicators');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(organizationEunIndicator: NewOrganizationEunIndicator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organizationEunIndicator);
    return this.http
      .post<RestOrganizationEunIndicator>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(organizationEunIndicator: IOrganizationEunIndicator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organizationEunIndicator);
    return this.http
      .put<RestOrganizationEunIndicator>(
        `${this.resourceUrl}/${this.getOrganizationEunIndicatorIdentifier(organizationEunIndicator)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(organizationEunIndicator: PartialUpdateOrganizationEunIndicator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organizationEunIndicator);
    return this.http
      .patch<RestOrganizationEunIndicator>(
        `${this.resourceUrl}/${this.getOrganizationEunIndicatorIdentifier(organizationEunIndicator)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestOrganizationEunIndicator>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestOrganizationEunIndicator[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrganizationEunIndicatorIdentifier(organizationEunIndicator: Pick<IOrganizationEunIndicator, 'id'>): number {
    return organizationEunIndicator.id;
  }

  compareOrganizationEunIndicator(
    o1: Pick<IOrganizationEunIndicator, 'id'> | null,
    o2: Pick<IOrganizationEunIndicator, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getOrganizationEunIndicatorIdentifier(o1) === this.getOrganizationEunIndicatorIdentifier(o2) : o1 === o2;
  }

  addOrganizationEunIndicatorToCollectionIfMissing<Type extends Pick<IOrganizationEunIndicator, 'id'>>(
    organizationEunIndicatorCollection: Type[],
    ...organizationEunIndicatorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const organizationEunIndicators: Type[] = organizationEunIndicatorsToCheck.filter(isPresent);
    if (organizationEunIndicators.length > 0) {
      const organizationEunIndicatorCollectionIdentifiers = organizationEunIndicatorCollection.map(
        organizationEunIndicatorItem => this.getOrganizationEunIndicatorIdentifier(organizationEunIndicatorItem)!
      );
      const organizationEunIndicatorsToAdd = organizationEunIndicators.filter(organizationEunIndicatorItem => {
        const organizationEunIndicatorIdentifier = this.getOrganizationEunIndicatorIdentifier(organizationEunIndicatorItem);
        if (organizationEunIndicatorCollectionIdentifiers.includes(organizationEunIndicatorIdentifier)) {
          return false;
        }
        organizationEunIndicatorCollectionIdentifiers.push(organizationEunIndicatorIdentifier);
        return true;
      });
      return [...organizationEunIndicatorsToAdd, ...organizationEunIndicatorCollection];
    }
    return organizationEunIndicatorCollection;
  }

  protected convertDateFromClient<
    T extends IOrganizationEunIndicator | NewOrganizationEunIndicator | PartialUpdateOrganizationEunIndicator
  >(organizationEunIndicator: T): RestOf<T> {
    return {
      ...organizationEunIndicator,
      createdDate: organizationEunIndicator.createdDate?.format(DATE_FORMAT) ?? null,
      lastModifiedDate: organizationEunIndicator.lastModifiedDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restOrganizationEunIndicator: RestOrganizationEunIndicator): IOrganizationEunIndicator {
    return {
      ...restOrganizationEunIndicator,
      createdDate: restOrganizationEunIndicator.createdDate ? dayjs(restOrganizationEunIndicator.createdDate) : undefined,
      lastModifiedDate: restOrganizationEunIndicator.lastModifiedDate ? dayjs(restOrganizationEunIndicator.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestOrganizationEunIndicator>): HttpResponse<IOrganizationEunIndicator> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestOrganizationEunIndicator[]>): HttpResponse<IOrganizationEunIndicator[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

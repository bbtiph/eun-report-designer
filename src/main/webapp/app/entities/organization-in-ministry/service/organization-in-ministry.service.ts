import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrganizationInMinistry, NewOrganizationInMinistry } from '../organization-in-ministry.model';

export type PartialUpdateOrganizationInMinistry = Partial<IOrganizationInMinistry> & Pick<IOrganizationInMinistry, 'id'>;

export type EntityResponseType = HttpResponse<IOrganizationInMinistry>;
export type EntityArrayResponseType = HttpResponse<IOrganizationInMinistry[]>;

@Injectable({ providedIn: 'root' })
export class OrganizationInMinistryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/organization-in-ministries');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(organizationInMinistry: NewOrganizationInMinistry): Observable<EntityResponseType> {
    return this.http.post<IOrganizationInMinistry>(this.resourceUrl, organizationInMinistry, { observe: 'response' });
  }

  update(organizationInMinistry: IOrganizationInMinistry): Observable<EntityResponseType> {
    return this.http.put<IOrganizationInMinistry>(
      `${this.resourceUrl}/${this.getOrganizationInMinistryIdentifier(organizationInMinistry)}`,
      organizationInMinistry,
      { observe: 'response' }
    );
  }

  partialUpdate(organizationInMinistry: PartialUpdateOrganizationInMinistry): Observable<EntityResponseType> {
    return this.http.patch<IOrganizationInMinistry>(
      `${this.resourceUrl}/${this.getOrganizationInMinistryIdentifier(organizationInMinistry)}`,
      organizationInMinistry,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrganizationInMinistry>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganizationInMinistry[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrganizationInMinistryIdentifier(organizationInMinistry: Pick<IOrganizationInMinistry, 'id'>): number {
    return organizationInMinistry.id;
  }

  compareOrganizationInMinistry(o1: Pick<IOrganizationInMinistry, 'id'> | null, o2: Pick<IOrganizationInMinistry, 'id'> | null): boolean {
    return o1 && o2 ? this.getOrganizationInMinistryIdentifier(o1) === this.getOrganizationInMinistryIdentifier(o2) : o1 === o2;
  }

  addOrganizationInMinistryToCollectionIfMissing<Type extends Pick<IOrganizationInMinistry, 'id'>>(
    organizationInMinistryCollection: Type[],
    ...organizationInMinistriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const organizationInMinistries: Type[] = organizationInMinistriesToCheck.filter(isPresent);
    if (organizationInMinistries.length > 0) {
      const organizationInMinistryCollectionIdentifiers = organizationInMinistryCollection.map(
        organizationInMinistryItem => this.getOrganizationInMinistryIdentifier(organizationInMinistryItem)!
      );
      const organizationInMinistriesToAdd = organizationInMinistries.filter(organizationInMinistryItem => {
        const organizationInMinistryIdentifier = this.getOrganizationInMinistryIdentifier(organizationInMinistryItem);
        if (organizationInMinistryCollectionIdentifiers.includes(organizationInMinistryIdentifier)) {
          return false;
        }
        organizationInMinistryCollectionIdentifiers.push(organizationInMinistryIdentifier);
        return true;
      });
      return [...organizationInMinistriesToAdd, ...organizationInMinistryCollection];
    }
    return organizationInMinistryCollection;
  }
}

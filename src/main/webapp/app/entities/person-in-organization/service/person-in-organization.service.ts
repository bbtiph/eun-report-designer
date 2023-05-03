import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPersonInOrganization, NewPersonInOrganization } from '../person-in-organization.model';

export type PartialUpdatePersonInOrganization = Partial<IPersonInOrganization> & Pick<IPersonInOrganization, 'id'>;

export type EntityResponseType = HttpResponse<IPersonInOrganization>;
export type EntityArrayResponseType = HttpResponse<IPersonInOrganization[]>;

@Injectable({ providedIn: 'root' })
export class PersonInOrganizationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/person-in-organizations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(personInOrganization: NewPersonInOrganization): Observable<EntityResponseType> {
    return this.http.post<IPersonInOrganization>(this.resourceUrl, personInOrganization, { observe: 'response' });
  }

  update(personInOrganization: IPersonInOrganization): Observable<EntityResponseType> {
    return this.http.put<IPersonInOrganization>(
      `${this.resourceUrl}/${this.getPersonInOrganizationIdentifier(personInOrganization)}`,
      personInOrganization,
      { observe: 'response' }
    );
  }

  partialUpdate(personInOrganization: PartialUpdatePersonInOrganization): Observable<EntityResponseType> {
    return this.http.patch<IPersonInOrganization>(
      `${this.resourceUrl}/${this.getPersonInOrganizationIdentifier(personInOrganization)}`,
      personInOrganization,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPersonInOrganization>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonInOrganization[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPersonInOrganizationIdentifier(personInOrganization: Pick<IPersonInOrganization, 'id'>): number {
    return personInOrganization.id;
  }

  comparePersonInOrganization(o1: Pick<IPersonInOrganization, 'id'> | null, o2: Pick<IPersonInOrganization, 'id'> | null): boolean {
    return o1 && o2 ? this.getPersonInOrganizationIdentifier(o1) === this.getPersonInOrganizationIdentifier(o2) : o1 === o2;
  }

  addPersonInOrganizationToCollectionIfMissing<Type extends Pick<IPersonInOrganization, 'id'>>(
    personInOrganizationCollection: Type[],
    ...personInOrganizationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const personInOrganizations: Type[] = personInOrganizationsToCheck.filter(isPresent);
    if (personInOrganizations.length > 0) {
      const personInOrganizationCollectionIdentifiers = personInOrganizationCollection.map(
        personInOrganizationItem => this.getPersonInOrganizationIdentifier(personInOrganizationItem)!
      );
      const personInOrganizationsToAdd = personInOrganizations.filter(personInOrganizationItem => {
        const personInOrganizationIdentifier = this.getPersonInOrganizationIdentifier(personInOrganizationItem);
        if (personInOrganizationCollectionIdentifiers.includes(personInOrganizationIdentifier)) {
          return false;
        }
        personInOrganizationCollectionIdentifiers.push(personInOrganizationIdentifier);
        return true;
      });
      return [...personInOrganizationsToAdd, ...personInOrganizationCollection];
    }
    return personInOrganizationCollection;
  }
}

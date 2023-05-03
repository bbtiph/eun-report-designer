import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEventInOrganization, NewEventInOrganization } from '../event-in-organization.model';

export type PartialUpdateEventInOrganization = Partial<IEventInOrganization> & Pick<IEventInOrganization, 'id'>;

export type EntityResponseType = HttpResponse<IEventInOrganization>;
export type EntityArrayResponseType = HttpResponse<IEventInOrganization[]>;

@Injectable({ providedIn: 'root' })
export class EventInOrganizationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/event-in-organizations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eventInOrganization: NewEventInOrganization): Observable<EntityResponseType> {
    return this.http.post<IEventInOrganization>(this.resourceUrl, eventInOrganization, { observe: 'response' });
  }

  update(eventInOrganization: IEventInOrganization): Observable<EntityResponseType> {
    return this.http.put<IEventInOrganization>(
      `${this.resourceUrl}/${this.getEventInOrganizationIdentifier(eventInOrganization)}`,
      eventInOrganization,
      { observe: 'response' }
    );
  }

  partialUpdate(eventInOrganization: PartialUpdateEventInOrganization): Observable<EntityResponseType> {
    return this.http.patch<IEventInOrganization>(
      `${this.resourceUrl}/${this.getEventInOrganizationIdentifier(eventInOrganization)}`,
      eventInOrganization,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEventInOrganization>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEventInOrganization[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEventInOrganizationIdentifier(eventInOrganization: Pick<IEventInOrganization, 'id'>): number {
    return eventInOrganization.id;
  }

  compareEventInOrganization(o1: Pick<IEventInOrganization, 'id'> | null, o2: Pick<IEventInOrganization, 'id'> | null): boolean {
    return o1 && o2 ? this.getEventInOrganizationIdentifier(o1) === this.getEventInOrganizationIdentifier(o2) : o1 === o2;
  }

  addEventInOrganizationToCollectionIfMissing<Type extends Pick<IEventInOrganization, 'id'>>(
    eventInOrganizationCollection: Type[],
    ...eventInOrganizationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const eventInOrganizations: Type[] = eventInOrganizationsToCheck.filter(isPresent);
    if (eventInOrganizations.length > 0) {
      const eventInOrganizationCollectionIdentifiers = eventInOrganizationCollection.map(
        eventInOrganizationItem => this.getEventInOrganizationIdentifier(eventInOrganizationItem)!
      );
      const eventInOrganizationsToAdd = eventInOrganizations.filter(eventInOrganizationItem => {
        const eventInOrganizationIdentifier = this.getEventInOrganizationIdentifier(eventInOrganizationItem);
        if (eventInOrganizationCollectionIdentifiers.includes(eventInOrganizationIdentifier)) {
          return false;
        }
        eventInOrganizationCollectionIdentifiers.push(eventInOrganizationIdentifier);
        return true;
      });
      return [...eventInOrganizationsToAdd, ...eventInOrganizationCollection];
    }
    return eventInOrganizationCollection;
  }
}

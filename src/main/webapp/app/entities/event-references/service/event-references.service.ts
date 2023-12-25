import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEventReferences, NewEventReferences } from '../event-references.model';

export type PartialUpdateEventReferences = Partial<IEventReferences> & Pick<IEventReferences, 'id'>;

export type EntityResponseType = HttpResponse<IEventReferences>;
export type EntityArrayResponseType = HttpResponse<IEventReferences[]>;

@Injectable({ providedIn: 'root' })
export class EventReferencesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/event-references');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eventReferences: NewEventReferences): Observable<EntityResponseType> {
    return this.http.post<IEventReferences>(this.resourceUrl, eventReferences, { observe: 'response' });
  }

  update(eventReferences: IEventReferences): Observable<EntityResponseType> {
    return this.http.put<IEventReferences>(`${this.resourceUrl}/${this.getEventReferencesIdentifier(eventReferences)}`, eventReferences, {
      observe: 'response',
    });
  }

  partialUpdate(eventReferences: PartialUpdateEventReferences): Observable<EntityResponseType> {
    return this.http.patch<IEventReferences>(`${this.resourceUrl}/${this.getEventReferencesIdentifier(eventReferences)}`, eventReferences, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEventReferences>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEventReferences[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEventReferencesIdentifier(eventReferences: Pick<IEventReferences, 'id'>): number {
    return eventReferences.id;
  }

  compareEventReferences(o1: Pick<IEventReferences, 'id'> | null, o2: Pick<IEventReferences, 'id'> | null): boolean {
    return o1 && o2 ? this.getEventReferencesIdentifier(o1) === this.getEventReferencesIdentifier(o2) : o1 === o2;
  }

  addEventReferencesToCollectionIfMissing<Type extends Pick<IEventReferences, 'id'>>(
    eventReferencesCollection: Type[],
    ...eventReferencesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const eventReferences: Type[] = eventReferencesToCheck.filter(isPresent);
    if (eventReferences.length > 0) {
      const eventReferencesCollectionIdentifiers = eventReferencesCollection.map(
        eventReferencesItem => this.getEventReferencesIdentifier(eventReferencesItem)!
      );
      const eventReferencesToAdd = eventReferences.filter(eventReferencesItem => {
        const eventReferencesIdentifier = this.getEventReferencesIdentifier(eventReferencesItem);
        if (eventReferencesCollectionIdentifiers.includes(eventReferencesIdentifier)) {
          return false;
        }
        eventReferencesCollectionIdentifiers.push(eventReferencesIdentifier);
        return true;
      });
      return [...eventReferencesToAdd, ...eventReferencesCollection];
    }
    return eventReferencesCollection;
  }
}

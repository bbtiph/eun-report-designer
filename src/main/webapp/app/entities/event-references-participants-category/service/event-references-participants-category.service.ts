import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import {
  IEventReferencesParticipantsCategory,
  NewEventReferencesParticipantsCategory,
} from '../event-references-participants-category.model';

export type PartialUpdateEventReferencesParticipantsCategory = Partial<IEventReferencesParticipantsCategory> &
  Pick<IEventReferencesParticipantsCategory, 'id'>;

export type EntityResponseType = HttpResponse<IEventReferencesParticipantsCategory>;
export type EntityArrayResponseType = HttpResponse<IEventReferencesParticipantsCategory[]>;

@Injectable({ providedIn: 'root' })
export class EventReferencesParticipantsCategoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/event-references-participants-categories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eventReferencesParticipantsCategory: NewEventReferencesParticipantsCategory): Observable<EntityResponseType> {
    return this.http.post<IEventReferencesParticipantsCategory>(this.resourceUrl, eventReferencesParticipantsCategory, {
      observe: 'response',
    });
  }

  update(eventReferencesParticipantsCategory: IEventReferencesParticipantsCategory): Observable<EntityResponseType> {
    return this.http.put<IEventReferencesParticipantsCategory>(
      `${this.resourceUrl}/${this.getEventReferencesParticipantsCategoryIdentifier(eventReferencesParticipantsCategory)}`,
      eventReferencesParticipantsCategory,
      { observe: 'response' }
    );
  }

  partialUpdate(eventReferencesParticipantsCategory: PartialUpdateEventReferencesParticipantsCategory): Observable<EntityResponseType> {
    return this.http.patch<IEventReferencesParticipantsCategory>(
      `${this.resourceUrl}/${this.getEventReferencesParticipantsCategoryIdentifier(eventReferencesParticipantsCategory)}`,
      eventReferencesParticipantsCategory,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEventReferencesParticipantsCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEventReferencesParticipantsCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEventReferencesParticipantsCategoryIdentifier(
    eventReferencesParticipantsCategory: Pick<IEventReferencesParticipantsCategory, 'id'>
  ): number {
    return eventReferencesParticipantsCategory.id;
  }

  compareEventReferencesParticipantsCategory(
    o1: Pick<IEventReferencesParticipantsCategory, 'id'> | null,
    o2: Pick<IEventReferencesParticipantsCategory, 'id'> | null
  ): boolean {
    return o1 && o2
      ? this.getEventReferencesParticipantsCategoryIdentifier(o1) === this.getEventReferencesParticipantsCategoryIdentifier(o2)
      : o1 === o2;
  }

  addEventReferencesParticipantsCategoryToCollectionIfMissing<Type extends Pick<IEventReferencesParticipantsCategory, 'id'>>(
    eventReferencesParticipantsCategoryCollection: Type[],
    ...eventReferencesParticipantsCategoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const eventReferencesParticipantsCategories: Type[] = eventReferencesParticipantsCategoriesToCheck.filter(isPresent);
    if (eventReferencesParticipantsCategories.length > 0) {
      const eventReferencesParticipantsCategoryCollectionIdentifiers = eventReferencesParticipantsCategoryCollection.map(
        eventReferencesParticipantsCategoryItem =>
          this.getEventReferencesParticipantsCategoryIdentifier(eventReferencesParticipantsCategoryItem)!
      );
      const eventReferencesParticipantsCategoriesToAdd = eventReferencesParticipantsCategories.filter(
        eventReferencesParticipantsCategoryItem => {
          const eventReferencesParticipantsCategoryIdentifier = this.getEventReferencesParticipantsCategoryIdentifier(
            eventReferencesParticipantsCategoryItem
          );
          if (eventReferencesParticipantsCategoryCollectionIdentifiers.includes(eventReferencesParticipantsCategoryIdentifier)) {
            return false;
          }
          eventReferencesParticipantsCategoryCollectionIdentifiers.push(eventReferencesParticipantsCategoryIdentifier);
          return true;
        }
      );
      return [...eventReferencesParticipantsCategoriesToAdd, ...eventReferencesParticipantsCategoryCollection];
    }
    return eventReferencesParticipantsCategoryCollection;
  }
}

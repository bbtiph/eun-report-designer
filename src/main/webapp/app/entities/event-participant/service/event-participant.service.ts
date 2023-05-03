import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEventParticipant, NewEventParticipant } from '../event-participant.model';

export type PartialUpdateEventParticipant = Partial<IEventParticipant> & Pick<IEventParticipant, 'id'>;

export type EntityResponseType = HttpResponse<IEventParticipant>;
export type EntityArrayResponseType = HttpResponse<IEventParticipant[]>;

@Injectable({ providedIn: 'root' })
export class EventParticipantService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/event-participants');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eventParticipant: NewEventParticipant): Observable<EntityResponseType> {
    return this.http.post<IEventParticipant>(this.resourceUrl, eventParticipant, { observe: 'response' });
  }

  update(eventParticipant: IEventParticipant): Observable<EntityResponseType> {
    return this.http.put<IEventParticipant>(
      `${this.resourceUrl}/${this.getEventParticipantIdentifier(eventParticipant)}`,
      eventParticipant,
      { observe: 'response' }
    );
  }

  partialUpdate(eventParticipant: PartialUpdateEventParticipant): Observable<EntityResponseType> {
    return this.http.patch<IEventParticipant>(
      `${this.resourceUrl}/${this.getEventParticipantIdentifier(eventParticipant)}`,
      eventParticipant,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEventParticipant>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEventParticipant[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEventParticipantIdentifier(eventParticipant: Pick<IEventParticipant, 'id'>): number {
    return eventParticipant.id;
  }

  compareEventParticipant(o1: Pick<IEventParticipant, 'id'> | null, o2: Pick<IEventParticipant, 'id'> | null): boolean {
    return o1 && o2 ? this.getEventParticipantIdentifier(o1) === this.getEventParticipantIdentifier(o2) : o1 === o2;
  }

  addEventParticipantToCollectionIfMissing<Type extends Pick<IEventParticipant, 'id'>>(
    eventParticipantCollection: Type[],
    ...eventParticipantsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const eventParticipants: Type[] = eventParticipantsToCheck.filter(isPresent);
    if (eventParticipants.length > 0) {
      const eventParticipantCollectionIdentifiers = eventParticipantCollection.map(
        eventParticipantItem => this.getEventParticipantIdentifier(eventParticipantItem)!
      );
      const eventParticipantsToAdd = eventParticipants.filter(eventParticipantItem => {
        const eventParticipantIdentifier = this.getEventParticipantIdentifier(eventParticipantItem);
        if (eventParticipantCollectionIdentifiers.includes(eventParticipantIdentifier)) {
          return false;
        }
        eventParticipantCollectionIdentifiers.push(eventParticipantIdentifier);
        return true;
      });
      return [...eventParticipantsToAdd, ...eventParticipantCollection];
    }
    return eventParticipantCollection;
  }
}

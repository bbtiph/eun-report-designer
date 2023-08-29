import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParticipantsEunIndicator, NewParticipantsEunIndicator } from '../participants-eun-indicator.model';

export type PartialUpdateParticipantsEunIndicator = Partial<IParticipantsEunIndicator> & Pick<IParticipantsEunIndicator, 'id'>;

type RestOf<T extends IParticipantsEunIndicator | NewParticipantsEunIndicator> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestParticipantsEunIndicator = RestOf<IParticipantsEunIndicator>;

export type NewRestParticipantsEunIndicator = RestOf<NewParticipantsEunIndicator>;

export type PartialUpdateRestParticipantsEunIndicator = RestOf<PartialUpdateParticipantsEunIndicator>;

export type EntityResponseType = HttpResponse<IParticipantsEunIndicator>;
export type EntityArrayResponseType = HttpResponse<IParticipantsEunIndicator[]>;

@Injectable({ providedIn: 'root' })
export class ParticipantsEunIndicatorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/participants-eun-indicators');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(participantsEunIndicator: NewParticipantsEunIndicator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(participantsEunIndicator);
    return this.http
      .post<RestParticipantsEunIndicator>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(participantsEunIndicator: IParticipantsEunIndicator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(participantsEunIndicator);
    return this.http
      .put<RestParticipantsEunIndicator>(
        `${this.resourceUrl}/${this.getParticipantsEunIndicatorIdentifier(participantsEunIndicator)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(participantsEunIndicator: PartialUpdateParticipantsEunIndicator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(participantsEunIndicator);
    return this.http
      .patch<RestParticipantsEunIndicator>(
        `${this.resourceUrl}/${this.getParticipantsEunIndicatorIdentifier(participantsEunIndicator)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestParticipantsEunIndicator>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestParticipantsEunIndicator[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getParticipantsEunIndicatorIdentifier(participantsEunIndicator: Pick<IParticipantsEunIndicator, 'id'>): number {
    return participantsEunIndicator.id;
  }

  compareParticipantsEunIndicator(
    o1: Pick<IParticipantsEunIndicator, 'id'> | null,
    o2: Pick<IParticipantsEunIndicator, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getParticipantsEunIndicatorIdentifier(o1) === this.getParticipantsEunIndicatorIdentifier(o2) : o1 === o2;
  }

  addParticipantsEunIndicatorToCollectionIfMissing<Type extends Pick<IParticipantsEunIndicator, 'id'>>(
    participantsEunIndicatorCollection: Type[],
    ...participantsEunIndicatorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const participantsEunIndicators: Type[] = participantsEunIndicatorsToCheck.filter(isPresent);
    if (participantsEunIndicators.length > 0) {
      const participantsEunIndicatorCollectionIdentifiers = participantsEunIndicatorCollection.map(
        participantsEunIndicatorItem => this.getParticipantsEunIndicatorIdentifier(participantsEunIndicatorItem)!
      );
      const participantsEunIndicatorsToAdd = participantsEunIndicators.filter(participantsEunIndicatorItem => {
        const participantsEunIndicatorIdentifier = this.getParticipantsEunIndicatorIdentifier(participantsEunIndicatorItem);
        if (participantsEunIndicatorCollectionIdentifiers.includes(participantsEunIndicatorIdentifier)) {
          return false;
        }
        participantsEunIndicatorCollectionIdentifiers.push(participantsEunIndicatorIdentifier);
        return true;
      });
      return [...participantsEunIndicatorsToAdd, ...participantsEunIndicatorCollection];
    }
    return participantsEunIndicatorCollection;
  }

  protected convertDateFromClient<
    T extends IParticipantsEunIndicator | NewParticipantsEunIndicator | PartialUpdateParticipantsEunIndicator
  >(participantsEunIndicator: T): RestOf<T> {
    return {
      ...participantsEunIndicator,
      createdDate: participantsEunIndicator.createdDate?.format(DATE_FORMAT) ?? null,
      lastModifiedDate: participantsEunIndicator.lastModifiedDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restParticipantsEunIndicator: RestParticipantsEunIndicator): IParticipantsEunIndicator {
    return {
      ...restParticipantsEunIndicator,
      createdDate: restParticipantsEunIndicator.createdDate ? dayjs(restParticipantsEunIndicator.createdDate) : undefined,
      lastModifiedDate: restParticipantsEunIndicator.lastModifiedDate ? dayjs(restParticipantsEunIndicator.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestParticipantsEunIndicator>): HttpResponse<IParticipantsEunIndicator> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestParticipantsEunIndicator[]>): HttpResponse<IParticipantsEunIndicator[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

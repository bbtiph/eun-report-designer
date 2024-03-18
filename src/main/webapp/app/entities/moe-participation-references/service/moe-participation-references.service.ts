import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMOEParticipationReferences, NewMOEParticipationReferences } from '../moe-participation-references.model';

export type PartialUpdateMOEParticipationReferences = Partial<IMOEParticipationReferences> & Pick<IMOEParticipationReferences, 'id'>;

type RestOf<T extends IMOEParticipationReferences | NewMOEParticipationReferences> = Omit<
  T,
  'startDate' | 'endDate' | 'createdDate' | 'lastModifiedDate'
> & {
  startDate?: string | null;
  endDate?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestMOEParticipationReferences = RestOf<IMOEParticipationReferences>;

export type NewRestMOEParticipationReferences = RestOf<NewMOEParticipationReferences>;

export type PartialUpdateRestMOEParticipationReferences = RestOf<PartialUpdateMOEParticipationReferences>;

export type EntityResponseType = HttpResponse<IMOEParticipationReferences>;
export type EntityArrayResponseType = HttpResponse<IMOEParticipationReferences[]>;

@Injectable({ providedIn: 'root' })
export class MOEParticipationReferencesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/moe-participation-references');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mOEParticipationReferences: NewMOEParticipationReferences): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mOEParticipationReferences);
    return this.http
      .post<RestMOEParticipationReferences>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(mOEParticipationReferences: IMOEParticipationReferences): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mOEParticipationReferences);
    return this.http
      .put<RestMOEParticipationReferences>(
        `${this.resourceUrl}/${this.getMOEParticipationReferencesIdentifier(mOEParticipationReferences)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(mOEParticipationReferences: PartialUpdateMOEParticipationReferences): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mOEParticipationReferences);
    return this.http
      .patch<RestMOEParticipationReferences>(
        `${this.resourceUrl}/${this.getMOEParticipationReferencesIdentifier(mOEParticipationReferences)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMOEParticipationReferences>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMOEParticipationReferences[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMOEParticipationReferencesIdentifier(mOEParticipationReferences: Pick<IMOEParticipationReferences, 'id'>): number {
    return mOEParticipationReferences.id;
  }

  compareMOEParticipationReferences(
    o1: Pick<IMOEParticipationReferences, 'id'> | null,
    o2: Pick<IMOEParticipationReferences, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getMOEParticipationReferencesIdentifier(o1) === this.getMOEParticipationReferencesIdentifier(o2) : o1 === o2;
  }

  addMOEParticipationReferencesToCollectionIfMissing<Type extends Pick<IMOEParticipationReferences, 'id'>>(
    mOEParticipationReferencesCollection: Type[],
    ...mOEParticipationReferencesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const mOEParticipationReferences: Type[] = mOEParticipationReferencesToCheck.filter(isPresent);
    if (mOEParticipationReferences.length > 0) {
      const mOEParticipationReferencesCollectionIdentifiers = mOEParticipationReferencesCollection.map(
        mOEParticipationReferencesItem => this.getMOEParticipationReferencesIdentifier(mOEParticipationReferencesItem)!
      );
      const mOEParticipationReferencesToAdd = mOEParticipationReferences.filter(mOEParticipationReferencesItem => {
        const mOEParticipationReferencesIdentifier = this.getMOEParticipationReferencesIdentifier(mOEParticipationReferencesItem);
        if (mOEParticipationReferencesCollectionIdentifiers.includes(mOEParticipationReferencesIdentifier)) {
          return false;
        }
        mOEParticipationReferencesCollectionIdentifiers.push(mOEParticipationReferencesIdentifier);
        return true;
      });
      return [...mOEParticipationReferencesToAdd, ...mOEParticipationReferencesCollection];
    }
    return mOEParticipationReferencesCollection;
  }

  protected convertDateFromClient<
    T extends IMOEParticipationReferences | NewMOEParticipationReferences | PartialUpdateMOEParticipationReferences
  >(mOEParticipationReferences: T): RestOf<T> {
    return {
      ...mOEParticipationReferences,
      startDate: mOEParticipationReferences.startDate?.format(DATE_FORMAT) ?? null,
      endDate: mOEParticipationReferences.endDate?.format(DATE_FORMAT) ?? null,
      createdDate: mOEParticipationReferences.createdDate?.format(DATE_FORMAT) ?? null,
      lastModifiedDate: mOEParticipationReferences.lastModifiedDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restMOEParticipationReferences: RestMOEParticipationReferences): IMOEParticipationReferences {
    return {
      ...restMOEParticipationReferences,
      startDate: restMOEParticipationReferences.startDate ? dayjs(restMOEParticipationReferences.startDate) : undefined,
      endDate: restMOEParticipationReferences.endDate ? dayjs(restMOEParticipationReferences.endDate) : undefined,
      createdDate: restMOEParticipationReferences.createdDate ? dayjs(restMOEParticipationReferences.createdDate) : undefined,
      lastModifiedDate: restMOEParticipationReferences.lastModifiedDate
        ? dayjs(restMOEParticipationReferences.lastModifiedDate)
        : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMOEParticipationReferences>): HttpResponse<IMOEParticipationReferences> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestMOEParticipationReferences[]>
  ): HttpResponse<IMOEParticipationReferences[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

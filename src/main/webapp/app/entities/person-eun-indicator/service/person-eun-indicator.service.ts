import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPersonEunIndicator, NewPersonEunIndicator } from '../person-eun-indicator.model';

export type PartialUpdatePersonEunIndicator = Partial<IPersonEunIndicator> & Pick<IPersonEunIndicator, 'id'>;

type RestOf<T extends IPersonEunIndicator | NewPersonEunIndicator> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestPersonEunIndicator = RestOf<IPersonEunIndicator>;

export type NewRestPersonEunIndicator = RestOf<NewPersonEunIndicator>;

export type PartialUpdateRestPersonEunIndicator = RestOf<PartialUpdatePersonEunIndicator>;

export type EntityResponseType = HttpResponse<IPersonEunIndicator>;
export type EntityArrayResponseType = HttpResponse<IPersonEunIndicator[]>;

@Injectable({ providedIn: 'root' })
export class PersonEunIndicatorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/person-eun-indicators');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(personEunIndicator: NewPersonEunIndicator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personEunIndicator);
    return this.http
      .post<RestPersonEunIndicator>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(personEunIndicator: IPersonEunIndicator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personEunIndicator);
    return this.http
      .put<RestPersonEunIndicator>(`${this.resourceUrl}/${this.getPersonEunIndicatorIdentifier(personEunIndicator)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(personEunIndicator: PartialUpdatePersonEunIndicator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personEunIndicator);
    return this.http
      .patch<RestPersonEunIndicator>(`${this.resourceUrl}/${this.getPersonEunIndicatorIdentifier(personEunIndicator)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPersonEunIndicator>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPersonEunIndicator[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPersonEunIndicatorIdentifier(personEunIndicator: Pick<IPersonEunIndicator, 'id'>): number {
    return personEunIndicator.id;
  }

  comparePersonEunIndicator(o1: Pick<IPersonEunIndicator, 'id'> | null, o2: Pick<IPersonEunIndicator, 'id'> | null): boolean {
    return o1 && o2 ? this.getPersonEunIndicatorIdentifier(o1) === this.getPersonEunIndicatorIdentifier(o2) : o1 === o2;
  }

  addPersonEunIndicatorToCollectionIfMissing<Type extends Pick<IPersonEunIndicator, 'id'>>(
    personEunIndicatorCollection: Type[],
    ...personEunIndicatorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const personEunIndicators: Type[] = personEunIndicatorsToCheck.filter(isPresent);
    if (personEunIndicators.length > 0) {
      const personEunIndicatorCollectionIdentifiers = personEunIndicatorCollection.map(
        personEunIndicatorItem => this.getPersonEunIndicatorIdentifier(personEunIndicatorItem)!
      );
      const personEunIndicatorsToAdd = personEunIndicators.filter(personEunIndicatorItem => {
        const personEunIndicatorIdentifier = this.getPersonEunIndicatorIdentifier(personEunIndicatorItem);
        if (personEunIndicatorCollectionIdentifiers.includes(personEunIndicatorIdentifier)) {
          return false;
        }
        personEunIndicatorCollectionIdentifiers.push(personEunIndicatorIdentifier);
        return true;
      });
      return [...personEunIndicatorsToAdd, ...personEunIndicatorCollection];
    }
    return personEunIndicatorCollection;
  }

  protected convertDateFromClient<T extends IPersonEunIndicator | NewPersonEunIndicator | PartialUpdatePersonEunIndicator>(
    personEunIndicator: T
  ): RestOf<T> {
    return {
      ...personEunIndicator,
      createdDate: personEunIndicator.createdDate?.format(DATE_FORMAT) ?? null,
      lastModifiedDate: personEunIndicator.lastModifiedDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restPersonEunIndicator: RestPersonEunIndicator): IPersonEunIndicator {
    return {
      ...restPersonEunIndicator,
      createdDate: restPersonEunIndicator.createdDate ? dayjs(restPersonEunIndicator.createdDate) : undefined,
      lastModifiedDate: restPersonEunIndicator.lastModifiedDate ? dayjs(restPersonEunIndicator.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPersonEunIndicator>): HttpResponse<IPersonEunIndicator> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPersonEunIndicator[]>): HttpResponse<IPersonEunIndicator[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

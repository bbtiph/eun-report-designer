import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWorkingGroupReferences, NewWorkingGroupReferences } from '../working-group-references.model';

export type PartialUpdateWorkingGroupReferences = Partial<IWorkingGroupReferences> & Pick<IWorkingGroupReferences, 'id'>;

type RestOf<T extends IWorkingGroupReferences | NewWorkingGroupReferences> = Omit<
  T,
  'countryRepresentativeStartDate' | 'countryRepresentativeEndDate'
> & {
  countryRepresentativeStartDate?: string | null;
  countryRepresentativeEndDate?: string | null;
};

export type RestWorkingGroupReferences = RestOf<IWorkingGroupReferences>;

export type NewRestWorkingGroupReferences = RestOf<NewWorkingGroupReferences>;

export type PartialUpdateRestWorkingGroupReferences = RestOf<PartialUpdateWorkingGroupReferences>;

export type EntityResponseType = HttpResponse<IWorkingGroupReferences>;
export type EntityArrayResponseType = HttpResponse<IWorkingGroupReferences[]>;

@Injectable({ providedIn: 'root' })
export class WorkingGroupReferencesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/working-group-references');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(workingGroupReferences: NewWorkingGroupReferences): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workingGroupReferences);
    return this.http
      .post<RestWorkingGroupReferences>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  upload(data: FormData): Observable<HttpResponse<{}>> {
    return this.http.post<String>(`${this.resourceUrl}/upload`, data, { observe: 'response' });
  }

  update(workingGroupReferences: IWorkingGroupReferences): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workingGroupReferences);
    return this.http
      .put<RestWorkingGroupReferences>(`${this.resourceUrl}/${this.getWorkingGroupReferencesIdentifier(workingGroupReferences)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(workingGroupReferences: PartialUpdateWorkingGroupReferences): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workingGroupReferences);
    return this.http
      .patch<RestWorkingGroupReferences>(`${this.resourceUrl}/${this.getWorkingGroupReferencesIdentifier(workingGroupReferences)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestWorkingGroupReferences>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  findAll(code?: string): Observable<IWorkingGroupReferences[]> {
    return this.http.get<IWorkingGroupReferences[]>(this.resourceUrl + '/by-country/' + code);
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestWorkingGroupReferences[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getWorkingGroupReferencesIdentifier(workingGroupReferences: Pick<IWorkingGroupReferences, 'id'>): number {
    return workingGroupReferences.id;
  }

  compareWorkingGroupReferences(o1: Pick<IWorkingGroupReferences, 'id'> | null, o2: Pick<IWorkingGroupReferences, 'id'> | null): boolean {
    return o1 && o2 ? this.getWorkingGroupReferencesIdentifier(o1) === this.getWorkingGroupReferencesIdentifier(o2) : o1 === o2;
  }

  addWorkingGroupReferencesToCollectionIfMissing<Type extends Pick<IWorkingGroupReferences, 'id'>>(
    workingGroupReferencesCollection: Type[],
    ...workingGroupReferencesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const workingGroupReferences: Type[] = workingGroupReferencesToCheck.filter(isPresent);
    if (workingGroupReferences.length > 0) {
      const workingGroupReferencesCollectionIdentifiers = workingGroupReferencesCollection.map(
        workingGroupReferencesItem => this.getWorkingGroupReferencesIdentifier(workingGroupReferencesItem)!
      );
      const workingGroupReferencesToAdd = workingGroupReferences.filter(workingGroupReferencesItem => {
        const workingGroupReferencesIdentifier = this.getWorkingGroupReferencesIdentifier(workingGroupReferencesItem);
        if (workingGroupReferencesCollectionIdentifiers.includes(workingGroupReferencesIdentifier)) {
          return false;
        }
        workingGroupReferencesCollectionIdentifiers.push(workingGroupReferencesIdentifier);
        return true;
      });
      return [...workingGroupReferencesToAdd, ...workingGroupReferencesCollection];
    }
    return workingGroupReferencesCollection;
  }

  protected convertDateFromClient<T extends IWorkingGroupReferences | NewWorkingGroupReferences | PartialUpdateWorkingGroupReferences>(
    workingGroupReferences: T
  ): RestOf<T> {
    return {
      ...workingGroupReferences,
      countryRepresentativeStartDate: workingGroupReferences.countryRepresentativeStartDate?.format(DATE_FORMAT) ?? null,
      countryRepresentativeEndDate: workingGroupReferences.countryRepresentativeEndDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restWorkingGroupReferences: RestWorkingGroupReferences): IWorkingGroupReferences {
    return {
      ...restWorkingGroupReferences,
      countryRepresentativeStartDate: restWorkingGroupReferences.countryRepresentativeStartDate
        ? dayjs(restWorkingGroupReferences.countryRepresentativeStartDate)
        : undefined,
      countryRepresentativeEndDate: restWorkingGroupReferences.countryRepresentativeEndDate
        ? dayjs(restWorkingGroupReferences.countryRepresentativeEndDate)
        : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestWorkingGroupReferences>): HttpResponse<IWorkingGroupReferences> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestWorkingGroupReferences[]>): HttpResponse<IWorkingGroupReferences[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

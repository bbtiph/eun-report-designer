import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJobInfo, NewJobInfo } from '../job-info.model';

export type PartialUpdateJobInfo = Partial<IJobInfo> & Pick<IJobInfo, 'id'>;

type RestOf<T extends IJobInfo | NewJobInfo> = Omit<T, 'startingDate' | 'endingDate'> & {
  startingDate?: string | null;
  endingDate?: string | null;
};

export type RestJobInfo = RestOf<IJobInfo>;

export type NewRestJobInfo = RestOf<NewJobInfo>;

export type PartialUpdateRestJobInfo = RestOf<PartialUpdateJobInfo>;

export type EntityResponseType = HttpResponse<IJobInfo>;
export type EntityArrayResponseType = HttpResponse<IJobInfo[]>;

@Injectable({ providedIn: 'root' })
export class JobInfoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/job-infos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(jobInfo: NewJobInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobInfo);
    return this.http
      .post<RestJobInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(jobInfo: IJobInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobInfo);
    return this.http
      .put<RestJobInfo>(`${this.resourceUrl}/${this.getJobInfoIdentifier(jobInfo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(jobInfo: PartialUpdateJobInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobInfo);
    return this.http
      .patch<RestJobInfo>(`${this.resourceUrl}/${this.getJobInfoIdentifier(jobInfo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestJobInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestJobInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getJobInfoIdentifier(jobInfo: Pick<IJobInfo, 'id'>): number {
    return jobInfo.id;
  }

  compareJobInfo(o1: Pick<IJobInfo, 'id'> | null, o2: Pick<IJobInfo, 'id'> | null): boolean {
    return o1 && o2 ? this.getJobInfoIdentifier(o1) === this.getJobInfoIdentifier(o2) : o1 === o2;
  }

  addJobInfoToCollectionIfMissing<Type extends Pick<IJobInfo, 'id'>>(
    jobInfoCollection: Type[],
    ...jobInfosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const jobInfos: Type[] = jobInfosToCheck.filter(isPresent);
    if (jobInfos.length > 0) {
      const jobInfoCollectionIdentifiers = jobInfoCollection.map(jobInfoItem => this.getJobInfoIdentifier(jobInfoItem)!);
      const jobInfosToAdd = jobInfos.filter(jobInfoItem => {
        const jobInfoIdentifier = this.getJobInfoIdentifier(jobInfoItem);
        if (jobInfoCollectionIdentifiers.includes(jobInfoIdentifier)) {
          return false;
        }
        jobInfoCollectionIdentifiers.push(jobInfoIdentifier);
        return true;
      });
      return [...jobInfosToAdd, ...jobInfoCollection];
    }
    return jobInfoCollection;
  }

  protected convertDateFromClient<T extends IJobInfo | NewJobInfo | PartialUpdateJobInfo>(jobInfo: T): RestOf<T> {
    return {
      ...jobInfo,
      startingDate: jobInfo.startingDate?.format(DATE_FORMAT) ?? null,
      endingDate: jobInfo.endingDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restJobInfo: RestJobInfo): IJobInfo {
    return {
      ...restJobInfo,
      startingDate: restJobInfo.startingDate ? dayjs(restJobInfo.startingDate) : undefined,
      endingDate: restJobInfo.endingDate ? dayjs(restJobInfo.endingDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestJobInfo>): HttpResponse<IJobInfo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestJobInfo[]>): HttpResponse<IJobInfo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

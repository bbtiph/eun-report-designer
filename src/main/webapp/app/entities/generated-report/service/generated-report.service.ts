import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGeneratedReport, NewGeneratedReport } from '../generated-report.model';

export type PartialUpdateGeneratedReport = Partial<IGeneratedReport> & Pick<IGeneratedReport, 'id'>;

type RestOf<T extends IGeneratedReport | NewGeneratedReport> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestGeneratedReport = RestOf<IGeneratedReport>;

export type NewRestGeneratedReport = RestOf<NewGeneratedReport>;

export type PartialUpdateRestGeneratedReport = RestOf<PartialUpdateGeneratedReport>;

export type EntityResponseType = HttpResponse<IGeneratedReport>;
export type EntityArrayResponseType = HttpResponse<IGeneratedReport[]>;

@Injectable({ providedIn: 'root' })
export class GeneratedReportService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/generated-reports');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(generatedReport: NewGeneratedReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(generatedReport);
    return this.http
      .post<RestGeneratedReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(generatedReport: IGeneratedReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(generatedReport);
    return this.http
      .put<RestGeneratedReport>(`${this.resourceUrl}/${this.getGeneratedReportIdentifier(generatedReport)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(generatedReport: PartialUpdateGeneratedReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(generatedReport);
    return this.http
      .patch<RestGeneratedReport>(`${this.resourceUrl}/${this.getGeneratedReportIdentifier(generatedReport)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestGeneratedReport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestGeneratedReport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGeneratedReportIdentifier(generatedReport: Pick<IGeneratedReport, 'id'>): number {
    return generatedReport.id;
  }

  compareGeneratedReport(o1: Pick<IGeneratedReport, 'id'> | null, o2: Pick<IGeneratedReport, 'id'> | null): boolean {
    return o1 && o2 ? this.getGeneratedReportIdentifier(o1) === this.getGeneratedReportIdentifier(o2) : o1 === o2;
  }

  addGeneratedReportToCollectionIfMissing<Type extends Pick<IGeneratedReport, 'id'>>(
    generatedReportCollection: Type[],
    ...generatedReportsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const generatedReports: Type[] = generatedReportsToCheck.filter(isPresent);
    if (generatedReports.length > 0) {
      const generatedReportCollectionIdentifiers = generatedReportCollection.map(
        generatedReportItem => this.getGeneratedReportIdentifier(generatedReportItem)!
      );
      const generatedReportsToAdd = generatedReports.filter(generatedReportItem => {
        const generatedReportIdentifier = this.getGeneratedReportIdentifier(generatedReportItem);
        if (generatedReportCollectionIdentifiers.includes(generatedReportIdentifier)) {
          return false;
        }
        generatedReportCollectionIdentifiers.push(generatedReportIdentifier);
        return true;
      });
      return [...generatedReportsToAdd, ...generatedReportCollection];
    }
    return generatedReportCollection;
  }

  protected convertDateFromClient<T extends IGeneratedReport | NewGeneratedReport | PartialUpdateGeneratedReport>(
    generatedReport: T
  ): RestOf<T> {
    return {
      ...generatedReport,
      createdDate: generatedReport.createdDate?.format(DATE_FORMAT) ?? null,
      lastModifiedDate: generatedReport.lastModifiedDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restGeneratedReport: RestGeneratedReport): IGeneratedReport {
    return {
      ...restGeneratedReport,
      createdDate: restGeneratedReport.createdDate ? dayjs(restGeneratedReport.createdDate) : undefined,
      lastModifiedDate: restGeneratedReport.lastModifiedDate ? dayjs(restGeneratedReport.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestGeneratedReport>): HttpResponse<IGeneratedReport> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestGeneratedReport[]>): HttpResponse<IGeneratedReport[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

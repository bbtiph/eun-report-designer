import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReportBlocksContentData, NewReportBlocksContentData } from '../report-blocks-content-data.model';

export type PartialUpdateReportBlocksContentData = Partial<IReportBlocksContentData> & Pick<IReportBlocksContentData, 'id'>;

export type EntityResponseType = HttpResponse<IReportBlocksContentData>;
export type EntityArrayResponseType = HttpResponse<IReportBlocksContentData[]>;

@Injectable({ providedIn: 'root' })
export class ReportBlocksContentDataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/report-blocks-content-data');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(reportBlocksContentData: NewReportBlocksContentData): Observable<EntityResponseType> {
    return this.http.post<IReportBlocksContentData>(this.resourceUrl, reportBlocksContentData, { observe: 'response' });
  }

  update(reportBlocksContentData: IReportBlocksContentData): Observable<EntityResponseType> {
    return this.http.put<IReportBlocksContentData>(
      `${this.resourceUrl}/${this.getReportBlocksContentDataIdentifier(reportBlocksContentData)}`,
      reportBlocksContentData,
      { observe: 'response' }
    );
  }

  partialUpdate(reportBlocksContentData: PartialUpdateReportBlocksContentData): Observable<EntityResponseType> {
    return this.http.patch<IReportBlocksContentData>(
      `${this.resourceUrl}/${this.getReportBlocksContentDataIdentifier(reportBlocksContentData)}`,
      reportBlocksContentData,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReportBlocksContentData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReportBlocksContentData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getReportBlocksContentDataIdentifier(reportBlocksContentData: Pick<IReportBlocksContentData, 'id'>): number {
    return reportBlocksContentData.id;
  }

  compareReportBlocksContentData(
    o1: Pick<IReportBlocksContentData, 'id'> | null,
    o2: Pick<IReportBlocksContentData, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getReportBlocksContentDataIdentifier(o1) === this.getReportBlocksContentDataIdentifier(o2) : o1 === o2;
  }

  addReportBlocksContentDataToCollectionIfMissing<Type extends Pick<IReportBlocksContentData, 'id'>>(
    reportBlocksContentDataCollection: Type[],
    ...reportBlocksContentDataToCheck: (Type | null | undefined)[]
  ): Type[] {
    const reportBlocksContentData: Type[] = reportBlocksContentDataToCheck.filter(isPresent);
    if (reportBlocksContentData.length > 0) {
      const reportBlocksContentDataCollectionIdentifiers = reportBlocksContentDataCollection.map(
        reportBlocksContentDataItem => this.getReportBlocksContentDataIdentifier(reportBlocksContentDataItem)!
      );
      const reportBlocksContentDataToAdd = reportBlocksContentData.filter(reportBlocksContentDataItem => {
        const reportBlocksContentDataIdentifier = this.getReportBlocksContentDataIdentifier(reportBlocksContentDataItem);
        if (reportBlocksContentDataCollectionIdentifiers.includes(reportBlocksContentDataIdentifier)) {
          return false;
        }
        reportBlocksContentDataCollectionIdentifiers.push(reportBlocksContentDataIdentifier);
        return true;
      });
      return [...reportBlocksContentDataToAdd, ...reportBlocksContentDataCollection];
    }
    return reportBlocksContentDataCollection;
  }
}

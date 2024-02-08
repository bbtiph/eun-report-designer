import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReportBlocksContent, NewReportBlocksContent } from '../report-blocks-content.model';

export type PartialUpdateReportBlocksContent = Partial<IReportBlocksContent> & Pick<IReportBlocksContent, 'id'>;

export type EntityResponseType = HttpResponse<IReportBlocksContent>;
export type EntityArrayResponseType = HttpResponse<IReportBlocksContent[]>;

@Injectable({ providedIn: 'root' })
export class ReportBlocksContentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/report-blocks-contents');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(reportBlocksContent: NewReportBlocksContent): Observable<EntityResponseType> {
    return this.http.post<IReportBlocksContent>(this.resourceUrl, reportBlocksContent, { observe: 'response' });
  }

  update(reportBlocksContent: IReportBlocksContent): Observable<EntityResponseType> {
    return this.http.put<IReportBlocksContent>(
      `${this.resourceUrl}/${this.getReportBlocksContentIdentifier(reportBlocksContent)}`,
      reportBlocksContent,
      { observe: 'response' }
    );
  }

  partialUpdate(reportBlocksContent: PartialUpdateReportBlocksContent): Observable<EntityResponseType> {
    return this.http.patch<IReportBlocksContent>(
      `${this.resourceUrl}/${this.getReportBlocksContentIdentifier(reportBlocksContent)}`,
      reportBlocksContent,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReportBlocksContent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReportBlocksContent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getReportBlocksContentIdentifier(reportBlocksContent: Pick<IReportBlocksContent, 'id'>): number {
    // @ts-ignore
    return reportBlocksContent.id;
  }

  compareReportBlocksContent(o1: Pick<IReportBlocksContent, 'id'> | null, o2: IReportBlocksContent | null): boolean {
    return o1 && o2 ? this.getReportBlocksContentIdentifier(o1) === this.getReportBlocksContentIdentifier(o2) : o1 === o2;
  }

  addReportBlocksContentToCollectionIfMissing<Type extends Pick<IReportBlocksContent, 'id'>>(
    reportBlocksContentCollection: Type[],
    ...reportBlocksContentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const reportBlocksContents: Type[] = reportBlocksContentsToCheck.filter(isPresent);
    if (reportBlocksContents.length > 0) {
      const reportBlocksContentCollectionIdentifiers = reportBlocksContentCollection.map(
        reportBlocksContentItem => this.getReportBlocksContentIdentifier(reportBlocksContentItem)!
      );
      const reportBlocksContentsToAdd = reportBlocksContents.filter(reportBlocksContentItem => {
        const reportBlocksContentIdentifier = this.getReportBlocksContentIdentifier(reportBlocksContentItem);
        if (reportBlocksContentCollectionIdentifiers.includes(reportBlocksContentIdentifier)) {
          return false;
        }
        reportBlocksContentCollectionIdentifiers.push(reportBlocksContentIdentifier);
        return true;
      });
      return [...reportBlocksContentsToAdd, ...reportBlocksContentCollection];
    }
    return reportBlocksContentCollection;
  }
}

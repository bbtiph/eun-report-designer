import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReportBlocks, NewReportBlocks } from '../report-blocks.model';

export type PartialUpdateReportBlocks = Partial<IReportBlocks> & Pick<IReportBlocks, 'id'>;

export type EntityResponseType = HttpResponse<IReportBlocks>;
export type EntityArrayResponseType = HttpResponse<IReportBlocks[]>;

@Injectable({ providedIn: 'root' })
export class ReportBlocksService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/report-blocks');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(reportBlocks: NewReportBlocks): Observable<EntityResponseType> {
    return this.http.post<IReportBlocks>(this.resourceUrl, reportBlocks, { observe: 'response' });
  }

  update(reportBlocks: IReportBlocks): Observable<EntityResponseType> {
    return this.http.put<IReportBlocks>(`${this.resourceUrl}/${this.getReportBlocksIdentifier(reportBlocks)}`, reportBlocks, {
      observe: 'response',
    });
  }

  partialUpdate(reportBlocks: PartialUpdateReportBlocks): Observable<EntityResponseType> {
    return this.http.patch<IReportBlocks>(`${this.resourceUrl}/${this.getReportBlocksIdentifier(reportBlocks)}`, reportBlocks, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReportBlocks>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReportBlocks[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getReportBlocksIdentifier(reportBlocks: Pick<IReportBlocks, 'id'>): number {
    return reportBlocks.id;
  }

  compareReportBlocks(o1: Pick<IReportBlocks, 'id'> | null, o2: Pick<IReportBlocks, 'id'> | null): boolean {
    return o1 && o2 ? this.getReportBlocksIdentifier(o1) === this.getReportBlocksIdentifier(o2) : o1 === o2;
  }

  addReportBlocksToCollectionIfMissing<Type extends Pick<IReportBlocks, 'id'>>(
    reportBlocksCollection: Type[],
    ...reportBlocksToCheck: (Type | null | undefined)[]
  ): Type[] {
    const reportBlocks: Type[] = reportBlocksToCheck.filter(isPresent);
    if (reportBlocks.length > 0) {
      const reportBlocksCollectionIdentifiers = reportBlocksCollection.map(
        reportBlocksItem => this.getReportBlocksIdentifier(reportBlocksItem)!
      );
      const reportBlocksToAdd = reportBlocks.filter(reportBlocksItem => {
        const reportBlocksIdentifier = this.getReportBlocksIdentifier(reportBlocksItem);
        if (reportBlocksCollectionIdentifiers.includes(reportBlocksIdentifier)) {
          return false;
        }
        reportBlocksCollectionIdentifiers.push(reportBlocksIdentifier);
        return true;
      });
      return [...reportBlocksToAdd, ...reportBlocksCollection];
    }
    return reportBlocksCollection;
  }
}

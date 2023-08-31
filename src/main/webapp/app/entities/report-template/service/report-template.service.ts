import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReportTemplate, NewReportTemplate } from '../report-template.model';

export type PartialUpdateReportTemplate = Partial<IReportTemplate> & Pick<IReportTemplate, 'id'>;

type RestOf<T extends IReportTemplate | NewReportTemplate> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestReportTemplate = RestOf<IReportTemplate>;

export type NewRestReportTemplate = RestOf<NewReportTemplate>;

export type PartialUpdateRestReportTemplate = RestOf<PartialUpdateReportTemplate>;

export type EntityResponseType = HttpResponse<IReportTemplate>;
export type EntityArrayResponseType = HttpResponse<IReportTemplate[]>;

@Injectable({ providedIn: 'root' })
export class ReportTemplateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/report-templates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(reportTemplate: NewReportTemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reportTemplate);
    return this.http
      .post<RestReportTemplate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(reportTemplate: IReportTemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reportTemplate);
    return this.http
      .put<RestReportTemplate>(`${this.resourceUrl}/${this.getReportTemplateIdentifier(reportTemplate)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(reportTemplate: PartialUpdateReportTemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reportTemplate);
    return this.http
      .patch<RestReportTemplate>(`${this.resourceUrl}/${this.getReportTemplateIdentifier(reportTemplate)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestReportTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestReportTemplate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getReportTemplateIdentifier(reportTemplate: Pick<IReportTemplate, 'id'>): number {
    return reportTemplate.id;
  }

  compareReportTemplate(o1: Pick<IReportTemplate, 'id'> | null, o2: Pick<IReportTemplate, 'id'> | null): boolean {
    return o1 && o2 ? this.getReportTemplateIdentifier(o1) === this.getReportTemplateIdentifier(o2) : o1 === o2;
  }

  addReportTemplateToCollectionIfMissing<Type extends Pick<IReportTemplate, 'id'>>(
    reportTemplateCollection: Type[],
    ...reportTemplatesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const reportTemplates: Type[] = reportTemplatesToCheck.filter(isPresent);
    if (reportTemplates.length > 0) {
      const reportTemplateCollectionIdentifiers = reportTemplateCollection.map(
        reportTemplateItem => this.getReportTemplateIdentifier(reportTemplateItem)!
      );
      const reportTemplatesToAdd = reportTemplates.filter(reportTemplateItem => {
        const reportTemplateIdentifier = this.getReportTemplateIdentifier(reportTemplateItem);
        if (reportTemplateCollectionIdentifiers.includes(reportTemplateIdentifier)) {
          return false;
        }
        reportTemplateCollectionIdentifiers.push(reportTemplateIdentifier);
        return true;
      });
      return [...reportTemplatesToAdd, ...reportTemplateCollection];
    }
    return reportTemplateCollection;
  }

  protected convertDateFromClient<T extends IReportTemplate | NewReportTemplate | PartialUpdateReportTemplate>(
    reportTemplate: T
  ): RestOf<T> {
    return {
      ...reportTemplate,
      createdDate: reportTemplate.createdDate?.format(DATE_FORMAT) ?? null,
      lastModifiedDate: reportTemplate.lastModifiedDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restReportTemplate: RestReportTemplate): IReportTemplate {
    return {
      ...restReportTemplate,
      createdDate: restReportTemplate.createdDate ? dayjs(restReportTemplate.createdDate) : undefined,
      lastModifiedDate: restReportTemplate.lastModifiedDate ? dayjs(restReportTemplate.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestReportTemplate>): HttpResponse<IReportTemplate> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestReportTemplate[]>): HttpResponse<IReportTemplate[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

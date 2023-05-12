import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReport } from '../report.model';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, ReportService } from '../service/report.service';
import { ReportDeleteDialogComponent } from '../delete/report-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';
import { AbstractExportModal } from '../../../shared/modal/abstract-export.modal';

@Component({
  selector: 'jhi-report',
  templateUrl: './report.component.html',
})
export class ReportComponent implements OnInit {
  reports?: IReport[];
  isLoading = false;

  predicate = 'id';
  ascending = true;

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  constructor(
    protected reportService: ReportService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected dataUtils: DataUtils,
    protected modalService: NgbModal,
    private http: HttpClient
  ) {}

  trackId = (_index: number, item: IReport): number => this.reportService.getReportIdentifier(item);

  ngOnInit(): void {
    this.load();
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(report: IReport): void {
    const modalRef = this.modalService.open(ReportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.report = report;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  // generateReport(report: IReport): void {
  //   const body = { id: 0, output: 'pdf', lang: 'en' };
  //
  //   this.http.post('api/reports/generate/' + report.acronym, body, { responseType: 'blob' }).subscribe(response => {
  //     var a = document.createElement('a');
  //     a.href = URL.createObjectURL(response);
  //     // @ts-ignore
  //     a.download = report.reportName;
  //     a.click();
  //   });
  // }

  generateReport(report: IReport): void {
    const modalRef = this.modalService.open(AbstractExportModal, {
      animation: true,
      size: 'lg',
    });
    modalRef.componentInstance.param = this;
    modalRef.componentInstance.reportName = report.reportName;

    modalRef.result.then(params => {
      console.log(params);

      const body = { id: 0, output: params.format.name, lang: 'ru' };

      this.http.post('api/reports/generate/' + report.acronym, body, { responseType: 'blob' }).subscribe(response => {
        var a = document.createElement('a');
        a.href = URL.createObjectURL(response);
        // @ts-ignore
        a.download = report.reportName;
        a.click();
      });
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.page, this.predicate, this.ascending);
  }

  navigateToPage(page = this.page): void {
    this.handleNavigation(page, this.predicate, this.ascending);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.page, this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.reports = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IReport[] | null): IReport[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(page?: number, predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const pageToLoad: number = page ?? 1;
    const queryObject = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.reportService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page = this.page, predicate?: string, ascending?: boolean): void {
    const queryParamsObj = {
      page,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }
}

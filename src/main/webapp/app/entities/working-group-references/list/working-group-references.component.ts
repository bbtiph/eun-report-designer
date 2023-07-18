import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkingGroupReferences } from '../working-group-references.model';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, WorkingGroupReferencesService } from '../service/working-group-references.service';
import { WorkingGroupReferencesDeleteDialogComponent } from '../delete/working-group-references-delete-dialog.component';
import { ICountries } from '../../countries/countries.model';

@Component({
  selector: 'jhi-working-group-references',
  templateUrl: './working-group-references.component.html',
})
export class WorkingGroupReferencesComponent implements OnInit {
  @Input() template: any | null = null;
  @Input() type: string | '' | undefined;
  @Input() isFromReportBlock: boolean | false | undefined;
  @Input() isEdit: boolean | false | undefined;
  @Input() selectedCountry: ICountries | null = null;
  @Input() settings: any | null = null;
  @Output() templateChanged = new EventEmitter<any>();

  workingGroupReferences?: IWorkingGroupReferences[] = [];
  isLoading = false;
  columns: string | undefined;

  predicate = 'id';
  ascending = true;

  showModal = false;
  // @ts-ignore
  selectedFile: File;

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  constructor(
    protected workingGroupReferencesService: WorkingGroupReferencesService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IWorkingGroupReferences): number =>
    this.workingGroupReferencesService.getWorkingGroupReferencesIdentifier(item);

  ngOnInit(): void {
    if ((this.isFromReportBlock && this.type !== 'template') || this.isEdit) this.loadByCountry();
    else if (!this.isFromReportBlock) this.load();
  }

  getValueTemplate(index: string): boolean {
    return this.template.columns.find((c: { name: string; index: string }) => c.index === index) !== undefined;
  }

  setValueTemplate(index: string, name: string): void {
    if (!this.getValueTemplate(index)) this.template.columns.push({ name: name, index: index });
    else this.template.columns = this.template.columns.filter((c: { name: string; index: string }) => c.index !== index);

    this.templateChanged.emit(this.template);
  }

  isActiveColumn(index: string): boolean {
    return (this.getValueTemplate(index) && this.type !== 'template') || this.type === 'template';
  }

  delete(workingGroupReferences: IWorkingGroupReferences): void {
    const modalRef = this.modalService.open(WorkingGroupReferencesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.workingGroupReferences = workingGroupReferences;
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

  loadByCountry(): void {
    this.workingGroupReferencesService
      .findAll(this.selectedCountry?.countryCode ?? '')
      .subscribe((workingGroupReference: IWorkingGroupReferences[]) => {
        this.workingGroupReferences = workingGroupReference;
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
    this.workingGroupReferences = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IWorkingGroupReferences[] | null): IWorkingGroupReferences[] {
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
    return this.workingGroupReferencesService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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

  openModal() {
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  onFileChange(event: any) {
    this.selectedFile = event.target.files[0];
  }

  uploadFile() {
    const formData = new FormData();
    formData.append('file', this.selectedFile);

    this.workingGroupReferencesService.upload(formData).subscribe(() => {
      this.closeModal();
      this.load();
    });
  }

  getValue(obj: any, property: any): any {
    return obj[property];
  }
}

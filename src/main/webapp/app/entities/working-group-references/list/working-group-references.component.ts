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
import { FilterOptions, IFilterOptions, IFilterOption } from 'app/shared/filter/filter.model';
import { ColDef, ColGroupDef, ICellRendererParams } from '@ag-grid-community/core';
import { RendererComponent } from '../../../shared/filter/renderer-component/renderer.component';
import { SkillFilter } from '../../../shared/filter/filters/skill.component.filter';
import { ProficiencyFilter } from '../../../shared/filter/filters/proficiency.component.filter';

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

  public columnDefs!: (ColDef | ColGroupDef)[];
  workingGroupReferences?: IWorkingGroupReferences[] = [];
  isLoading = false;
  columns: string | undefined;

  predicate = 'id';
  ascending = true;
  filters: IFilterOptions = new FilterOptions();

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
    this.columnDefs = [
      {
        headerName: '#',
        width: 40,
        checkboxSelection: true,
        filter: false,
        sortable: false,
        suppressMenu: true,
        pinned: true,
      },
      {
        field: 'countryCode',
        headerName: 'Country Code',
        pinned: true,
      },
      {
        field: 'countryName',
        headerName: 'Country Name',
        pinned: true,
      },
      {
        headerName: 'Country representative',
        children: [
          {
            headerName: 'First Name',
            field: 'countryRepresentativeFirstName',
            width: 150,
          },
          {
            headerName: 'Last Name',
            field: 'countryRepresentativeLastName',
            width: 150,
          },
          {
            headerName: 'Mail',
            field: 'countryRepresentativeMail',
            width: 150,
          },
          {
            headerName: 'Position',
            field: 'countryRepresentativePosition',
            width: 150,
          },
          {
            headerName: 'Start Date',
            field: 'countryRepresentativeStartDate',
            width: 250,
            cellRenderer: (params: ICellRendererParams) => {
              return params.value
                ? pad(params.value.toDate().getDate(), 2) +
                    '/' +
                    pad(params.value.toDate().getMonth() + 1, 2) +
                    '/' +
                    params.value.toDate().getFullYear()
                : '';
            },
            menuTabs: ['filterMenuTab'],
            filter: 'agDateColumnFilter',
          },
          {
            headerName: 'End Date',
            field: 'countryRepresentativeEndDate',
            width: 250,
            cellRenderer: (params: ICellRendererParams) => {
              return params.value
                ? pad(params.value.toDate().getDate(), 2) +
                    '/' +
                    pad(params.value.toDate().getMonth() + 1, 2) +
                    '/' +
                    params.value.toDate().getFullYear()
                : '';
            },
            menuTabs: ['filterMenuTab'],
            filter: 'agDateColumnFilter',
          },
          {
            headerName: 'countryRepresentativeMinistry',
            field: 'First Name',
            width: 150,
          },
          {
            headerName: 'countryRepresentativeDepartment',
            field: 'First Name',
            width: 150,
          },
        ],
      },
      {
        headerName: 'Contact Eun',
        children: [
          {
            headerName: 'First Name',
            field: 'contactEunFirstName',
            minWidth: 150,
            filter: 'agTextColumnFilter',
          },
          {
            headerName: 'Last Name',
            field: 'contactEunLastName',
            cellRenderer: RendererComponent,
            minWidth: 150,
            filter: 'agTextColumnFilter',
          },
        ],
      },
      {
        headerName: '',
        children: [
          {
            field: 'type',
            headerName: 'Type',
            filter: 'agTextColumnFilter',
          },
          {
            field: 'isActive',
            headerName: 'Is Active',
            filter: false,
          },
          {
            field: 'createdBy',
            headerName: 'Created By',
            filter: 'agTextColumnFilter',
          },
          {
            field: 'lastModifiedBy',
            headerName: 'Last Modified By',
            filter: 'agTextColumnFilter',
          },
          {
            headerName: 'Created Date',
            field: 'createdDate',
            width: 250,
            cellRenderer: (params: ICellRendererParams) => {
              return params.value
                ? pad(params.value.toDate().getDate(), 2) +
                    '/' +
                    pad(params.value.toDate().getMonth() + 1, 2) +
                    '/' +
                    params.value.toDate().getFullYear()
                : '';
            },
            menuTabs: ['filterMenuTab'],
            filter: 'agDateColumnFilter',
          },
          {
            headerName: 'Last Modified Date',
            field: 'lastModifiedDate',
            width: 250,
            cellRenderer: (params: ICellRendererParams) => {
              return params.value
                ? pad(params.value.toDate().getDate(), 2) +
                    '/' +
                    pad(params.value.toDate().getMonth() + 1, 2) +
                    '/' +
                    params.value.toDate().getFullYear()
                : '';
            },
            menuTabs: ['filterMenuTab'],
            filter: 'agDateColumnFilter',
          },
        ],
      },
    ];
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
    const modalRef = this.modalService.open(WorkingGroupReferencesDeleteDialogComponent, {
      size: 'lg',
      backdrop: 'static',
    });
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
    this.handleNavigation(this.page, this.predicate, this.ascending, this.filters.filterOptions);
  }

  navigateToPage(page = this.page): void {
    this.handleNavigation(page, this.predicate, this.ascending, this.filters.filterOptions);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.page, this.predicate, this.ascending, this.filters.filterOptions))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
    this.filters.initializeFromParams(params);
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

  protected queryBackend(
    page?: number,
    predicate?: string,
    ascending?: boolean,
    filterOptions?: IFilterOption[]
  ): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const pageToLoad: number = page ?? 1;
    const queryObject: any = {
      page: pageToLoad - 1,
      // size: this.itemsPerPage,
      size: 1000,
      sort: this.getSortQueryParam(predicate, ascending),
    };
    filterOptions?.forEach(filterOption => {
      queryObject[filterOption.name] = filterOption.values;
    });
    return this.workingGroupReferencesService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page = this.page, predicate?: string, ascending?: boolean, filterOptions?: IFilterOption[]): void {
    const queryParamsObj: any = {
      page,
      // size: this.itemsPerPage,
      size: 1000,
      sort: this.getSortQueryParam(predicate, ascending),
    };

    filterOptions?.forEach(filterOption => {
      queryParamsObj[filterOption.nameAsQueryParam()] = filterOption.values;
    });

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

function pad(num: number, totalStringSize: number) {
  let asString = num + '';
  while (asString.length < totalStringSize) {
    asString = '0' + asString;
  }
  return asString;
}

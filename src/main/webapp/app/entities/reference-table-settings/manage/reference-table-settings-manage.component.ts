import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IReferenceTableSettings } from '../reference-table-settings.model';
import { EntityArrayResponseType, ReferenceTableSettingsService } from '../service/reference-table-settings.service';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { SortService } from '../../../shared/sort/sort.service';
import { DataUtils } from '../../../core/util/data-util.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { ASC, DEFAULT_SORT_DATA, DESC, ITEM_DELETED_EVENT, SORT } from '../../../config/navigation.constants';
import { ColDef, ColGroupDef, ICellRendererParams } from '@ag-grid-community/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { AbstractUploadFileModal } from '../../../shared/modal/abstract-upload-file.modal';
import { LoaderService } from '../../../shared/loader/loader-service.service';
import { AbstractDynamicFormBySettingsModal } from '../../../shared/modal/abstract-dynamic-form-by-settings.modal';
import { ICountries } from '../../countries/countries.model';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'jhi-reference-table-settings-manage',
  templateUrl: './reference-table-settings-manage.component.html',
  styleUrls: ['./reference-table-settings-manage.component.scss'],
})
export class ReferenceTableSettingsManageComponent implements OnInit {
  @Input() template: any | null = null;
  @Input() type: string | '' | undefined;
  @Input() isFromReportBlock: boolean | false | undefined;
  @Input() isEdit: boolean | false | undefined;
  @Input() selectedCountry: ICountries | null = null;
  @Input() settings: any | null = null;
  @Input() periodDateFromAndTo: any | null = null;
  @Input() source: string | '' | undefined;
  @Output() templateChanged = new EventEmitter<any>();

  referenceTableSettings?: IReferenceTableSettings[];
  selectedReferenceTableSettings: IReferenceTableSettings | null = null;
  isLoading = false;
  selectedRefTable: string = 'working_group_reference';

  public columnDefs!: (ColDef | ColGroupDef)[];
  data?: any[] = [];

  predicate = 'id';
  ascending = true;
  formGroup: FormGroup;

  constructor(
    protected referenceTableSettingsService: ReferenceTableSettingsService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected dataUtils: DataUtils,
    protected modalService: NgbModal,
    private http: HttpClient,
    public loader: LoaderService,
    private formBuilder: FormBuilder
  ) {
    this.formGroup = this.formBuilder.group({});
  }

  ngOnInit(): void {
    if (this.isFromReportBlock) {
      let queryParams = new HttpParams();
      if (this.periodDateFromAndTo) {
        queryParams = queryParams.set('fromDate', this.periodDateFromAndTo.from);
        queryParams = queryParams.set('toDate', this.periodDateFromAndTo.to);
      }
      this.referenceTableSettingsService
        .findAllReferenceTableSettingsDataByCountry(this.source ?? '', this.selectedCountry?.countryCode ?? '', queryParams)
        .subscribe(data => {
          this.data = data;
        });
    } else {
      this.load();
    }
  }

  transformSettingsToColumnDefs(settings: any[]): any[] {
    const columnDefs: any[] = [];
    for (const setting of settings) {
      if (setting.type !== 'list') {
        const columnDef: any = {
          headerName: setting.name,
          field: !setting.children ? setting.index : null,
          filter: setting.type === 'text' ? 'agTextColumnFilter' : setting.type === 'date' ? 'agDateColumnFilter' : 'agTextColumnFilter',
          width: setting?.width ? setting?.width : null,
        };

        if (setting.type === 'date') {
          columnDef.cellRenderer = (params: ICellRendererParams) => {
            if (typeof params.value === 'string' && params.value) {
              const dateValue = new Date(params.value);
              if (!isNaN(dateValue.getTime())) {
                // Проверьте, что преобразование прошло успешно
                return pad(dateValue.getDate(), 2) + '/' + pad(dateValue.getMonth() + 1, 2) + '/' + dateValue.getFullYear();
              }
            }
            return '';
          };
        }

        if (setting.children) {
          columnDef.children = this.transformSettingsToColumnDefs(setting.children);
        }

        columnDefs.push(columnDef);
      }
    }

    return columnDefs;
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
        this.onRefTableSelect(this.selectedRefTable);
      },
    });
  }

  trackId = (_index: number, item: IReferenceTableSettings): number =>
    this.referenceTableSettingsService.getReferenceTableSettingsIdentifier(item);

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.referenceTableSettingsService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.referenceTableSettings = this.refineData(dataFromBody);
  }

  protected fillComponentAttributesFromResponseBody(data: IReferenceTableSettings[] | null): IReferenceTableSettings[] {
    return data ?? [];
  }

  protected refineData(data: IReferenceTableSettings[]): IReferenceTableSettings[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  onRefTableSelect(refTable: string) {
    this.selectedRefTable = refTable;
    this.referenceTableSettingsService.findByRefTable(refTable ?? '').subscribe((result: IReferenceTableSettings) => {
      this.selectedReferenceTableSettings = result;
      const columnDef: any = {
        headerName: 'Actions',
        field: 'reference-table-settings-manage',
        cellRenderer: 'btnCellRendererOfReferences',
        filter: false,
        sortable: false,
        suppressMenu: true,
        pinned: true,
        cellRendererParams: {
          deleteAction: function (row: any) {
            // @ts-ignore
            this.deleteReferenceRowByRefTableAndId(row);
          }.bind(this),
          viewAction: function (row: any) {
            // @ts-ignore
            this.viewReferenceRowByRefTable(row, 'view');
          }.bind(this),
          editAction: function (row: any) {
            // @ts-ignore
            this.viewReferenceRowByRefTable(row, 'edit');
          }.bind(this),
        },
      };
      // @ts-ignore
      this.columnDefs = this.transformSettingsToColumnDefs(JSON.parse(result?.columns));
      this.columnDefs.push(columnDef);

      this.referenceTableSettingsService.findAllReferenceTableSettingsData(refTable ?? '').subscribe(data => {
        this.data = data;
      });
    });
  }

  deleteReferenceRowByRefTableAndId(row: any): void {
    this.referenceTableSettingsService.deleteReferenceRowByRefTableAndId(this.selectedRefTable ?? '', row.id).subscribe(() => {
      this.onRefTableSelect(this.selectedRefTable);
    });
  }

  viewReferenceRowByRefTable(row: any, action: string): void {
    const modalRef = this.modalService.open(AbstractDynamicFormBySettingsModal, {
      animation: true,
      size: 'lg',
    });
    modalRef.componentInstance.param = this;
    modalRef.componentInstance.row = row;
    modalRef.componentInstance.action = action;
    modalRef.componentInstance.settings = this.selectedReferenceTableSettings?.columns;
    modalRef.result.then(params => {
      if (params.row) {
        this.referenceTableSettingsService.updateReferenceRowByRefTable(this.selectedRefTable ?? '', params.row).subscribe(() => {
          this.onRefTableSelect(this.selectedRefTable);
        });
      }
    });
  }

  downloadExcel() {
    this.http
      .post(`api/reference-table-settings/by-ref-table/download/${this.selectedRefTable}`, null, { responseType: 'blob' as 'json' })
      .subscribe(response => {
        // @ts-ignore
        const blob = new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `${this.selectedRefTable}.xlsx`;
        a.click();
        window.URL.revokeObjectURL(url);
      });
  }

  uploadFile(): void {
    const modalRef = this.modalService.open(AbstractUploadFileModal, {
      animation: true,
      size: 'lg',
    });
    modalRef.componentInstance.param = this;
    modalRef.result.then(params => {
      console.log(params.files[0]);
      const formData = new FormData();
      formData.append('file', params.files[0]);
      this.http
        .post(`api/reference-table-settings/by-ref-table/upload/${this.selectedRefTable}`, formData, { observe: 'response' })
        .subscribe(() => {
          this.load();
        });
    });
  }

  // @ts-ignore
  getColumnFormControl(index: string, name: string): FormControl {
    const formControlName = `column_${index}`;
    let column;
    if (!this.formGroup.get(formControlName) || this.formGroup.get(formControlName) === null) {
      column = this.template.columns.find((c: { name: string; index: string }) => c.index === index);
      this.formGroup.addControl(formControlName, new FormControl(column != null ? column.name : name));
    }
    return this.formGroup.get(formControlName) as FormControl;
  }

  setColumnValue(index: string): void {
    const formControlName = `column_${index}`;
    this.template.columns.forEach((c: { name: string; index: string }) => {
      if (c.index === index) c.name = this.formGroup.get(formControlName)?.value;
    });

    this.templateChanged.emit(this.template);
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

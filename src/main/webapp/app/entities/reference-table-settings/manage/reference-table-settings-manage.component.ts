import { Component, OnInit } from '@angular/core';
import { IReferenceTableSettings } from '../reference-table-settings.model';
import { EntityArrayResponseType, ReferenceTableSettingsService } from '../service/reference-table-settings.service';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { SortService } from '../../../shared/sort/sort.service';
import { DataUtils } from '../../../core/util/data-util.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { combineLatest, Observable, switchMap, tap } from 'rxjs';
import { ASC, DEFAULT_SORT_DATA, DESC, SORT } from '../../../config/navigation.constants';
import { WorkingGroupReferencesService } from '../../working-group-references/service/working-group-references.service';
import { MoeContactsService } from '../../moe-contacts/service/moe-contacts.service';
import { IWorkingGroupReferences } from '../../working-group-references/working-group-references.model';
import { IMoeContacts } from '../../moe-contacts/moe-contacts.model';
import { ColDef, ColGroupDef, ICellRendererParams } from '@ag-grid-community/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'jhi-reference-table-settings-manage',
  templateUrl: './reference-table-settings-manage.component.html',
  styleUrls: ['./reference-table-settings-manage.component.scss'],
})
export class ReferenceTableSettingsManageComponent implements OnInit {
  referenceTableSettings?: IReferenceTableSettings[];
  selectedReferenceTableSettings: IReferenceTableSettings | null = null;
  isLoading = false;
  selectedRefTable: string | null = null;

  public columnDefs!: (ColDef | ColGroupDef)[];
  workingGroupReferences?: IWorkingGroupReferences[] = [];
  moeContacts?: IMoeContacts[] = [];
  data?: any[] = [];

  predicate = 'id';
  ascending = true;

  constructor(
    protected referenceTableSettingsService: ReferenceTableSettingsService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected dataUtils: DataUtils,
    protected modalService: NgbModal,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.load();
  }

  transformSettingsToColumnDefs(settings: any[]): any[] {
    const columnDefs: any[] = [];
    for (const setting of settings) {
      const columnDef: any = {
        headerName: setting.name,
        field: !setting.children ? setting.index : null,
        filter: setting.type === 'text' ? 'agTextColumnFilter' : setting.type === 'date' ? 'agDateColumnFilter' : null,
        // any other options
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

    return columnDefs;
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
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

  onRefTableSelect(refTable: string | null) {
    this.selectedRefTable = refTable;
    this.referenceTableSettingsService.findByRefTable(refTable ?? '').subscribe((result: IReferenceTableSettings) => {
      this.selectedReferenceTableSettings = result;
      // @ts-ignore
      this.columnDefs = this.transformSettingsToColumnDefs(JSON.parse(result?.columns));
      this.referenceTableSettingsService.findAllReferenceTableSettingsData(refTable ?? '').subscribe(data => {
        this.data = data;
        console.log('data ref: ', data);
      });
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
}

function pad(num: number, totalStringSize: number) {
  let asString = num + '';
  while (asString.length < totalStringSize) {
    asString = '0' + asString;
  }
  return asString;
}

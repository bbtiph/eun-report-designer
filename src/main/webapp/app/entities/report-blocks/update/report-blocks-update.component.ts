import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ReportBlocksFormGroup, ReportBlocksFormService } from './report-blocks-form.service';
import { IReportBlocks } from '../report-blocks.model';
import { ReportBlocksService } from '../service/report-blocks.service';
import { ICountries } from 'app/entities/countries/countries.model';
import { CountriesService } from 'app/entities/countries/service/countries.service';
import { IReport } from 'app/entities/report/report.model';
import { ReportService } from 'app/entities/report/service/report.service';
import { IReportBlocksContent } from '../../report-blocks-content/report-blocks-content.model';
import { IReportBlocksContentData, NewReportBlocksContentData } from '../../report-blocks-content-data/report-blocks-content-data.model';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ReportBlocksContentDataService } from '../../report-blocks-content-data/service/report-blocks-content-data.service';
import { ReportBlocksContentService } from '../../report-blocks-content/service/report-blocks-content.service';
import { IReportBlocksContentTemplateColumn } from '../../report-blocks-content/report-blocks-content-template-column.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ReferenceSelectionModal } from '../../../shared/modal/reference-selection.modal';
import { IReferenceTableSettings } from '../../reference-table-settings/reference-table-settings.model';
import { ReferenceTableSettingsService } from '../../reference-table-settings/service/reference-table-settings.service';
import { DragulaService } from 'ng2-dragula';

@Component({
  selector: 'jhi-report-blocks-update',
  templateUrl: './report-blocks-update.component.html',
  styleUrls: ['./report-block-update.component.scss'],
})
export class ReportBlocksUpdateComponent implements OnInit, OnDestroy {
  @Input() block: IReportBlocks | null = null;
  @Input() selectedCountryFromReport: ICountries | null = null;
  @Input() isFromReport: boolean | false | undefined;
  isSaving = false;
  isEdit = false;
  type: string = '';
  tableColumns = new Map();
  referenceTableSettings = new Map();
  reportBlocks: IReportBlocks | null = null;
  reportBlocksContents?: IReportBlocksContent[];
  selectedCountry: ICountries | null = null;
  countryId: number | undefined;
  groupNameDragula: string = 'CONTENT';

  countriesSharedCollection: ICountries[] = [];
  reportsSharedCollection: IReport[] = [];
  columns: IReportBlocksContentTemplateColumn[] = [];
  formGroup: FormGroup;

  editForm: ReportBlocksFormGroup = this.reportBlocksFormService.createReportBlocksFormGroup();

  constructor(
    private modalService: NgbModal,
    protected reportBlocksService: ReportBlocksService,
    protected reportBlocksContentService: ReportBlocksContentService,
    protected reportBlocksFormService: ReportBlocksFormService,
    protected countriesService: CountriesService,
    protected reportService: ReportService,
    protected activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    protected reportBlocksContentDataService: ReportBlocksContentDataService,
    protected referenceTableSettingsService: ReferenceTableSettingsService,
    private dragulaService: DragulaService
  ) {
    this.formGroup = this.formBuilder.group({});
  }

  compareCountries = (o1: ICountries | null, o2: ICountries | null): boolean => this.countriesService.compareCountries(o1, o2);

  compareReport = (o1: IReport | null, o2: IReport | null): boolean => this.reportService.compareReport(o1, o2);

  ngOnInit(): void {
    if (this.isFromReport) {
      this.type = 'report';
      this.reportBlocks = this.block;
      if (this.block) {
        this.updateForm(this.block);
      }
      this.selectedCountry = this.selectedCountryFromReport;
    } else {
      // @ts-ignore
      this.type = this.activatedRoute.data.value.type;
      // @ts-ignore
      this.countryId = this.activatedRoute.params.value.countryId;
      this.countriesService.findById(this.countryId || 1).subscribe((country: ICountries) => {
        this.selectedCountry = country;
      });

      this.activatedRoute.data.subscribe(({ reportBlocks }) => {
        this.reportBlocks = reportBlocks;
        if (reportBlocks) {
          this.updateForm(reportBlocks);
        }
        this.loadRelationshipsOptions();
      });
    }

    if (this.isFromReport) {
      this.groupNameDragula += this.reportBlocks?.id;
    }

    this.dragulaService.createGroup(this.groupNameDragula, {
      revertOnSpill: true,
      copy: false,
      moves: function (el, container, cHandle) {
        // @ts-ignore
        return cHandle.className === 'handle-content';
      },
    });
    this.dragulaService.dropModel(this.groupNameDragula).subscribe(args => {});
    this.reportBlocksContents = this.reportBlocks?.reportBlocksContents ?? [];

    if (this.type != 'new') this.initializeFormControls();
    this.getTableColumns();
    this.loadReferenceTableSettings();
  }

  ngOnDestroy() {
    this.dragulaService.destroy(this.groupNameDragula);
  }

  drop(contents: IReportBlocksContent[]) {
    // @ts-ignore
    this.reportBlocks.reportBlocksContents = contents;
    // @ts-ignore
    this.reportBlocks.reportBlocksContents.forEach((content, index) => {
      content.priorityNumber = index;
    });
    // @ts-ignore
    this.reportBlocksService.update(this.reportBlocks).subscribe();
  }

  getTableColumns() {
    if (this.reportBlocks && this.reportBlocks.reportBlocksContents) {
      const elements = this.reportBlocks.reportBlocksContents.filter(a => a.type === 'table' || a.type === 'reference');
      elements.forEach(element => {
        this.tableColumns.set(element.id, JSON.parse(element.template ?? '{}'));
      });
    }
  }

  loadReferenceTableSettings() {
    if (this.reportBlocks && this.reportBlocks.reportBlocksContents) {
      const elements = this.reportBlocks.reportBlocksContents.filter(a => a.type === 'reference');
      elements.forEach(element => {
        let content = JSON.parse(element.template ?? '{}');
        if (!this.referenceTableSettings.has(content.source)) {
          this.referenceTableSettingsService.findByRefTable(content.source).subscribe((result: IReferenceTableSettings) => {
            this.referenceTableSettings.set(content.source, JSON.parse(result.columns ?? '[{}]'));
          });
        }
      });
    }
  }

  previousState(): void {
    if (this.type != 'report') window.history.back();
  }

  isEditChange(): void {
    this.isEdit = !this.isEdit;
  }

  save(): void {
    this.isSaving = true;
    const reportBlocks = this.reportBlocksFormService.getReportBlocks(this.editForm);

    // @ts-ignore
    for (const content of reportBlocks.reportBlocksContents) {
      const formControl = this.formGroup.get(`content_${content.id}_name`) as FormControl;
      // @ts-ignore
      const contentData = JSON.parse(content.template);
      contentData.name = formControl.value;
      content.template = JSON.stringify(contentData);
      if (content.type === 'text') {
        const formControl = this.formGroup.get(`content_${content.id}_data`) as FormControl;
        // @ts-ignore
        const contentData = JSON.parse(content.template);
        contentData.data = formControl.value;
        content.template = JSON.stringify(contentData);
        for (const row of content.reportBlocksContentData) {
          const formControl = this.formGroup.get(`content_${content.id}_${row.id}_data`) as FormControl;
          // @ts-ignore
          const rowData = JSON.parse(row.data);
          rowData.data = formControl.value;
          row.data = JSON.stringify(rowData);
        }
      } else if (content.type === 'table') {
        // @ts-ignore
        const columns = this.getColumns(content.template);
        for (const column of columns) {
          // @ts-ignore
          const columnData = JSON.parse(content.template);
          const formControlName = `column_${content.id}_${column.index}`;
          const formControl = this.formGroup.get(formControlName) as FormControl;
          columnData.columns.find((c: any) => c.index === column.index).name = formControl.value;
          content.template = JSON.stringify(columnData);
        }
        for (const row of content.reportBlocksContentData) {
          // @ts-ignore
          const rowData = JSON.parse(row.data);
          for (const column of columns) {
            const formControlName = `row_${content.id}_${row.id}_column_${column.index}`;
            const formControl = this.formGroup.get(formControlName) as FormControl;
            try {
              rowData.rows.find((r: any) => r.index === column.index).data = formControl.value;
            } catch (e) {
              console.log(e);
            }
          }
          row.data = JSON.stringify(rowData);
        }
      }
    }

    if (reportBlocks.id !== null) {
      this.subscribeToSaveResponse(this.reportBlocksService.update(reportBlocks));
    } else {
      this.subscribeToSaveResponse(this.reportBlocksService.create(reportBlocks));
    }
    if (this.type === 'report') this.isEditChange();
  }

  close(): void {
    this.formGroup = this.formBuilder.group({});
    this.ngOnInit();
    if (this.type === 'report') this.isEditChange();
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReportBlocks>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(reportBlocks: IReportBlocks): void {
    this.reportBlocks = reportBlocks;
    this.reportBlocksFormService.resetForm(this.editForm, reportBlocks);

    this.countriesSharedCollection = this.countriesService.addCountriesToCollectionIfMissing<ICountries>(
      this.countriesSharedCollection,
      ...(reportBlocks.countryIds ?? [])
    );
    this.reportsSharedCollection = this.reportService.addReportToCollectionIfMissing<IReport>(
      this.reportsSharedCollection,
      reportBlocks.report
    );
  }

  protected loadRelationshipsOptions(): void {
    this.countriesService
      .query()
      .pipe(map((res: HttpResponse<ICountries[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountries[]) =>
          this.countriesService.addCountriesToCollectionIfMissing<ICountries>(countries, ...(this.reportBlocks?.countryIds ?? []))
        )
      )
      .subscribe((countries: ICountries[]) => (this.countriesSharedCollection = countries));

    this.reportService
      .query()
      .pipe(map((res: HttpResponse<IReport[]>) => res.body ?? []))
      .pipe(map((reports: IReport[]) => this.reportService.addReportToCollectionIfMissing<IReport>(reports, this.reportBlocks?.report)))
      .subscribe((reports: IReport[]) => (this.reportsSharedCollection = reports));
  }

  addRow(content: IReportBlocksContent): void {
    let newRow: NewReportBlocksContentData = {
      id: null,
      data: this.generateInitialData(content),
      reportBlocksContent: content,
      country: {
        id: this.countryId || 1,
      },
      newContentData: false,
    };

    this.reportBlocksContentDataService.createWithContent(newRow, content.id).subscribe((res: HttpResponse<IReportBlocksContentData>) => {
      const newRow: IReportBlocksContentData = res.body!;
      // @ts-ignore
      content.reportBlocksContentData.push(newRow);
      this.initializeFormControls();
    });
  }

  generateInitialData(content: IReportBlocksContent): string {
    const columns = this.getColumns(content.template ?? '{}');
    const initialData = { rows: columns.map(column => ({ data: '', index: column.index })) };
    return JSON.stringify(initialData);
  }

  removeRow(content: IReportBlocksContent, rowIndex: number, rowId: number): void {
    this.reportBlocksContentDataService.delete(rowId).subscribe(() => {
      console.log('deleted row ID: ', rowId);
    });

    content.reportBlocksContentData.splice(rowIndex, 1);
  }

  addColumn(content: IReportBlocksContent): void {
    try {
      // @ts-ignore
      const template = JSON.parse(content.template);
      if (Array.isArray(template.columns)) {
        let newColumnIndex = 1;
        if (template.columns.length > 0) {
          const columnIndices = template.columns.map((column: { name: string; index: any }) => column.index);
          newColumnIndex = Math.max(...columnIndices) + 1;
        }

        template.columns.push({ name: '', index: newColumnIndex });
        content.template = JSON.stringify(template);
        const formControlName1 = `column_${content.id}_${newColumnIndex}`;
        this.formGroup.addControl(formControlName1, new FormControl(''));

        for (const row of content.reportBlocksContentData) {
          // @ts-ignore
          const rowData = JSON.parse(row.data);
          const formControlName = `row_${content.id}_${row.id}_column_${newColumnIndex}`;
          this.formGroup.addControl(formControlName, new FormControl(''));
          rowData.rows.push({ data: '', index: newColumnIndex });
          row.data = JSON.stringify(rowData);
        }

        this.getTableColumns();
      }
    } catch (error) {
      console.error('Error parsing JSON:', error);
    }
  }

  removeColumn(content: IReportBlocksContent, index: number, columnIndex: number): void {
    try {
      // @ts-ignore
      const template = JSON.parse(content.template);
      if (Array.isArray(template.columns)) {
        template.columns.splice(index, 1);
        content.template = JSON.stringify(template);

        for (const row of content.reportBlocksContentData) {
          // @ts-ignore
          const rowData = JSON.parse(row.data);
          // @ts-ignore
          const rowIndex = rowData.rows.findIndex(rowRes => rowRes.index === columnIndex);
          if (rowIndex !== -1) {
            rowData.rows.splice(rowIndex, 1);
            row.data = JSON.stringify(rowData);

            const formControlName = `row_${content.id}_${row.id}_column_${columnIndex}`;
            this.formGroup.removeControl(formControlName);
          }
        }
        this.initializeFormControls();
        this.getTableColumns();
      }
    } catch (error) {
      console.error('Error parsing JSON:', error);
    }
  }

  getColumns(template: string): any[] {
    try {
      return JSON.parse(template).columns;
    } catch (error) {
      console.error('Error parsing template JSON:', error);
      return [];
    }
  }

  getName(template: string): string {
    try {
      return JSON.parse(template).name;
    } catch (error) {
      console.error('Error parsing template JSON:', error);
      return '';
    }
  }

  getRows(data: string): any[] {
    try {
      return JSON.parse(data).rows;
    } catch (error) {
      console.error('Error parsing template JSON:', error);
      return [];
    }
  }

  initializeFormControls(): void {
    // @ts-ignore
    for (const content of this.reportBlocks?.reportBlocksContents) {
      // @ts-ignore
      const contentData = JSON.parse(content.template);
      this.formGroup.addControl(`content_${content.id}_name`, new FormControl(contentData.name));

      if (content.type === 'text') {
        this.formGroup.addControl(`content_${content.id}_data`, new FormControl(contentData.data));
        for (const row of content.reportBlocksContentData) {
          const formControlName = `content_${content.id}_${row.id}_data`;
          this.formGroup.addControl(formControlName, new FormControl(JSON.parse(row.data ?? '{}').data));
        }
      } else {
        // @ts-ignore
        const columns = this.getColumns(content.template);
        for (const column of columns) {
          const formControlName = `column_${content.id}_${column.index}`;
          this.formGroup.addControl(formControlName, new FormControl(column.name));
        }
        for (const row of content.reportBlocksContentData) {
          for (const column of columns) {
            const formControlName = `row_${content.id}_${row.id}_column_${column.index}`;
            const desiredObject = this.getRows(row.data ?? '{}').find(row => row.index === column.index);
            if (desiredObject) {
              const dataValue = desiredObject.data;
              this.formGroup.addControl(formControlName, new FormControl(dataValue));
            } else {
              this.formGroup.addControl(formControlName, new FormControl(''));
            }
          }
        }
      }
    }
  }

  getFormControlByKey(key: string): FormControl {
    if ((this.formGroup.get(key) as FormControl) == null) console.log('teeeeeeeeee???', key);
    return this.formGroup.get(key) as FormControl;
  }

  getColumnFormControl(content: IReportBlocksContent, columnIndex: number): FormControl {
    const formControlName = `column_${content.id}_${columnIndex}`;
    if ((this.formGroup.get(formControlName) as FormControl) == null) console.log('reeeeeeeeee???', formControlName);
    return this.formGroup.get(formControlName) as FormControl;
  }

  getRowDataFormControl(content: IReportBlocksContent, rowIndex: number, columnIndex: number): FormControl {
    const formControlName = `row_${content.id}_${content.reportBlocksContentData[rowIndex].id}_column_${columnIndex}`;
    if ((this.formGroup.get(formControlName) as FormControl) == null) console.log('eeeeeeeeee???', formControlName);
    return this.formGroup.get(formControlName) as FormControl;
  }

  addStructuredSubBlock() {
    const contentIndices = this.reportBlocks?.reportBlocksContents?.map((content: IReportBlocksContent) => content.id);
    // @ts-ignore
    const newContentIndex = 1 + (contentIndices.length > 0 ? Math.max(...contentIndices) : 0);
    const priorityNumbers = this.reportBlocks?.reportBlocksContents?.map((content: IReportBlocksContent) => content.priorityNumber);
    // @ts-ignore
    const priorityNumber = 1 + (priorityNumbers.length > 0 ? Math.max(...priorityNumbers) : 0);
    const subBlock: IReportBlocksContent = {
      id: newContentIndex,
      type: 'table',
      priorityNumber: priorityNumber + 1,
      template: '{"name":"","columns":[]}',
      isActive: true,
      newContentData: true,
      reportBlocksContentData: [],
    };
    this.reportBlocks?.reportBlocksContents?.push(subBlock);
    // this.reportBlocksContents?.push(subBlock);
    this.initializeFormControls();
    this.getTableColumns();
  }

  addReferenceSubBlock() {
    const modalRef = this.modalService.open(ReferenceSelectionModal, {
      animation: true,
      size: 'lg',
    });
    modalRef.componentInstance.param = this;

    modalRef.result.then(params => {
      const contentIndices = this.reportBlocks?.reportBlocksContents?.map((content: IReportBlocksContent) => content.id);
      // @ts-ignore
      const newContentIndex = 1 + (contentIndices.length > 0 ? Math.max(...contentIndices) : 0);
      const priorityNumbers = this.reportBlocks?.reportBlocksContents?.map((content: IReportBlocksContent) => content.priorityNumber);
      // @ts-ignore
      const priorityNumber = 1 + (priorityNumbers.length > 0 ? Math.max(...priorityNumbers) : 0);
      const subBlock: IReportBlocksContent = {
        id: newContentIndex,
        type: 'reference',
        priorityNumber: priorityNumber + 1,
        template: `{"name":"","columns":[],"source":"${params.source}"}`,
        isActive: true,
        newContentData: true,
        reportBlocksContentData: [],
      };
      this.reportBlocks?.reportBlocksContents?.push(subBlock);
      // this.reportBlocksContents?.push(subBlock);
      this.initializeFormControls();
      this.getTableColumns();
      this.loadReferenceTableSettings();
    });
  }

  addTextSubBlock() {
    const contentIndices = this.reportBlocks?.reportBlocksContents?.map((content: IReportBlocksContent) => content.id);
    // @ts-ignore
    const newContentIndex = 1 + (contentIndices.length > 0 ? Math.max(...contentIndices) : 0);
    const priorityNumbers = this.reportBlocks?.reportBlocksContents?.map((content: IReportBlocksContent) => content.priorityNumber);
    // @ts-ignore
    const priorityNumber = 1 + (priorityNumbers.length > 0 ? Math.max(...priorityNumbers) : 0);
    const subBlock: IReportBlocksContent = {
      id: newContentIndex,
      type: 'text',
      priorityNumber: priorityNumber + 1,
      template: '{"name":"","data":""}',
      isActive: true,
      newContentData: true,
      reportBlocksContentData: [],
    };
    this.reportBlocks?.reportBlocksContents?.push(subBlock);
    // this.reportBlocksContents?.push(subBlock);
    this.initializeFormControls();
  }

  removeSubBlock(content: IReportBlocksContent) {
    if (this.reportBlocksContents) {
      const index = this.reportBlocksContents.indexOf(content);
      if (index !== -1) {
        this.reportBlocksContents.splice(index, 1);
      }
    }
    content.deleted = true;
  }

  onDataChanged(content: IReportBlocksContent, template: any) {
    content.template = JSON.stringify(template);
  }
}

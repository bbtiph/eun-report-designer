import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ReportBlocksFormService, ReportBlocksFormGroup } from './report-blocks-form.service';
import { IColumn, IReportBlocks, ITemplate } from '../report-blocks.model';
import { ReportBlocksService } from '../service/report-blocks.service';
import { ICountries } from 'app/entities/countries/countries.model';
import { CountriesService } from 'app/entities/countries/service/countries.service';
import { IReport } from 'app/entities/report/report.model';
import { ReportService } from 'app/entities/report/service/report.service';
import { IReportBlocksContent } from '../../report-blocks-content/report-blocks-content.model';
import { IReportBlocksContentData, NewReportBlocksContentData } from '../../report-blocks-content-data/report-blocks-content-data.model';
import { AbstractControl, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ReportBlocksContentDataService } from '../../report-blocks-content-data/service/report-blocks-content-data.service';

@Component({
  selector: 'jhi-report-blocks-update',
  templateUrl: './report-blocks-update.component.html',
})
export class ReportBlocksUpdateComponent implements OnInit {
  isSaving = false;
  reportBlocks: IReportBlocks | null = null;
  reportBlocksContents?: IReportBlocksContent[];

  countriesSharedCollection: ICountries[] = [];
  reportsSharedCollection: IReport[] = [];
  formGroup: FormGroup;

  editForm: ReportBlocksFormGroup = this.reportBlocksFormService.createReportBlocksFormGroup();

  constructor(
    protected reportBlocksService: ReportBlocksService,
    protected reportBlocksFormService: ReportBlocksFormService,
    protected countriesService: CountriesService,
    protected reportService: ReportService,
    protected activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    protected reportBlocksContentDataService: ReportBlocksContentDataService
  ) {
    this.formGroup = this.formBuilder.group({});
  }

  compareCountries = (o1: ICountries | null, o2: ICountries | null): boolean => this.countriesService.compareCountries(o1, o2);

  compareReport = (o1: IReport | null, o2: IReport | null): boolean => this.reportService.compareReport(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportBlocks }) => {
      this.reportBlocks = reportBlocks;
      if (reportBlocks) {
        this.updateForm(reportBlocks);
      }
      this.initializeFormControls();

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reportBlocks = this.reportBlocksFormService.getReportBlocks(this.editForm);

    // @ts-ignore
    for (const content of reportBlocks.reportBlocksContents) {
      // @ts-ignore
      const columns = this.getColumns(content.template);
      for (const row of content.reportBlocksContentData) {
        // @ts-ignore
        const rowData = JSON.parse(row.data);
        for (const column of columns) {
          const formControlName = `row_${content.id}_${row.id}_column_${column.index}`;
          const formControl = this.formGroup.get(formControlName) as FormControl;
          rowData.rows.find((r: any) => r.index === column.index).data = formControl.value;
        }
        row.data = JSON.stringify(rowData);
      }
    }

    if (reportBlocks.id !== null) {
      this.subscribeToSaveResponse(this.reportBlocksService.update(reportBlocks));
    } else {
      this.subscribeToSaveResponse(this.reportBlocksService.create(reportBlocks));
    }
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
      data: '',
      reportBlocksContent: content,
      country: null,
    };
    this.reportBlocksContentDataService.create(newRow).subscribe((res: HttpResponse<IReportBlocksContentData>) => {
      const newRow: IReportBlocksContentData = res.body!;
      // @ts-ignore
      content.reportBlocksContentData.push(newRow);

      const rowControls = content.reportBlocksContentData.map(row => {
        const controls: { [key: string]: AbstractControl } = {};

        // @ts-ignore
        const columns = this.getColumns(content.template ?? '{}');
        for (const column of columns) {
          const formControlName = `row_${content.id}_${row.id}_column_${column.index}`;
          controls[formControlName] = new FormControl('');
        }

        return controls;
      });

      // @ts-ignore
      this.formGroup.addControl('rowGroup', new FormGroup(rowControls));
    });
  }

  removeRow(content: IReportBlocksContent, rowIndex: number): void {
    content.reportBlocksContentData.splice(rowIndex, 1);
  }

  addColumn(content: IReportBlocksContent): void {
    try {
      // @ts-ignore
      const template = JSON.parse(content.template);
      if (Array.isArray(template.columns)) {
        const newColumnIndex = template.columns.length + 1;

        template.columns.push({ name: '', index: newColumnIndex });
        content.template = JSON.stringify(template);

        for (const row of content.reportBlocksContentData) {
          // @ts-ignore
          const rowData = JSON.parse(row.data);
          rowData.rows.push({ data: '', index: newColumnIndex });
          row.data = JSON.stringify(rowData);
          const formControlName = `row_${content.id}_${row.id}_column_${newColumnIndex}`;
          this.formGroup.addControl(formControlName, new FormControl(''));
        }
      }
    } catch (error) {
      console.error('Error parsing JSON:', error);
    }
  }

  removeColumn(content: IReportBlocksContent, columnIndex: number): void {
    try {
      // @ts-ignore
      const template = JSON.parse(content.template);
      if (Array.isArray(template.columns)) {
        template.columns.splice(columnIndex, 1);
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
            console.log('r>>', columnIndex, formControlName);
            // console.log('r2>>', this.formGroup.controls)
            console.log('data>>', row.data);
            console.log('template>>', content.template);
            this.formGroup.removeControl(formControlName);
          }
        }
        debugger;
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
}

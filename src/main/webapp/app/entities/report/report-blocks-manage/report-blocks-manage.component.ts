import { Component, ElementRef, Input, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IReportBlocks } from '../../report-blocks/report-blocks.model';
import { ReportBlocksService } from '../../report-blocks/service/report-blocks.service';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { IReport } from '../report.model';
import { AbstractExportModal } from '../../../shared/modal/abstract-export.modal';
import { ActivatedRoute } from '@angular/router';
import { ReportBlockEdit } from './report-block-edit/report-block-edit.component';

@Component({
  selector: 'jhi-report-blocks-manage',
  templateUrl: './report-blocks-manage.component.html',
})
export class ReportBlocksManageComponent implements OnInit {
  reportBlocks: IReportBlocks[] | undefined;
  report: IReport | null = null;

  @ViewChild('editor', { static: false }) editorElement?: ElementRef;
  editor: any;

  constructor(
    private modalService: NgbModal,
    protected reportBlocksService: ReportBlocksService,
    private http: HttpClient,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ report }) => {
      this.report = report;
    });
    this.reportBlocksService.findAll().subscribe(blocks => {
      this.reportBlocks = blocks;
    });
  }

  changeIsActive(block: IReportBlocks) {
    const updatedBlock = { ...block };
    updatedBlock.isActive = !updatedBlock.isActive;
    this.reportBlocksService.update(updatedBlock).subscribe();
  }

  openEditor(block: IReportBlocks) {
    const modalRef = this.modalService.open(ReportBlockEdit, { animation: true, size: 'xl' });
    modalRef.componentInstance.block = block;
  }

  generateReport(): void {
    const modalRef = this.modalService.open(AbstractExportModal, {
      animation: true,
      size: 'lg',
    });
    modalRef.componentInstance.param = this;
    modalRef.componentInstance.reportName = this.report?.reportName;

    modalRef.result.then(params => {
      console.log(params);

      const body = { id: 0, output: params.format.name, lang: 'ru' };

      this.http.post('api/reports/generate/' + this.report?.acronym, body, { responseType: 'blob' }).subscribe(response => {
        var a = document.createElement('a');
        a.href = URL.createObjectURL(response);
        // @ts-ignore
        a.download = this.report?.reportName;
        a.click();
      });
    });
  }
}

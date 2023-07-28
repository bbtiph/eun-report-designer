import { Component, ElementRef, Input, OnInit, ViewChild, AfterViewInit, OnDestroy } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IReportBlocks } from '../../report-blocks/report-blocks.model';
import { ReportBlocksService } from '../../report-blocks/service/report-blocks.service';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { IReport } from '../report.model';
import { AbstractExportModal } from '../../../shared/modal/abstract-export.modal';
import { ActivatedRoute } from '@angular/router';
import { CountriesService } from '../../countries/service/countries.service';
import { ICountries } from '../../countries/countries.model';
import { DragulaService } from 'ng2-dragula';

@Component({
  selector: 'jhi-report-blocks-manage',
  templateUrl: './report-blocks-manage.component.html',
  styleUrls: ['./report-blocks-manage.component.scss'],
})
export class ReportBlocksManageComponent implements OnInit, OnDestroy {
  reportBlocks: IReportBlocks[] | undefined;
  report: IReport | null = null;
  selectedCountry: ICountries | null = null;

  @ViewChild('editor', { static: false }) editorElement?: ElementRef;
  editor: any;

  constructor(
    private modalService: NgbModal,
    protected reportBlocksService: ReportBlocksService,
    private http: HttpClient,
    protected activatedRoute: ActivatedRoute,
    protected countriesService: CountriesService,
    private dragulaService: DragulaService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ report }) => {
      this.report = report;
    });
    // @ts-ignore
    let countryId = this.activatedRoute.params.value.country;
    this.countriesService.findById(countryId || 1).subscribe((country: ICountries) => {
      this.selectedCountry = country;

      // @ts-ignore
      this.reportBlocksService.findAllByReport(this.report?.id, this.selectedCountry?.id).subscribe(blocks => {
        this.reportBlocks = blocks;
        // @ts-ignore
        this.reportBlocks = this.reportBlocks?.sort((a, b) => a.priorityNumber - b.priorityNumber);
      });
    });

    this.dragulaService.createGroup('REPORT_BLOCKS', {
      revertOnSpill: true,
      copy: false,
      moves: function (el, container, cHandle) {
        // @ts-ignore
        return cHandle.className === 'handle';
      },
    });
    this.dragulaService.dropModel('REPORT_BLOCKS').subscribe(args => {});
  }

  drop(event: IReportBlocks[]) {
    // @ts-ignore
    this.reportBlocks.forEach((block, index) => {
      block.priorityNumber = index;
      this.reportBlocksService.update(block).subscribe();
    });
  }

  ngOnDestroy() {
    this.dragulaService.destroy('REPORT_BLOCKS');
  }

  changeIsActive(block: IReportBlocks) {
    const updatedBlock = { ...block };
    updatedBlock.isActive = !updatedBlock.isActive;
    this.reportBlocksService.update(updatedBlock).subscribe();
  }

  generateReport(): void {
    const modalRef = this.modalService.open(AbstractExportModal, {
      animation: true,
      size: 'lg',
    });
    modalRef.componentInstance.param = this;
    modalRef.componentInstance.reportName = this.report?.reportName;
    modalRef.componentInstance.countryName = this.selectedCountry?.countryName;

    modalRef.result.then(params => {
      debugger;
      const body = {
        data: JSON.stringify(this.reportBlocks),
        output: params.format.name,
        lang: 'ru',
        country: this.selectedCountry?.countryCode,
        reportId: this.report?.id,
      };
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

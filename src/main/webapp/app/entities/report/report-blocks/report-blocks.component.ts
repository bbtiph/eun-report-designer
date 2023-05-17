import { Component, ElementRef, Input, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IReportBlocks } from '../../report-blocks/report-blocks.model';
import { ReportBlocksService } from '../../report-blocks/service/report-blocks.service';
import { AngularEditorConfig } from '@kolkov/angular-editor';

@Component({
  selector: 'app-report-blocks',
  templateUrl: './report-blocks.component.html',
})
export class ReportBlocksComponent implements OnInit {
  reportBlocks?: IReportBlocks[];
  block?: IReportBlocks | null;
  editorConfig: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: 'auto',
    minHeight: '0',
    maxHeight: 'auto',
    width: 'auto',
    minWidth: '0',
    translate: 'yes',
    enableToolbar: true,
    showToolbar: true,
    placeholder: 'Enter text here...',
    defaultParagraphSeparator: '',
    defaultFontName: '',
    defaultFontSize: '',
    fonts: [
      { class: 'arial', name: 'Arial' },
      { class: 'times-new-roman', name: 'Times New Roman' },
      { class: 'calibri', name: 'Calibri' },
      { class: 'comic-sans-ms', name: 'Comic Sans MS' },
    ],
    customClasses: [
      {
        name: 'quote',
        class: 'quote',
      },
      {
        name: 'redText',
        class: 'redText',
      },
      {
        name: 'titleText',
        class: 'titleText',
        tag: 'h1',
      },
    ],
  };

  @ViewChild('editor', { static: false }) editorElement?: ElementRef;
  editor: any;

  constructor(private modalService: NgbModal, protected reportBlocksService: ReportBlocksService) {}

  ngOnInit() {
    this.reportBlocksService.findAll().subscribe(blocks => {
      this.reportBlocks = blocks;
    });
  }

  openEditor(content: any, block: IReportBlocks) {
    const modalRef = this.modalService.open(content, { centered: true });
    this.block = block;
  }
}

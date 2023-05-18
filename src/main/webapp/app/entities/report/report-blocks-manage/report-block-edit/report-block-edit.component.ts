import { Component, Input, OnInit } from '@angular/core';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { IReportBlocks } from '../../../report-blocks/report-blocks.model';
import { ActivatedRoute } from '@angular/router';
import { ReportBlocksService } from '../../../report-blocks/service/report-blocks.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'report-block-edit',
  templateUrl: './report-block-edit.component.html',
  styleUrls: ['./report-block-edit.component.scss'],
})
export class ReportBlockEdit implements OnInit {
  // @ts-ignore
  @Input() public block: IReportBlocks;
  name = 'Angular 6';
  htmlContent = '';

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected reportBlocksService: ReportBlocksService,
    protected modal: NgbActiveModal
  ) {}

  config: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: '45rem',
    minHeight: '10rem',
    placeholder: 'Enter text here...',
    translate: 'no',
    defaultParagraphSeparator: 'p',
    defaultFontName: 'Arial',
    toolbarHiddenButtons: [['bold']],
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

  ngOnInit(): void {}

  save() {
    this.reportBlocksService.update(this.block).subscribe();
    this.modal.close();
  }
}

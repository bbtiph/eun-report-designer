import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CountryService } from '../../entities/country/service/country.service';
import { ICountry } from '../../entities/country/country.model';
import { Observable } from 'rxjs';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'popup-window-html-modal',
  template: ` <div class="modal-xl">
    <div class="modal-header">
      <h4 class="modal-title text-center" id="modal-title"></h4>
      <button type="button" class="close" aria-describedby="modal-title" (click)="modal.dismiss()">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <iframe class="modal-iframe" [srcdoc]="reportHtml"></iframe>
    <div class="modal-footer">
      <button type="button" class="btn btn-outline-secondary" (click)="modal.dismiss()">Close</button>
    </div>
  </div>`,
  styleUrls: ['./abstract.modal.scss'],
})
export class PopupWindowHtmlModal implements OnInit {
  @Input() public reportHtml?: string;

  constructor(public modal: NgbActiveModal, private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    // @ts-ignore
    this.reportHtml = this.safeHTML(this.reportHtml);
  }

  safeHTML(content: any) {
    return this.sanitizer.bypassSecurityTrustHtml(content);
  }
}

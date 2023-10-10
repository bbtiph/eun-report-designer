import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CountryService } from '../../entities/country/service/country.service';
import { ICountry } from '../../entities/country/country.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'popup-window-html-modal',
  template: ` <div class="modal-header">
      <h4 class="modal-title text-center" id="modal-title">Export</h4>
      <button type="button" class="close" aria-describedby="modal-title" (click)="modal.dismiss()">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
          <div [innerHTML]="reportHtml"></div>
        </div>
      </div>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-outline-secondary" (click)="modal.dismiss()">Close</button>
    </div>`,
  styleUrls: ['./popup-window-html.modal.scss'],
})
export class PopupWindowHtmlModal implements OnInit {
  @Input() public reportHtml?: string;

  constructor(public modal: NgbActiveModal) {}

  ngOnInit(): void {}
}

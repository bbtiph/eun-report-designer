import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-reference-selection-modal',
  template: ` <div class="modal-header">
      <h4 class="modal-title text-center" id="modal-title">Choose reference:</h4>
      <button type="button" class="close" aria-describedby="modal-title" (click)="modal.dismiss()">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
          <div class="form-group">
            <label class="col-form-label">References:</label>
            <select class="form-control" [(ngModel)]="source" data-cy="status">
              <option [value]="null"></option>
              <option [value]="'working_group'">Working group</option>
              <option [value]="'projects'">Projects</option>
            </select>
          </div>
        </div>
      </div>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-outline-secondary" (click)="modal.dismiss()">Close</button>
      <button type="button" class="btn btn-success" (click)="modal.close({ source: source })">Add</button>
    </div>`,
})
export class ReferenceSelectionModal implements OnInit {
  public source: string | undefined;

  constructor(public modal: NgbActiveModal) {}

  ngOnInit(): void {}
}

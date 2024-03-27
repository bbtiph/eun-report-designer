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
              <option [value]="'job_info'">ERP Project Data</option>
              <option [value]="'event_reference'">Event references</option>
              <option [value]="'moe_participation'">MoE Participation references</option>
              <option [value]="'funding_and_project_of_project'">Funding&project of the Project</option>
              <option [value]="'funding_and_project_for_eun'">Funding&project for EUN</option>
              <option [value]="'participants_eun_indicator'">Courses</option>
              <option [value]="'organization_eun_indicator'">Organizations</option>
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

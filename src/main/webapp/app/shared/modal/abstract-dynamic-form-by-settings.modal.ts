import { Component, Inject, Input, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'abstract-dynamic-form-by-settings',
  templateUrl: './abstract-dynamic-form-by-settings.modal.html',
  styleUrls: ['./abstract.modal.scss'],
})
export class AbstractDynamicFormBySettingsModal implements OnInit {
  // @ts-ignore
  filterForm: FormGroup;
  // @ts-ignore
  filterFields: any[];

  // @ts-ignore
  @Input() public settings: string;
  // @ts-ignore
  @Input() public action: string;
  // @ts-ignore
  @Input() public row: any;

  constructor(public modal: NgbActiveModal, private fb: FormBuilder) {}

  ngOnInit() {
    this.filterFields = JSON.parse(this.settings);
    this.filterForm = this.generateFilterForm();
    console.log(this.row);
  }

  generateFilterForm(): FormGroup {
    const baseForm = this.fb.group({});
    this.filterFields.forEach(field => {
      baseForm.addControl(field.index, this.generateFormGroup(baseForm, field, field.index));
    });
    return baseForm;
  }

  generateFormGroup(baseForm: FormGroup, field: { children: any[] }, index: string): FormGroup | FormControl {
    if (field.children) {
      const formGroup = this.fb.group({});
      field.children.forEach(item => {
        formGroup.addControl(item.index, this.generateFormGroup(formGroup, item, item.index));
      });
      return formGroup;
    }

    return new FormControl(this.row[index]);
  }
}

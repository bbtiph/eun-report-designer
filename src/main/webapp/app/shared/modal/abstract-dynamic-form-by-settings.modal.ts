import { Component, Inject, Input, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CountryService } from '../../entities/country/service/country.service';
import { ICountry } from '../../entities/country/country.model';
import { ICountries } from '../../entities/countries/countries.model';
import { Validators } from '@angular/forms';
import { ReferenceTableSettingsService } from '../../entities/reference-table-settings/service/reference-table-settings.service';

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

  countriesSharedCollection: ICountries[] = [];

  constructor(
    public modal: NgbActiveModal,
    private fb: FormBuilder,
    protected countriesService: CountryService,
    protected referenceTableSettingsService: ReferenceTableSettingsService
  ) {}

  ngOnInit() {
    this.filterFields = JSON.parse(this.settings);
    this.filterForm = this.generateFilterForm();
    this.countriesService.findAll().subscribe((countries: ICountry[]) => {
      this.countriesSharedCollection = countries;
    });
  }

  generateFilterForm(): FormGroup {
    const baseForm = this.fb.group({});
    this.filterFields.forEach(field => {
      baseForm.addControl(field.index, this.generateFormGroup(baseForm, field, field.index));
    });
    return baseForm;
  }

  generateFormGroup(baseForm: FormGroup, field: { children: any[]; disabled: boolean }, index: string): FormGroup | FormControl {
    if (field.children) {
      const formGroup = this.fb.group({});
      field.children.forEach(item => {
        formGroup.addControl(item.index, this.generateFormGroup(formGroup, item, item.index));
      });
      return formGroup;
    }

    return new FormControl(
      {
        value: this.row != null ? this.row[index] : '',
        disabled: field.disabled,
      },
      Validators.required
    );
  }

  convert(): any {
    const result: { [key: string]: any } = {};

    const extractControls = (controls: { [key: string]: AbstractControl }) => {
      for (const key of Object.keys(controls)) {
        const control = controls[key];

        if (control instanceof FormGroup) {
          Object.assign(result, extractControls(control.controls));
        } else {
          result[key] = control.value;
        }
      }
    };
    extractControls(this.filterForm.controls);

    if (this.row) {
      Object.keys(this.row)
        .filter(key => !result.hasOwnProperty(key))
        .forEach(key => (result[key] = this.row[key]));
    }
    return result;
  }

  onChangeCountry(event: any, relationField: string) {
    this.filterForm.controls[relationField].setValue(event[relationField]);
  }
}

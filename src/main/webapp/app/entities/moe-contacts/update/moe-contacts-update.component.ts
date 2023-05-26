import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MoeContactsFormService, MoeContactsFormGroup } from './moe-contacts-form.service';
import { IMoeContacts } from '../moe-contacts.model';
import { MoeContactsService } from '../service/moe-contacts.service';

@Component({
  selector: 'jhi-moe-contacts-update',
  templateUrl: './moe-contacts-update.component.html',
})
export class MoeContactsUpdateComponent implements OnInit {
  isSaving = false;
  moeContacts: IMoeContacts | null = null;

  editForm: MoeContactsFormGroup = this.moeContactsFormService.createMoeContactsFormGroup();

  constructor(
    protected moeContactsService: MoeContactsService,
    protected moeContactsFormService: MoeContactsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moeContacts }) => {
      this.moeContacts = moeContacts;
      if (moeContacts) {
        this.updateForm(moeContacts);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const moeContacts = this.moeContactsFormService.getMoeContacts(this.editForm);
    if (moeContacts.id !== null) {
      this.subscribeToSaveResponse(this.moeContactsService.update(moeContacts));
    } else {
      this.subscribeToSaveResponse(this.moeContactsService.create(moeContacts));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMoeContacts>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(moeContacts: IMoeContacts): void {
    this.moeContacts = moeContacts;
    this.moeContactsFormService.resetForm(this.editForm, moeContacts);
  }
}

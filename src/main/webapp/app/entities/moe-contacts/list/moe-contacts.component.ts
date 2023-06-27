import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMoeContacts } from '../moe-contacts.model';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, MoeContactsService } from '../service/moe-contacts.service';
import { MoeContactsDeleteDialogComponent } from '../delete/moe-contacts-delete-dialog.component';
import { SortService } from 'app/shared/sort/sort.service';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'jhi-moe-contacts',
  templateUrl: './moe-contacts.component.html',
  styleUrls: ['./moe-contacts.component.scss'],
})
export class MoeContactsComponent implements OnInit {
  moeContacts?: IMoeContacts[];
  isLoading = false;

  predicate = 'id';
  ascending = true;

  showModal = false;
  // @ts-ignore
  selectedFile: File;

  constructor(
    protected moeContactsService: MoeContactsService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal,
    protected http: HttpClient
  ) {}

  trackId = (_index: number, item: IMoeContacts): number => this.moeContactsService.getMoeContactsIdentifier(item);

  ngOnInit(): void {
    this.load();
  }

  delete(moeContacts: IMoeContacts): void {
    const modalRef = this.modalService.open(MoeContactsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.moeContacts = moeContacts;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.predicate, this.ascending);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.moeContacts = this.refineData(dataFromBody);
  }

  protected refineData(data: IMoeContacts[]): IMoeContacts[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: IMoeContacts[] | null): IMoeContacts[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.moeContactsService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(predicate?: string, ascending?: boolean): void {
    const queryParamsObj = {
      sort: this.getSortQueryParam(predicate, ascending),
    };

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }

  openModal() {
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  onFileChange(event: any) {
    this.selectedFile = event.target.files[0];
  }

  uploadFile() {
    const formData = new FormData();
    formData.append('file', this.selectedFile);

    this.moeContactsService.upload(formData).subscribe(() => {
      this.closeModal();
      this.load();
    });

    // this.http.post('api/moe-contacts/upload', formData).subscribe(
    //   (res) => {
    //     debugger
    //     // Успешное выполнение запроса
    //     this.closeModal();
    //   },
    //   error => {
    //     debugger
    //     // Обработка ошибок при загрузке файла
    //   }
    // );
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<String>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {}

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {}
}

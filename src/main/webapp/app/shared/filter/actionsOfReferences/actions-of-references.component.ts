import { Component, OnDestroy } from '@angular/core';
import { ICellRendererAngularComp } from '@ag-grid-community/angular';
import { ICellRendererParams } from '@ag-grid-community/core';

@Component({
  selector: 'app-button-of-references-renderer',
  templateUrl: './actions-of-references.component.html',
})
export class ButtonRendererOfReferencesComponent implements ICellRendererAngularComp, OnDestroy {
  private params: any;
  public context: string | undefined;
  public id: number | undefined;

  agInit(params: any): void {
    this.params = params;
    this.context = this.params.column.colId;
    this.id = this.params.data.id;
  }

  btnClickedHandler(e: any) {
    this.params.deleteAction(this.params.data);
  }

  btnViewRow(e: any) {
    this.params.viewAction(this.params.data);
  }

  btnEditRow(e: any) {
    this.params.editAction(this.params.data);
  }

  ngOnDestroy() {}

  refresh(params: ICellRendererParams<any>): boolean {
    return false;
  }
}

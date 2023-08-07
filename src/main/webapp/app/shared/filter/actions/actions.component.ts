import { Component, OnDestroy } from '@angular/core';
import { ICellRendererAngularComp } from '@ag-grid-community/angular';
import { ICellRendererParams } from '@ag-grid-community/core';

@Component({
  selector: 'app-button-renderer',
  templateUrl: './actions.component.html',
})
export class ButtonRendererComponent implements ICellRendererAngularComp, OnDestroy {
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

  ngOnDestroy() {}

  refresh(params: ICellRendererParams<any>): boolean {
    return false;
  }
}

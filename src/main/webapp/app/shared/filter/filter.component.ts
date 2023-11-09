import { Component, Input } from '@angular/core';
import {
  CellClickedEvent,
  CellContextMenuEvent,
  CellDoubleClickedEvent,
  ColDef,
  ColGroupDef,
  ColumnApi,
  GridApi,
  GridReadyEvent,
  Module,
} from '@ag-grid-community/core';
import { RendererComponent } from './renderer-component/renderer.component';
import { RangeSelectionModule } from '@ag-grid-enterprise/range-selection';
import { SetFilterModule } from '@ag-grid-enterprise/set-filter';
import { RowGroupingModule } from '@ag-grid-enterprise/row-grouping';
import { GridChartsModule } from '@ag-grid-enterprise/charts';
import { StatusBarModule } from '@ag-grid-enterprise/status-bar';
import { FiltersToolPanelModule } from '@ag-grid-enterprise/filter-tool-panel';
import { ColumnsToolPanelModule } from '@ag-grid-enterprise/column-tool-panel';
import { SideBarModule } from '@ag-grid-enterprise/side-bar';
import { MenuModule } from '@ag-grid-enterprise/menu';
import { ClientSideRowModelModule } from '@ag-grid-community/client-side-row-model';
import { SortableHeaderComponent } from './header-component/sortable-header.component';
import { HeaderGroupComponent } from './header-group-component/header-group.component';
import { DateComponent } from './date-component/date.component';
import { IWorkingGroupReferences } from '../../entities/working-group-references/working-group-references.model';
import { ButtonRendererComponent } from './actions/actions.component';
import { ButtonRendererOfReferencesComponent } from './actionsOfReferences/actions-of-references.component';

@Component({
  selector: 'jhi-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css'],
})
export class FilterComponent {
  @Input() columnDefs!: (ColDef | ColGroupDef)[];
  @Input() rowData?: IWorkingGroupReferences[] = [];
  @Input() totalItems: number | 0 | undefined;
  public rowCount!: string;

  public defaultColDef: ColDef;
  public components: any;

  public modules: Module[] = [
    ClientSideRowModelModule,
    MenuModule,
    SideBarModule,
    ColumnsToolPanelModule,
    FiltersToolPanelModule,
    StatusBarModule,
    GridChartsModule,
    RowGroupingModule,
    SetFilterModule,
    RangeSelectionModule,
  ];

  public api!: GridApi;
  public columnApi!: ColumnApi;

  constructor() {
    this.defaultColDef = {
      resizable: true,
      sortable: true,
      filter: true,
      floatingFilter: true,
      headerComponent: 'sortableHeaderComponent',
      headerComponentParams: {
        menuIcon: 'fa-bars',
      },
    };

    this.components = {
      sortableHeaderComponent: SortableHeaderComponent,
      agDateInput: DateComponent,
      headerGroupComponent: HeaderGroupComponent,
      rendererComponent: RendererComponent,
      btnCellRenderer: ButtonRendererComponent,
      btnCellRendererOfReferences: ButtonRendererOfReferencesComponent,
    };
  }

  onFilterChanged(): void {
    const filters = this.api?.getFilterModel().toString();
    console.log('filter: ', filters.toString());

    const filterData = { filters };
    console.log('filter: ', filterData.toString());
  }

  private calculateRowCount() {
    if (this.api && this.rowData) {
      const model = this.api.getModel();
      const totalRows = this.rowData.length;
      const processedRows = model.getRowCount();
      this.rowCount = processedRows.toLocaleString() + ' / ' + totalRows.toLocaleString();
    }
  }

  public onModelUpdated() {
    this.calculateRowCount();
  }

  public onGridReady(params: GridReadyEvent) {
    this.api = params.api;
    this.columnApi = params.columnApi;

    // this.api.sizeColumnsToFit();

    this.calculateRowCount();
  }

  public onCellClicked($event: CellClickedEvent) {
    console.log('onCellClicked: ' + $event.rowIndex + ' ' + $event.colDef.field);
  }

  public onCellDoubleClicked($event: CellDoubleClickedEvent) {
    console.log('onCellDoubleClicked: ' + $event.rowIndex + ' ' + $event.colDef.field);
  }

  public onCellContextMenu($event: CellContextMenuEvent) {
    console.log('onCellContextMenu: ' + $event.rowIndex + ' ' + $event.colDef.field);
  }
}

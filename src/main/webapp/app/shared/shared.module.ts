import { NgModule } from '@angular/core';

import { SharedLibsModule } from './shared-libs.module';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { DurationPipe } from './date/duration.pipe';
import { FormatMediumDatetimePipe } from './date/format-medium-datetime.pipe';
import { FormatMediumDatePipe } from './date/format-medium-date.pipe';
import { SortByDirective } from './sort/sort-by.directive';
import { SortDirective } from './sort/sort.directive';
import { ItemCountComponent } from './pagination/item-count.component';
import { FilterComponent } from './filter/filter.component';
import { AbstractExportModal } from './modal/abstract-export.modal';
import { ReferenceSelectionModal } from './modal/reference-selection.modal';
import { AgGridModule } from '@ag-grid-community/angular';
import { DateComponent } from './filter/date-component/date.component';
import { SortableHeaderComponent } from './filter/header-component/sortable-header.component';
import { HeaderGroupComponent } from './filter/header-group-component/header-group.component';
import { RendererComponent } from './filter/renderer-component/renderer.component';
import { ButtonRendererComponent } from './filter/actions/actions.component';
import { RouterLink } from '@angular/router';
import { DialogConfirmComponent } from './dialog-confirm/dialog-confirm.component';
import { FileDragNDropDirective } from './file-drag-n-drop/file-drag-n-drop.directive';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { PopupWindowHtmlModal } from './modal/popup-window-html.modal';
import { AbstractCloneReportModal } from './modal/abstract-clone-report.modal';
import { AbstractUploadFileModal } from './modal/abstract-upload-file.modal';
import { AbstractDynamicFormBySettingsModal } from './modal/abstract-dynamic-form-by-settings.modal';
import { ButtonRendererOfReferencesComponent } from './filter/actionsOfReferences/actions-of-references.component';
import { JsonEditorToolbarComponent } from './json-editor/json-editor-toolbar.component';
import { NgJsonEditorModule } from 'ang-jsoneditor';
import { NgSelectModule } from '@ng-select/ng-select';
import { HintDirective } from './hint/hint.directive';

@NgModule({
  imports: [SharedLibsModule, AgGridModule, RouterLink, MatProgressSpinnerModule, NgJsonEditorModule, NgSelectModule],
  declarations: [
    AlertComponent,
    AlertErrorComponent,
    HasAnyAuthorityDirective,
    DurationPipe,
    DateComponent,
    SortableHeaderComponent,
    HeaderGroupComponent,
    RendererComponent,
    ButtonRendererComponent,
    ButtonRendererOfReferencesComponent,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    SortByDirective,
    SortDirective,
    ItemCountComponent,
    FilterComponent,
    AbstractExportModal,
    AbstractUploadFileModal,
    JsonEditorToolbarComponent,
    AbstractDynamicFormBySettingsModal,
    PopupWindowHtmlModal,
    AbstractCloneReportModal,
    ReferenceSelectionModal,
    DialogConfirmComponent,
    FileDragNDropDirective,
    HintDirective,
  ],
  exports: [
    SharedLibsModule,
    AlertComponent,
    AlertErrorComponent,
    HasAnyAuthorityDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    SortByDirective,
    SortDirective,
    ItemCountComponent,
    FilterComponent,
    FileDragNDropDirective,
    JsonEditorToolbarComponent,
    HintDirective,
  ],
})
export class SharedModule {}

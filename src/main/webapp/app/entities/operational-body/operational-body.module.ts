import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OperationalBodyComponent } from './list/operational-body.component';
import { OperationalBodyDetailComponent } from './detail/operational-body-detail.component';
import { OperationalBodyUpdateComponent } from './update/operational-body-update.component';
import { OperationalBodyDeleteDialogComponent } from './delete/operational-body-delete-dialog.component';
import { OperationalBodyRoutingModule } from './route/operational-body-routing.module';

@NgModule({
  imports: [SharedModule, OperationalBodyRoutingModule],
  declarations: [
    OperationalBodyComponent,
    OperationalBodyDetailComponent,
    OperationalBodyUpdateComponent,
    OperationalBodyDeleteDialogComponent,
  ],
})
export class OperationalBodyModule {}

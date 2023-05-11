import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PrivilegeComponent } from './list/privilege.component';
import { PrivilegeDetailComponent } from './detail/privilege-detail.component';
import { PrivilegeUpdateComponent } from './update/privilege-update.component';
import { PrivilegeDeleteDialogComponent } from './delete/privilege-delete-dialog.component';
import { PrivilegeRoutingModule } from './route/privilege-routing.module';

@NgModule({
  imports: [SharedModule, PrivilegeRoutingModule],
  declarations: [PrivilegeComponent, PrivilegeDetailComponent, PrivilegeUpdateComponent, PrivilegeDeleteDialogComponent],
})
export class PrivilegeModule {}

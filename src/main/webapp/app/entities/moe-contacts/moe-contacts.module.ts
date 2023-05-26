import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MoeContactsComponent } from './list/moe-contacts.component';
import { MoeContactsDetailComponent } from './detail/moe-contacts-detail.component';
import { MoeContactsUpdateComponent } from './update/moe-contacts-update.component';
import { MoeContactsDeleteDialogComponent } from './delete/moe-contacts-delete-dialog.component';
import { MoeContactsRoutingModule } from './route/moe-contacts-routing.module';

@NgModule({
  imports: [SharedModule, MoeContactsRoutingModule],
  declarations: [MoeContactsComponent, MoeContactsDetailComponent, MoeContactsUpdateComponent, MoeContactsDeleteDialogComponent],
})
export class MoeContactsModule {}

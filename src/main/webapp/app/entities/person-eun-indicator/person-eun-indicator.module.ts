import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PersonEunIndicatorComponent } from './list/person-eun-indicator.component';
import { PersonEunIndicatorDetailComponent } from './detail/person-eun-indicator-detail.component';
import { PersonEunIndicatorUpdateComponent } from './update/person-eun-indicator-update.component';
import { PersonEunIndicatorDeleteDialogComponent } from './delete/person-eun-indicator-delete-dialog.component';
import { PersonEunIndicatorRoutingModule } from './route/person-eun-indicator-routing.module';

@NgModule({
  imports: [SharedModule, PersonEunIndicatorRoutingModule],
  declarations: [
    PersonEunIndicatorComponent,
    PersonEunIndicatorDetailComponent,
    PersonEunIndicatorUpdateComponent,
    PersonEunIndicatorDeleteDialogComponent,
  ],
})
export class PersonEunIndicatorModule {}

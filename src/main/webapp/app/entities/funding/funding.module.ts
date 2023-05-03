import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FundingComponent } from './list/funding.component';
import { FundingDetailComponent } from './detail/funding-detail.component';
import { FundingUpdateComponent } from './update/funding-update.component';
import { FundingDeleteDialogComponent } from './delete/funding-delete-dialog.component';
import { FundingRoutingModule } from './route/funding-routing.module';

@NgModule({
  imports: [SharedModule, FundingRoutingModule],
  declarations: [FundingComponent, FundingDetailComponent, FundingUpdateComponent, FundingDeleteDialogComponent],
})
export class FundingModule {}

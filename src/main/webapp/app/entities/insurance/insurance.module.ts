import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JiotmsSharedModule } from 'app/shared/shared.module';
import { InsuranceComponent } from './insurance.component';
import { InsuranceDetailComponent } from './insurance-detail.component';
import { InsuranceUpdateComponent } from './insurance-update.component';
import { InsuranceDeleteDialogComponent } from './insurance-delete-dialog.component';
import { insuranceRoute } from './insurance.route';

@NgModule({
  imports: [JiotmsSharedModule, RouterModule.forChild(insuranceRoute)],
  declarations: [InsuranceComponent, InsuranceDetailComponent, InsuranceUpdateComponent, InsuranceDeleteDialogComponent],
  entryComponents: [InsuranceDeleteDialogComponent]
})
export class JiotmsInsuranceModule {}

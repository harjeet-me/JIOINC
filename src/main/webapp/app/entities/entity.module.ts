import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'load-order',
        loadChildren: () => import('./load-order/load-order.module').then(m => m.JiotmsLoadOrderModule)
      },
      {
        path: 'invoice',
        loadChildren: () => import('./invoice/invoice.module').then(m => m.JiotmsInvoiceModule)
      },
      {
        path: 'invoice-item',
        loadChildren: () => import('./invoice-item/invoice-item.module').then(m => m.JiotmsInvoiceItemModule)
      },
      {
        path: 'insurance',
        loadChildren: () => import('./insurance/insurance.module').then(m => m.JiotmsInsuranceModule)
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.JiotmsCustomerModule)
      },
      {
        path: 'contact',
        loadChildren: () => import('./contact/contact.module').then(m => m.JiotmsContactModule)
      },
      {
        path: 'driver',
        loadChildren: () => import('./driver/driver.module').then(m => m.JiotmsDriverModule)
      },
      {
        path: 'owner-operator',
        loadChildren: () => import('./owner-operator/owner-operator.module').then(m => m.JiotmsOwnerOperatorModule)
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.JiotmsLocationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JiotmsEntityModule {}

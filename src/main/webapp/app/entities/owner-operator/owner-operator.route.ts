import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { OwnerOperator } from 'app/shared/model/owner-operator.model';
import { OwnerOperatorService } from './owner-operator.service';
import { OwnerOperatorComponent } from './owner-operator.component';
import { OwnerOperatorDetailComponent } from './owner-operator-detail.component';
import { OwnerOperatorUpdateComponent } from './owner-operator-update.component';
import { IOwnerOperator } from 'app/shared/model/owner-operator.model';

@Injectable({ providedIn: 'root' })
export class OwnerOperatorResolve implements Resolve<IOwnerOperator> {
  constructor(private service: OwnerOperatorService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOwnerOperator> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((ownerOperator: HttpResponse<OwnerOperator>) => ownerOperator.body));
    }
    return of(new OwnerOperator());
  }
}

export const ownerOperatorRoute: Routes = [
  {
    path: '',
    component: OwnerOperatorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsApp.ownerOperator.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OwnerOperatorDetailComponent,
    resolve: {
      ownerOperator: OwnerOperatorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsApp.ownerOperator.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OwnerOperatorUpdateComponent,
    resolve: {
      ownerOperator: OwnerOperatorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsApp.ownerOperator.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OwnerOperatorUpdateComponent,
    resolve: {
      ownerOperator: OwnerOperatorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsApp.ownerOperator.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

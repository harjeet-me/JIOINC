import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOwnerOperator } from 'app/shared/model/owner-operator.model';

@Component({
  selector: 'jhi-owner-operator-detail',
  templateUrl: './owner-operator-detail.component.html'
})
export class OwnerOperatorDetailComponent implements OnInit {
  ownerOperator: IOwnerOperator;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ownerOperator }) => {
      this.ownerOperator = ownerOperator;
    });
  }

  previousState() {
    window.history.back();
  }
}

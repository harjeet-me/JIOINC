import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOwnerOperator } from 'app/shared/model/owner-operator.model';
import { OwnerOperatorService } from './owner-operator.service';
import { OwnerOperatorDeleteDialogComponent } from './owner-operator-delete-dialog.component';

@Component({
  selector: 'jhi-owner-operator',
  templateUrl: './owner-operator.component.html'
})
export class OwnerOperatorComponent implements OnInit, OnDestroy {
  ownerOperators: IOwnerOperator[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected ownerOperatorService: OwnerOperatorService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.ownerOperatorService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IOwnerOperator[]>) => (this.ownerOperators = res.body));
      return;
    }
    this.ownerOperatorService.query().subscribe((res: HttpResponse<IOwnerOperator[]>) => {
      this.ownerOperators = res.body;
      this.currentSearch = '';
    });
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInOwnerOperators();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOwnerOperator) {
    return item.id;
  }

  registerChangeInOwnerOperators() {
    this.eventSubscriber = this.eventManager.subscribe('ownerOperatorListModification', () => this.loadAll());
  }

  delete(ownerOperator: IOwnerOperator) {
    const modalRef = this.modalService.open(OwnerOperatorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ownerOperator = ownerOperator;
  }
}

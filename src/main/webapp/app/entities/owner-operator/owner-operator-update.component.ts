import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IOwnerOperator, OwnerOperator } from 'app/shared/model/owner-operator.model';
import { OwnerOperatorService } from './owner-operator.service';
import { IInsurance } from 'app/shared/model/insurance.model';
import { InsuranceService } from 'app/entities/insurance/insurance.service';

@Component({
  selector: 'jhi-owner-operator-update',
  templateUrl: './owner-operator-update.component.html'
})
export class OwnerOperatorUpdateComponent implements OnInit {
  isSaving: boolean;

  operinsurances: IInsurance[];

  editForm = this.fb.group({
    id: [],
    company: [],
    firstName: [],
    lastName: [],
    contactDesignation: [],
    email: [],
    phoneNumber: [],
    address: [],
    streetAddress: [],
    city: [],
    stateProvince: [],
    country: [],
    postalCode: [],
    dot: [],
    mc: [],
    remarks: [],
    operInsurance: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ownerOperatorService: OwnerOperatorService,
    protected insuranceService: InsuranceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ownerOperator }) => {
      this.updateForm(ownerOperator);
    });
    this.insuranceService.query({ filter: 'owneroperator-is-null' }).subscribe(
      (res: HttpResponse<IInsurance[]>) => {
        if (!this.editForm.get('operInsurance').value || !this.editForm.get('operInsurance').value.id) {
          this.operinsurances = res.body;
        } else {
          this.insuranceService
            .find(this.editForm.get('operInsurance').value.id)
            .subscribe(
              (subRes: HttpResponse<IInsurance>) => (this.operinsurances = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(ownerOperator: IOwnerOperator) {
    this.editForm.patchValue({
      id: ownerOperator.id,
      company: ownerOperator.company,
      firstName: ownerOperator.firstName,
      lastName: ownerOperator.lastName,
      contactDesignation: ownerOperator.contactDesignation,
      email: ownerOperator.email,
      phoneNumber: ownerOperator.phoneNumber,
      address: ownerOperator.address,
      streetAddress: ownerOperator.streetAddress,
      city: ownerOperator.city,
      stateProvince: ownerOperator.stateProvince,
      country: ownerOperator.country,
      postalCode: ownerOperator.postalCode,
      dot: ownerOperator.dot,
      mc: ownerOperator.mc,
      remarks: ownerOperator.remarks,
      operInsurance: ownerOperator.operInsurance
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ownerOperator = this.createFromForm();
    if (ownerOperator.id !== undefined) {
      this.subscribeToSaveResponse(this.ownerOperatorService.update(ownerOperator));
    } else {
      this.subscribeToSaveResponse(this.ownerOperatorService.create(ownerOperator));
    }
  }

  private createFromForm(): IOwnerOperator {
    return {
      ...new OwnerOperator(),
      id: this.editForm.get(['id']).value,
      company: this.editForm.get(['company']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      contactDesignation: this.editForm.get(['contactDesignation']).value,
      email: this.editForm.get(['email']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      address: this.editForm.get(['address']).value,
      streetAddress: this.editForm.get(['streetAddress']).value,
      city: this.editForm.get(['city']).value,
      stateProvince: this.editForm.get(['stateProvince']).value,
      country: this.editForm.get(['country']).value,
      postalCode: this.editForm.get(['postalCode']).value,
      dot: this.editForm.get(['dot']).value,
      mc: this.editForm.get(['mc']).value,
      remarks: this.editForm.get(['remarks']).value,
      operInsurance: this.editForm.get(['operInsurance']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOwnerOperator>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackInsuranceById(index: number, item: IInsurance) {
    return item.id;
  }
}

import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IInsurance, Insurance } from 'app/shared/model/insurance.model';
import { InsuranceService } from './insurance.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';
import { IOwnerOperator } from 'app/shared/model/owner-operator.model';
import { OwnerOperatorService } from 'app/entities/owner-operator/owner-operator.service';

@Component({
  selector: 'jhi-insurance-update',
  templateUrl: './insurance-update.component.html'
})
export class InsuranceUpdateComponent implements OnInit {
  isSaving: boolean;

  customers: ICustomer[];

  owneroperators: IOwnerOperator[];
  issueDateDp: any;
  expiryDateDp: any;

  editForm = this.fb.group({
    id: [],
    providerName: [],
    issueDate: [],
    expiryDate: [],
    policyDocument: [],
    policyDocumentContentType: [],
    coverageStatement: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected insuranceService: InsuranceService,
    protected customerService: CustomerService,
    protected ownerOperatorService: OwnerOperatorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ insurance }) => {
      this.updateForm(insurance);
    });
    this.customerService
      .query()
      .subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.ownerOperatorService
      .query()
      .subscribe(
        (res: HttpResponse<IOwnerOperator[]>) => (this.owneroperators = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(insurance: IInsurance) {
    this.editForm.patchValue({
      id: insurance.id,
      providerName: insurance.providerName,
      issueDate: insurance.issueDate,
      expiryDate: insurance.expiryDate,
      policyDocument: insurance.policyDocument,
      policyDocumentContentType: insurance.policyDocumentContentType,
      coverageStatement: insurance.coverageStatement
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const insurance = this.createFromForm();
    if (insurance.id !== undefined) {
      this.subscribeToSaveResponse(this.insuranceService.update(insurance));
    } else {
      this.subscribeToSaveResponse(this.insuranceService.create(insurance));
    }
  }

  private createFromForm(): IInsurance {
    return {
      ...new Insurance(),
      id: this.editForm.get(['id']).value,
      providerName: this.editForm.get(['providerName']).value,
      issueDate: this.editForm.get(['issueDate']).value,
      expiryDate: this.editForm.get(['expiryDate']).value,
      policyDocumentContentType: this.editForm.get(['policyDocumentContentType']).value,
      policyDocument: this.editForm.get(['policyDocument']).value,
      coverageStatement: this.editForm.get(['coverageStatement']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInsurance>>) {
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

  trackCustomerById(index: number, item: ICustomer) {
    return item.id;
  }

  trackOwnerOperatorById(index: number, item: IOwnerOperator) {
    return item.id;
  }
}

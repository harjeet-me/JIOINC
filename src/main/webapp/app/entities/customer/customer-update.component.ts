import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from 'app/entities/contact/contact.service';

@Component({
  selector: 'jhi-customer-update',
  templateUrl: './customer-update.component.html'
})
export class CustomerUpdateComponent implements OnInit {
  isSaving: boolean;

  contacts: IContact[];
  customerSinceDp: any;

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
    companyLogo: [],
    companyLogoContentType: [],
    customerSince: [],
    remarks: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected customerService: CustomerService,
    protected contactService: ContactService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.updateForm(customer);
    });
    this.contactService
      .query()
      .subscribe((res: HttpResponse<IContact[]>) => (this.contacts = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(customer: ICustomer) {
    this.editForm.patchValue({
      id: customer.id,
      company: customer.company,
      firstName: customer.firstName,
      lastName: customer.lastName,
      contactDesignation: customer.contactDesignation,
      email: customer.email,
      phoneNumber: customer.phoneNumber,
      address: customer.address,
      streetAddress: customer.streetAddress,
      city: customer.city,
      stateProvince: customer.stateProvince,
      country: customer.country,
      postalCode: customer.postalCode,
      dot: customer.dot,
      mc: customer.mc,
      companyLogo: customer.companyLogo,
      companyLogoContentType: customer.companyLogoContentType,
      customerSince: customer.customerSince,
      remarks: customer.remarks
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

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const customer = this.createFromForm();
    if (customer.id !== undefined) {
      this.subscribeToSaveResponse(this.customerService.update(customer));
    } else {
      this.subscribeToSaveResponse(this.customerService.create(customer));
    }
  }

  private createFromForm(): ICustomer {
    return {
      ...new Customer(),
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
      companyLogoContentType: this.editForm.get(['companyLogoContentType']).value,
      companyLogo: this.editForm.get(['companyLogo']).value,
      customerSince: this.editForm.get(['customerSince']).value,
      remarks: this.editForm.get(['remarks']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>) {
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

  trackContactById(index: number, item: IContact) {
    return item.id;
  }
}

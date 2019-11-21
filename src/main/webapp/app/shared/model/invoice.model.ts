import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { TaxType } from 'app/shared/model/enumerations/tax-type.model';
import { CURRENCY } from 'app/shared/model/enumerations/currency.model';
import { InvoiceStatus } from 'app/shared/model/enumerations/invoice-status.model';

export interface IInvoice {
  id?: number;
  invoiceId?: string;
  orderNo?: string;
  taxRate?: number;
  taxType?: TaxType;
  currency?: CURRENCY;
  invoiceTaxTotal?: number;
  invoiceSubTotal?: number;
  invoiceTotal?: number;
  invoiceDate?: Moment;
  invoicePaidDate?: Moment;
  payRefNo?: string;
  invoiceDueDate?: Moment;
  status?: InvoiceStatus;
  invoicePdfContentType?: string;
  invoicePdf?: any;
  remarks?: string;
  customer?: ICustomer;
}

export class Invoice implements IInvoice {
  constructor(
    public id?: number,
    public invoiceId?: string,
    public orderNo?: string,
    public taxRate?: number,
    public taxType?: TaxType,
    public currency?: CURRENCY,
    public invoiceTaxTotal?: number,
    public invoiceSubTotal?: number,
    public invoiceTotal?: number,
    public invoiceDate?: Moment,
    public invoicePaidDate?: Moment,
    public payRefNo?: string,
    public invoiceDueDate?: Moment,
    public status?: InvoiceStatus,
    public invoicePdfContentType?: string,
    public invoicePdf?: any,
    public remarks?: string,
    public customer?: ICustomer
  ) {}
}

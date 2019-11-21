import { Moment } from 'moment';
import { IInsurance } from 'app/shared/model/insurance.model';
import { ILoadOrder } from 'app/shared/model/load-order.model';
import { IInvoice } from 'app/shared/model/invoice.model';
import { IContact } from 'app/shared/model/contact.model';
import { Designation } from 'app/shared/model/enumerations/designation.model';
import { CountryEnum } from 'app/shared/model/enumerations/country-enum.model';

export interface ICustomer {
  id?: number;
  company?: string;
  firstName?: string;
  lastName?: string;
  contactDesignation?: Designation;
  email?: string;
  phoneNumber?: number;
  address?: string;
  streetAddress?: string;
  city?: string;
  stateProvince?: string;
  country?: CountryEnum;
  postalCode?: string;
  dot?: string;
  mc?: number;
  companyLogoContentType?: string;
  companyLogo?: any;
  customerSince?: Moment;
  remarks?: string;
  custInsurance?: IInsurance;
  loadOrders?: ILoadOrder[];
  invoices?: IInvoice[];
  morecontact?: IContact;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public company?: string,
    public firstName?: string,
    public lastName?: string,
    public contactDesignation?: Designation,
    public email?: string,
    public phoneNumber?: number,
    public address?: string,
    public streetAddress?: string,
    public city?: string,
    public stateProvince?: string,
    public country?: CountryEnum,
    public postalCode?: string,
    public dot?: string,
    public mc?: number,
    public companyLogoContentType?: string,
    public companyLogo?: any,
    public customerSince?: Moment,
    public remarks?: string,
    public custInsurance?: IInsurance,
    public loadOrders?: ILoadOrder[],
    public invoices?: IInvoice[],
    public morecontact?: IContact
  ) {}
}

import { IInsurance } from 'app/shared/model/insurance.model';
import { Designation } from 'app/shared/model/enumerations/designation.model';
import { CountryEnum } from 'app/shared/model/enumerations/country-enum.model';

export interface IOwnerOperator {
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
  remarks?: string;
  insurance?: IInsurance;
}

export class OwnerOperator implements IOwnerOperator {
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
    public remarks?: string,
    public insurance?: IInsurance
  ) {}
}

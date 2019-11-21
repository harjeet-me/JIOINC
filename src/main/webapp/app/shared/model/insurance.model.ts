import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { IOwnerOperator } from 'app/shared/model/owner-operator.model';

export interface IInsurance {
  id?: number;
  providerName?: string;
  issueDate?: Moment;
  expiryDate?: Moment;
  policyDocumentContentType?: string;
  policyDocument?: any;
  coverageStatement?: string;
  customer?: ICustomer;
  ownerOperator?: IOwnerOperator;
}

export class Insurance implements IInsurance {
  constructor(
    public id?: number,
    public providerName?: string,
    public issueDate?: Moment,
    public expiryDate?: Moment,
    public policyDocumentContentType?: string,
    public policyDocument?: any,
    public coverageStatement?: string,
    public customer?: ICustomer,
    public ownerOperator?: IOwnerOperator
  ) {}
}

import { Moment } from 'moment';
import { ILoadOrder } from 'app/shared/model/load-order.model';

export interface IDriver {
  id?: number;
  company?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: number;
  licenceNumber?: number;
  dob?: Moment;
  companyJoinedOn?: Moment;
  companyLeftOn?: Moment;
  imageContentType?: string;
  image?: any;
  licenceImageContentType?: string;
  licenceImage?: any;
  remarks?: string;
  loadOrders?: ILoadOrder[];
}

export class Driver implements IDriver {
  constructor(
    public id?: number,
    public company?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: number,
    public licenceNumber?: number,
    public dob?: Moment,
    public companyJoinedOn?: Moment,
    public companyLeftOn?: Moment,
    public imageContentType?: string,
    public image?: any,
    public licenceImageContentType?: string,
    public licenceImage?: any,
    public remarks?: string,
    public loadOrders?: ILoadOrder[]
  ) {}
}

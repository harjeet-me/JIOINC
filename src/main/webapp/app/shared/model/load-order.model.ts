import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { IDriver } from 'app/shared/model/driver.model';
import { StatusEnum } from 'app/shared/model/enumerations/status-enum.model';
import { COVEREDBY } from 'app/shared/model/enumerations/coveredby.model';
import { LoadType } from 'app/shared/model/enumerations/load-type.model';
import { SizeEnum } from 'app/shared/model/enumerations/size-enum.model';

export interface ILoadOrder {
  id?: number;
  orderNumber?: string;
  description?: string;
  shipmentNumber?: string;
  bol?: string;
  pickup?: Moment;
  drop?: Moment;
  pickupLocation?: string;
  dropLocation?: string;
  currentLocation?: string;
  status?: StatusEnum;
  detention?: number;
  chasisInTime?: Moment;
  podContentType?: string;
  pod?: any;
  hazmat?: boolean;
  recievedBy?: string;
  coveredBy?: COVEREDBY;
  loadType?: LoadType;
  containerSize?: SizeEnum;
  numbersOfContainer?: number;
  comments?: string;
  customer?: ICustomer;
  driver?: IDriver;
}

export class LoadOrder implements ILoadOrder {
  constructor(
    public id?: number,
    public orderNumber?: string,
    public description?: string,
    public shipmentNumber?: string,
    public bol?: string,
    public pickup?: Moment,
    public drop?: Moment,
    public pickupLocation?: string,
    public dropLocation?: string,
    public currentLocation?: string,
    public status?: StatusEnum,
    public detention?: number,
    public chasisInTime?: Moment,
    public podContentType?: string,
    public pod?: any,
    public hazmat?: boolean,
    public recievedBy?: string,
    public coveredBy?: COVEREDBY,
    public loadType?: LoadType,
    public containerSize?: SizeEnum,
    public numbersOfContainer?: number,
    public comments?: string,
    public customer?: ICustomer,
    public driver?: IDriver
  ) {
    this.hazmat = this.hazmat || false;
  }
}

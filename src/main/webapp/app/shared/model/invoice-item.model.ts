export interface IInvoiceItem {
  id?: number;
  description?: string;
  qty?: number;
  price?: number;
  total?: number;
}

export class InvoiceItem implements IInvoiceItem {
  constructor(public id?: number, public description?: string, public qty?: number, public price?: number, public total?: number) {}
}

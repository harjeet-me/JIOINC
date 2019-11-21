import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { OwnerOperatorService } from 'app/entities/owner-operator/owner-operator.service';
import { IOwnerOperator, OwnerOperator } from 'app/shared/model/owner-operator.model';
import { Designation } from 'app/shared/model/enumerations/designation.model';
import { CountryEnum } from 'app/shared/model/enumerations/country-enum.model';

describe('Service Tests', () => {
  describe('OwnerOperator Service', () => {
    let injector: TestBed;
    let service: OwnerOperatorService;
    let httpMock: HttpTestingController;
    let elemDefault: IOwnerOperator;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(OwnerOperatorService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new OwnerOperator(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        Designation.MANAGER,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        CountryEnum.USA,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a OwnerOperator', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new OwnerOperator(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a OwnerOperator', () => {
        const returnedFromService = Object.assign(
          {
            company: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            contactDesignation: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 1,
            address: 'BBBBBB',
            streetAddress: 'BBBBBB',
            city: 'BBBBBB',
            stateProvince: 'BBBBBB',
            country: 'BBBBBB',
            postalCode: 'BBBBBB',
            dot: 'BBBBBB',
            mc: 1,
            remarks: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of OwnerOperator', () => {
        const returnedFromService = Object.assign(
          {
            company: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            contactDesignation: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 1,
            address: 'BBBBBB',
            streetAddress: 'BBBBBB',
            city: 'BBBBBB',
            stateProvince: 'BBBBBB',
            country: 'BBBBBB',
            postalCode: 'BBBBBB',
            dot: 'BBBBBB',
            mc: 1,
            remarks: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a OwnerOperator', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

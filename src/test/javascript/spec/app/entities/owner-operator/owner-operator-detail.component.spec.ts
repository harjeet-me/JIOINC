import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JiotmsTestModule } from '../../../test.module';
import { OwnerOperatorDetailComponent } from 'app/entities/owner-operator/owner-operator-detail.component';
import { OwnerOperator } from 'app/shared/model/owner-operator.model';

describe('Component Tests', () => {
  describe('OwnerOperator Management Detail Component', () => {
    let comp: OwnerOperatorDetailComponent;
    let fixture: ComponentFixture<OwnerOperatorDetailComponent>;
    const route = ({ data: of({ ownerOperator: new OwnerOperator(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JiotmsTestModule],
        declarations: [OwnerOperatorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OwnerOperatorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OwnerOperatorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ownerOperator).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

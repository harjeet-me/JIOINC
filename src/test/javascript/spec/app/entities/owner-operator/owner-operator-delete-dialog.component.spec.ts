import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JiotmsTestModule } from '../../../test.module';
import { OwnerOperatorDeleteDialogComponent } from 'app/entities/owner-operator/owner-operator-delete-dialog.component';
import { OwnerOperatorService } from 'app/entities/owner-operator/owner-operator.service';

describe('Component Tests', () => {
  describe('OwnerOperator Management Delete Component', () => {
    let comp: OwnerOperatorDeleteDialogComponent;
    let fixture: ComponentFixture<OwnerOperatorDeleteDialogComponent>;
    let service: OwnerOperatorService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JiotmsTestModule],
        declarations: [OwnerOperatorDeleteDialogComponent]
      })
        .overrideTemplate(OwnerOperatorDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OwnerOperatorDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OwnerOperatorService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});

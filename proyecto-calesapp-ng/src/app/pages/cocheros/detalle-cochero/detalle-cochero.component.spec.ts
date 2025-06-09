import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleCocheroComponent } from './detalle-cochero.component';

describe('DetalleCocheroComponent', () => {
  let component: DetalleCocheroComponent;
  let fixture: ComponentFixture<DetalleCocheroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleCocheroComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalleCocheroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MisContactosComponent } from './mis-contactos.component';

describe('MisContactosComponent', () => {
  let component: MisContactosComponent;
  let fixture: ComponentFixture<MisContactosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MisContactosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MisContactosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

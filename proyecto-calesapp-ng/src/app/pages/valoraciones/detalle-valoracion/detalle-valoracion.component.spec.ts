import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleValoracionComponent } from './detalle-valoracion.component';

describe('DetalleValoracionComponent', () => {
  let component: DetalleValoracionComponent;
  let fixture: ComponentFixture<DetalleValoracionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleValoracionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalleValoracionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

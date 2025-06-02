import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleParadaComponent } from './detalle-parada.component';

describe('DetalleParadaComponent', () => {
  let component: DetalleParadaComponent;
  let fixture: ComponentFixture<DetalleParadaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleParadaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalleParadaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

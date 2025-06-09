import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaValoracionesComponent } from './lista-valoraciones.component';

describe('ListaValoracionesComponent', () => {
  let component: ListaValoracionesComponent;
  let fixture: ComponentFixture<ListaValoracionesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaValoracionesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaValoracionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

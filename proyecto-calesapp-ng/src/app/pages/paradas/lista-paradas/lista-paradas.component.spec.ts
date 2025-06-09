import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaParadasComponent } from './lista-paradas.component';

describe('ListaParadasComponent', () => {
  let component: ListaParadasComponent;
  let fixture: ComponentFixture<ListaParadasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaParadasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaParadasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

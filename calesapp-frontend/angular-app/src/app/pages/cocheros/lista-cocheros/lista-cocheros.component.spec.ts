import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaCocherosComponent } from './lista-cocheros.component';

describe('ListaCocherosComponent', () => {
  let component: ListaCocherosComponent;
  let fixture: ComponentFixture<ListaCocherosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaCocherosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaCocherosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

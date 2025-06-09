import { TestBed } from '@angular/core/testing';

import { ParadasService } from './paradas.service';

describe('ParadasService', () => {
  let service: ParadasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParadasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

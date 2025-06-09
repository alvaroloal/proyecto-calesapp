import { TestBed } from '@angular/core/testing';

import { CocherosService } from './cocheros.service';

describe('CocherosService', () => {
  let service: CocherosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CocherosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { BodyInfoServiceService } from './body-info-service.service';

describe('BodyInfoServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BodyInfoServiceService = TestBed.get(BodyInfoServiceService);
    expect(service).toBeTruthy();
  });
});

package com.moaguide.service.building;

import com.moaguide.domain.building.lease.Lease;
import com.moaguide.domain.building.lease.LeaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LeaseService {
    private final LeaseRepository leaseRepository;

    public List<Lease> detail(String id) {
        List<Lease> lease = leaseRepository.findByproductId(id);
        return lease;
    }
}

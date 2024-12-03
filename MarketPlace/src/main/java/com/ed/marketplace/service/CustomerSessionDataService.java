package com.ed.marketplace.service;

import com.ed.marketplace.entity.CustomerSessionData;
import com.ed.marketplace.repository.redis.CustomerSessionDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerSessionDataService {

    private final CustomerSessionDataRepository customerSessionDataRepository;


    public void saveSessionData( ){
        CustomerSessionData sessionData = new CustomerSessionData();

        customerSessionDataRepository.save(sessionData);
    }


}

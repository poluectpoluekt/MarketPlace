package com.ed.marketplace.service;

import com.ed.marketplace.app_class.CustomerSessionData;
import com.ed.marketplace.repository.redis.CustomerSessionDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Класс не используется, оставлен, если понадобитсья сохранять сессию пользователя
 */
@RequiredArgsConstructor
@Service
public class CustomerSessionDataService {

    private final CustomerSessionDataRepository customerSessionDataRepository;


    public void saveSessionData() {
        CustomerSessionData sessionData = new CustomerSessionData();

        customerSessionDataRepository.save(sessionData);
    }


}

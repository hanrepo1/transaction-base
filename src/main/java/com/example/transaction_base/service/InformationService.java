package com.example.transaction_base.service;

import com.example.transaction_base.model.Banner;
import com.example.transaction_base.model.Services;
import com.example.transaction_base.repository.BannerRepository;
import com.example.transaction_base.repository.ServiceRepository;
import com.example.transaction_base.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformationService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private BannerRepository bannerRepository;

    public ResponseEntity<Object> banner(HttpServletRequest request) {
        try {
            List<Banner> banners = bannerRepository.findAll();

            return ResponseUtil.dataFound("Data Found", banners , request);
        } catch (Exception e) {
            return ResponseUtil.validationFailed("An error occurred while fetching the banners: " + e.getMessage(), request);
        }
    }

    public ResponseEntity<Object> services(HttpServletRequest request) {
        try {
            List<Services> services = serviceRepository.findAll();

            return ResponseUtil.dataFound("Data Found", services , request);
        } catch (Exception e) {
            return ResponseUtil.validationFailed("An error occurred while fetching the services: " + e.getMessage(), request);
        }
    }

}

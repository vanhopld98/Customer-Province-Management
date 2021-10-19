package com.codegym.service;

import com.codegym.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProvinceService extends IGeneralService<Province> {
    Page<Province> findByNameContaining(String name, Pageable pageable);
}

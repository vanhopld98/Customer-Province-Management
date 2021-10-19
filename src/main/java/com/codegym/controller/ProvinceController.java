package com.codegym.controller;

import com.codegym.model.Province;
import com.codegym.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/province")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @GetMapping
    public ModelAndView index(@RequestParam(name = "q")Optional<String> q,@PageableDefault(size = 2) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/province/index");
        Page<Province> provinces;
        if (q.isPresent()){
            provinces = provinceService.findByNameContaining(q.get(),pageable);
        }else {
            provinces = provinceService.findAll(pageable);
        }
        modelAndView.addObject("provinces", provinces);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("/province/create");
        modelAndView.addObject("province", new Province());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute Province province) {
        ModelAndView modelAndView = new ModelAndView("redirect:/province");
        provinceService.save(province);
        modelAndView.addObject("province", new Province());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        Optional<Province> provinces = provinceService.findById(id);
        ModelAndView modelAndView;
        if (provinces.isPresent()) {
            modelAndView = new ModelAndView("/province/edit");
            modelAndView.addObject("province", provinces.get());

        } else {
            modelAndView = new ModelAndView("/error-404");
        }
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView edit(@ModelAttribute Province province) {
        provinceService.save(province);
        return new ModelAndView("redirect:/province");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        Optional<Province> provinces = provinceService.findById(id);
        ModelAndView modelAndView;
        if (provinces.isPresent()) {
            modelAndView = new ModelAndView("/province/delete");
            modelAndView.addObject("province", provinces.get());
        } else {
            modelAndView = new ModelAndView("/error-404");
        }
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView delete(@ModelAttribute Province province) {
        provinceService.remove(province.getId());
        return new ModelAndView("redirect:/province");
    }
}

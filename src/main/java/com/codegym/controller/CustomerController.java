package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.Province;
import com.codegym.service.ICustomerService;
import com.codegym.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @ModelAttribute(name = "provinces")
    public Iterable<Province> provinces() {
        return provinceService.findAll();
    }

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return new ModelAndView("redirect:/customer");
    }

    @GetMapping
    public ModelAndView index() {
        Iterable<Customer> customerList = customerService.findAll();
        ModelAndView modelAndView = new ModelAndView("/customer/index");
        modelAndView.addObject("customerList", customerList);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        ModelAndView modelAndView;
        if (customer.isPresent()) {
            modelAndView = new ModelAndView("/customer/edit");
            modelAndView.addObject("customer", customer.get());
        } else {
            modelAndView = new ModelAndView("/error-404");
        }
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView edit(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return new ModelAndView("redirect:/customer");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        ModelAndView modelAndView;
        if (customer.isPresent()) {
            modelAndView = new ModelAndView("/customer/delete");
            modelAndView.addObject("customer", customer);
        } else {
            modelAndView = new ModelAndView("/error-404");
        }
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView delete(@ModelAttribute Customer customer) {
        customerService.remove(customer.getId());
        return new ModelAndView("redirect:/customer");
    }
}

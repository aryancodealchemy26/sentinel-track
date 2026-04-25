package com.sentinel.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebCtrl {

    @Autowired
    SiteRepo repo;

    // This shows the home page
    @GetMapping("/")
    public String home(Model m) {
        List<Site> all = repo.findAll();
        m.addAttribute("list", all);
        return "index";
    }

    // This saves a new site
    @PostMapping("/add")
    public String add(@RequestParam String name, @RequestParam String url) {
        Site s = new Site();
        s.name = name;
        s.url = url;
        s.st = "PENDING"; // Initial status
        repo.save(s);
        return "redirect:/";
    }
}
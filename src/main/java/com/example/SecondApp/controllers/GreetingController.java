package com.example.SecondApp.controllers;

import com.example.SecondApp.models.Visitor;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.SecondApp.repo.VisitorsRepository;

@RestController
public class GreetingController {
    @Autowired
    private VisitorsRepository visitorsRepository;

    @GetMapping("/")
    public String greeting(){
        return "Hello, from Spring";
    }

    @PostMapping("/")
    public ResponseEntity greetWithName(@RequestParam(value = "name", required = false) String name,
                                        @RequestBody(required = false) String jsonName,
                                        HttpServletRequest request){
        if (jsonName!=null && !jsonName.equals("")){
            name = (String) new JSONObject(jsonName).get("name");
        }
        if (name==null){
            return ResponseEntity.badRequest().body("No name parameter");
        }
        Visitor visitor = new Visitor();
        visitor.setName(name);
        visitor.setIp(request.getRemoteAddr());
        visitorsRepository.save(visitor);
        return ResponseEntity.ok(String.format("Hello, %s, from Spring",name));
    }



    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name") JSONPObject name) {
        return "greet";
    }
}

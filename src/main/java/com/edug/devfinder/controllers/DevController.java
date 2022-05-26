package com.edug.devfinder.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/dev")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DevController {

}

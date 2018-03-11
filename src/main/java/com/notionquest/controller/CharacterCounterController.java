package com.notionquest.controller;

import com.notionquest.model.MixStrings;
import com.notionquest.model.MixStringsOutcome;
import com.notionquest.service.CharacterCounterService;
import com.notionquest.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class CharacterCounterController {

    private final Logger LOG = Logger.getLogger(getClass().getName());

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }


    @Autowired
    private CharacterCounterService characterCounterService;

    @PostMapping(value = Constants.CHARACTER_COUNT_URI, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public MixStringsOutcome getCharactersMix(@RequestBody MixStrings mixStrings) {
        LOG.info("Input strings :" + mixStrings);
        return new MixStringsOutcome(characterCounterService.getMix(mixStrings.getStrings()));
    }
}

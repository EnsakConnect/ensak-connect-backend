package com.ensak.connect.controller;


import com.ensak.connect.dto.TestRequest;
import com.ensak.connect.dto.TestResponse;
import com.ensak.connect.service.TestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/ensak-connect")
@AllArgsConstructor
public class TestController {

    private final TestService testService;
    @PostMapping
    public TestResponse postTest(@RequestBody TestRequest testRequest){
        log.info("Post test works {}", testRequest);
        testService.addTest(testRequest);
        return new TestResponse("Post request works!");
    }

    @GetMapping
    public TestResponse getTest(){
        log.info("Get test works");
        return new TestResponse("Get request works!");
    }
}

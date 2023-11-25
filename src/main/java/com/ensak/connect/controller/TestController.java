package com.ensak.connect.controller;


import com.ensak.connect.dto.TestRequest;
import com.ensak.connect.dto.TestResponse;
import com.ensak.connect.service.TestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequestMapping("api/v1/ensak-connect")
@AllArgsConstructor
public class TestController {

    private final TestService testService;
    @PostMapping
    public TestResponse postTest(@RequestBody TestRequest testRequest){
        log.info("Post test works {}", testRequest);
        testService.addTest(testRequest);
        TestResponse response = new TestResponse("Post request works!");
        return response;
    }

    @GetMapping
    public TestResponse getTest(){
        log.info("Get test works");
        TestResponse response = new TestResponse("Get request works!");
        return response;
    }
}

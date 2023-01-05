package com.dfoff.demo.Controller;

import com.dfoff.demo.Service.DFCharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DFCharacterController {
    private final DFCharacterService DFCharacterService;



}

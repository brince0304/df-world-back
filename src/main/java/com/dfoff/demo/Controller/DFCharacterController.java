package com.dfoff.demo.Controller;

import com.dfoff.demo.Service.DFCharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DFCharacterController {
    private final DFCharacterService DFCharacterService;

    @GetMapping("/df/update.df")
    public ResponseEntity<?> getServerAndJobListForUpdate(){
        try {
            DFCharacterService.getJobList();
            DFCharacterService.getServerStatus();
            return new ResponseEntity<>(new ModelAndView("/index"), null, 200);
        } catch (Exception e) {
            log.error("error", e);
            return new ResponseEntity<>(new ModelAndView("/index"), null, 500);
        }
    }

}

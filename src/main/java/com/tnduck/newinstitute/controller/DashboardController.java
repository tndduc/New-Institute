package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.service.MessageSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 9:20 PM
 */
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController extends AbstractBaseController {
    private final MessageSourceService messageSourceService;

    @GetMapping
    public ResponseEntity<String> dashboard() {
        return ResponseEntity.ok(messageSourceService.get("hi"));
    }
}

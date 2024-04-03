package com.example.demo.controller;
import com.example.demo.Service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

    @Autowired
    private RewardService rewardService;

    @GetMapping("/{customerId}/{year}/{month}")
    public ResponseEntity<Map<String, Object>> getRewards(@PathVariable Long customerId, @PathVariable int year, @PathVariable int month) {
        Map<String, Object> rewards = rewardService.calculateRewards(customerId, year, month);
        return ResponseEntity.ok(rewards);
    }
}
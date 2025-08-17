package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.requests.AssignJudgeToHackathonRequest;
import com.hackhero.coremodule.dto.requests.CreateJudgeRequest;
import com.hackhero.coremodule.dto.responses.JudgeResponse;
import com.hackhero.coremodule.dto.responses.SubmissionResponse;
import com.hackhero.coremodule.services.JudgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/judges")
@RequiredArgsConstructor
public class JudgeController {

    private final JudgeService judgeService;

    @PostMapping
    public JudgeResponse createJudge(@RequestBody CreateJudgeRequest request) {
        return judgeService.createJudge(request);
    }

    @GetMapping("/{id}")
    public JudgeResponse getJudgeById(@PathVariable("id") Long id) {
        return judgeService.getJudgeById(id);
    }

    @GetMapping
    public List<JudgeResponse> getAllJudges() {
        return judgeService.getAllJudges();
    }

    @PutMapping("/{id}")
    public JudgeResponse updateJudge(@PathVariable("id") Long id,
                                     @RequestBody CreateJudgeRequest request) {
        return judgeService.updateJudge(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteJudge(@PathVariable("id") Long id) {
        judgeService.deleteJudge(id);
    }

    @PostMapping("/assign")
    public ResponseEntity<String> assignJudgeToHackathon(
            @RequestBody AssignJudgeToHackathonRequest request) {
        judgeService.assignJudgeToHackathon(request);
        return ResponseEntity.ok("Judge " + request.judgeId() + " assigned to Hackathon " + request.hackathonId());
    }


    @GetMapping("/submissions")
    public ResponseEntity<List<SubmissionResponse>> getAllSubmissions() {
        return ResponseEntity.ok(judgeService.getAllSubmissions());
    }

    @PostMapping("/submissions/{submissionId}/score")
    public ResponseEntity<String> scoreSubmission(
            @PathVariable("submissionId") Long submissionId,
            @RequestParam int score) {
        judgeService.scoreSubmission(submissionId, score);
        return ResponseEntity.ok("Scored submission " + submissionId + " with " + score);
    }
}

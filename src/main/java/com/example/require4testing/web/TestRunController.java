package com.example.require4testing.web;

import com.example.require4testing.entity.TestCase;
import com.example.require4testing.entity.TestExecution;
import com.example.require4testing.entity.TestRun;
import com.example.require4testing.service.TestCaseService;
import com.example.require4testing.service.TestRunService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/testruns")
public class TestRunController {

    private final TestRunService testRunService;
    private final TestCaseService testCaseService;

    public TestRunController(TestRunService testRunService, TestCaseService testCaseService) {
        this.testRunService = testRunService;
        this.testCaseService = testCaseService;
    }


    @GetMapping
    public String viewAllRuns(Model model) {
        List<TestRun> runs = testRunService.getAllTestRuns();
        model.addAttribute("testRuns", runs);
        return "testruns/list";
    }


    @GetMapping("/new")
    public String showNewRunForm(Model model) {
        model.addAttribute("testRun", new TestRun());
        return "testruns/form";
    }


    @PostMapping("/new")
    public String addTestRun(@Valid @ModelAttribute("testRun") TestRun testRun,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "testruns/form";
        }
        testRunService.addTestRun(testRun);
        return "redirect:/testruns";
    }


    @GetMapping("/{runId}")
    public String viewTestRunDetails(@PathVariable Long runId, Model model) {
        TestRun run = testRunService.getTestRun(runId);
        model.addAttribute("testRun", run);
        model.addAttribute("executions", run.getExecutions());
        return "testruns/detail";
    }


    @GetMapping("/{runId}/assign")
    public String showAssignTestCasesForm(@PathVariable Long runId, Model model) {
        model.addAttribute("allTestCases", testCaseService.getAllTestCases());
        model.addAttribute("runId", runId);
        return "testruns/assign_form";
    }


    @PostMapping("/{runId}/assign")
    public String assignTestCases(@PathVariable Long runId,
                                  @RequestParam(name = "testCaseIds", required = false) List<Long> testCaseIds) {
        if (testCaseIds != null) {
            testRunService.assignTestCases(runId, testCaseIds);
        }
        return "redirect:/testruns/" + runId;
    }


    @PostMapping("/{runId}/testcases/{caseId}/result")
    public String setTestResult(@PathVariable Long runId,
                                @PathVariable Long caseId,
                                @RequestParam("result") String result) {
        TestRun run = testRunService.getTestRun(runId);
        for (TestExecution exec : run.getExecutions()) {
            if (exec.getTestCase().getId().equals(caseId)) {
                testRunService.setTestRunResult(exec.getId(), result);
                break;
            }
        }
        return "redirect:/testruns/" + runId;
    }
}

package com.example.require4testing.web;

import com.example.require4testing.entity.Requirement;
import com.example.require4testing.entity.TestCase;
import com.example.require4testing.service.RequirementService;
import com.example.require4testing.service.TestCaseService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/requirements")
public class RequirementController {

    private final RequirementService requirementService;
    private final TestCaseService testCaseService;

    public RequirementController(RequirementService requirementService, TestCaseService testCaseService) {
        this.requirementService = requirementService;
        this.testCaseService = testCaseService;
    }


    @GetMapping
    public String viewRequirements(Model model) {
        List<Requirement> all = requirementService.getAllRequirements();
        model.addAttribute("requirements", all);
        return "requirements/list";
    }


    @GetMapping("/new")
    public String showNewRequirementForm(Model model) {
        model.addAttribute("requirement", new Requirement());
        return "requirements/form";
    }


    @PostMapping("/new")
    public String addRequirement(@Valid @ModelAttribute("requirement") Requirement requirement,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "requirements/form";
        }
        requirementService.addRequirement(requirement);
        return "redirect:/requirements";
    }


    @GetMapping("/{reqId}")
    public String viewRequirementDetails(@PathVariable Long reqId, Model model) {
        Requirement requirement = requirementService.getRequirement(reqId)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Anforderungs-Id: " + reqId));
        model.addAttribute("requirement", requirement);
        List<TestCase> testCases = testCaseService.getTestCasesbyRequirement(reqId);
        model.addAttribute("testCases", testCases);
        return "requirements/detail";
    }


    @GetMapping("/{reqId}/testcases/new")
    public String showNewTestCaseForm(@PathVariable Long reqId, Model model) {
        Requirement requirement = requirementService.getRequirement(reqId)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Anforderungs-Id: " + reqId));
        TestCase testCase = new TestCase();
        testCase.setRequirement(requirement);
        model.addAttribute("testCase", testCase);
        model.addAttribute("requirementId", reqId);
        model.addAttribute("requirementTitle", requirement.getTitle());
        return "requirements/testcase_form";
    }


    @PostMapping("/{reqId}/testcases/new")
    public String addTestCase(@PathVariable Long reqId,
                              @Valid @ModelAttribute("testCase") TestCase testCase,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            String requirementTitle = "";
            Optional<Requirement> requirement = requirementService.getRequirement(reqId);
            if (requirement.isPresent()) {
                requirementTitle = requirement.get().getTitle();
            }
            model.addAttribute("requirementId", reqId);
            model.addAttribute("requirementTitle", requirementTitle);
            return "requirements/testcase_form";
        }
        testCaseService.addTestCase(reqId, testCase);
        return "redirect:/requirements/" + reqId;
    }
}

package com.example.require4testing.service;

import com.example.require4testing.entity.Requirement;
import com.example.require4testing.repository.RequirementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequirementService {
    private final RequirementRepository requirementRepository;

    public RequirementService(RequirementRepository requirementRepository){
        this.requirementRepository = requirementRepository;
    }

    public List<Requirement> getAllRequirements(){
        return (List<Requirement>) requirementRepository.findAll();
    }

    public Requirement addRequirement(Requirement requirement){

        return requirementRepository.save(requirement);

    }

    public Optional<Requirement> getRequirement(Long Id) {
        return requirementRepository.findById(Id);
    }

}

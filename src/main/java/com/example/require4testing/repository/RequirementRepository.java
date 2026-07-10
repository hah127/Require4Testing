package com.example.require4testing.repository;

import com.example.require4testing.entity.Requirement;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface RequirementRepository extends CrudRepository<Requirement, Long> {

    List<Requirement> findByTitleContaining(String keyword);

}

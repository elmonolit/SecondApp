package com.example.SecondApp.repo;

import com.example.SecondApp.models.Visitor;
import org.springframework.data.repository.CrudRepository;

public interface VisitorsRepository extends CrudRepository<Visitor,Integer> {
}

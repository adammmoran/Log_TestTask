package yrk.com.ua.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yrk.com.ua.models.Case;

@Repository
public interface CaseDAO extends CrudRepository<Case,Integer> {
}

package com.softuni.volunteerplatform.causes.repository;
;
import com.softuni.volunteerplatform.causes.model.entity.Cause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CauseRepository extends JpaRepository<Cause,Long> {
}

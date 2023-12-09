package com.app.lms.features.patrons;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Integer> {

    //Page<Patron> findByOrderByIdDesc(Pageable pageable);

}

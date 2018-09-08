package com.embl.technicaltest.repository;

import org.springframework.data.repository.CrudRepository;

import com.embl.technicaltest.domainobject.PersonDO;

/**
 * Person Repository
 * 
 * @author Ashutosh Shimpi
 *
 */
public interface PersonRepository extends CrudRepository<PersonDO, Long> {

}

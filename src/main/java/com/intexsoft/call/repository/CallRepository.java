package com.intexsoft.call.repository;

import com.intexsoft.call.model.Call;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Repository for {@code Call} entity</p>
 * Implements {@code CrudRepository<T, ID>} for {@code Call} entity with ID of type {@code Long}
 */
@Repository
public interface CallRepository extends CrudRepository<Call, Long> {
}

package com.intexsoft.call.repository;

import com.intexsoft.call.model.Call;
import org.springframework.data.repository.CrudRepository;

public interface ICallRepository extends CrudRepository<Call, Long> {
}

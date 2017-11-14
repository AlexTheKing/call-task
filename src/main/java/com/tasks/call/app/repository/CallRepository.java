package com.tasks.call.app.repository;

import com.tasks.call.app.model.Call;
import org.springframework.data.repository.CrudRepository;

public interface CallRepository extends CrudRepository<Call, Long> {
}

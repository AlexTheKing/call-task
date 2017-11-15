package com.tasks.call.app.repository;

import com.tasks.call.app.model.Call;
import org.springframework.data.repository.CrudRepository;

public interface ICallRepository extends CrudRepository<Call, Long> {
}

package com.api.viasoft.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.viasoft.constant.States;
import com.api.viasoft.model.NfeServicesStatus;

@Repository
public interface INfeServicesStatusRepository extends CrudRepository<NfeServicesStatus, Long> {

	@Query(
			" SELECT nss FROM NfeServicesStatus nss " +
					" WHERE creationDate BETWEEN :min and :max " +
					" ORDER BY creationDate DESC "
	)
	List<NfeServicesStatus> findByDate(LocalDateTime min, LocalDateTime max);

	List<NfeServicesStatus> findByState(States state);

}

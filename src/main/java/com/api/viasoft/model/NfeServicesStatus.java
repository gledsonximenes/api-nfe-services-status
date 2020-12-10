package com.api.viasoft.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.api.viasoft.constant.States;
import com.api.viasoft.constant.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
		name = "tb_nfe_services_status"
)
public class NfeServicesStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "col_id")
	private Long id;

	@Enumerated(value = EnumType.ORDINAL)
	@Column(name= "col_state")
	private States state;

	@Enumerated(value = EnumType.ORDINAL)
	@Column(name = "col_authorization")
	private Status authorization;

	@Enumerated(value = EnumType.ORDINAL)
	@Column(name = "col_return_authorization")
	private Status returnAuthorization;

	@Enumerated(value = EnumType.ORDINAL)
	@Column(name = "col_disable")
	private Status disable;

	@Enumerated(value = EnumType.ORDINAL)
	@Column(name = "col_protocol_consultation")
	private Status protocolConsultation;

	@Enumerated(value = EnumType.ORDINAL)
	@Column(name = "col_status_service")
	private Status statusService;

	@Column(name = "col_average_time")
	private String averageTime;

	@Enumerated(value = EnumType.ORDINAL)
	@Column(name = "col_query_register")
	private Status queryRegister;

	@Enumerated(value = EnumType.ORDINAL)
	@Column(name = "col_reception_event")
	private Status receptionEvent;

	@Column(name = "col_creation_date", nullable = false)
	private LocalDateTime creationDate;

	@PrePersist
	public void prePersist() {
		if (creationDate == null) {
			creationDate = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
		}
	}

}

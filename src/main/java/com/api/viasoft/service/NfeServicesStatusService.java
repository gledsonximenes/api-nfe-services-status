package com.api.viasoft.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.viasoft.constant.States;
import com.api.viasoft.constant.Status;
import com.api.viasoft.model.NfeServicesStatus;
import com.api.viasoft.repository.INfeServicesStatusRepository;

@Service
public class NfeServicesStatusService {

	@Autowired
	private INfeServicesStatusRepository repository;

	private final String urlNfeServices = "http://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx";

	private Document document;

	public List<NfeServicesStatus> searchCurrentStatusServicesNfe() {
		try {
			document = Jsoup.connect(urlNfeServices).get();

			List<NfeServicesStatus> nfeServicesStatusList = new ArrayList<>();

			Elements elementsByClass = document.getElementsByClass("tabelaListagemDados");

			for (Element elementByClass : elementsByClass) {

				Elements elementsByTr = elementByClass.getElementsByTag("tr");

				NfeServicesStatus nfeServicesStatus;

				for (Element elementByTr : elementsByTr) {
					Elements elementsByTd = elementByTr.getElementsByTag("td");
					if (elementsByTd.size() > 0) {
						nfeServicesStatus = NfeServicesStatus
								.builder()
								.state(getStatesEnum(elementsByTd.get(0).text()))
								.authorization(getStatusEnum(elementsByTd.get(1)))
								.returnAuthorization(getStatusEnum(elementsByTd.get(2)))
								.disable(getStatusEnum(elementsByTd.get(3)))
								.protocolConsultation(getStatusEnum(elementsByTd.get(4)))
								.statusService(getStatusEnum(elementsByTd.get(5)))
								.averageTime(elementsByTd.get(6).text())
								.queryRegister(getStatusEnum(elementsByTd.get(7)))
								.receptionEvent(getStatusEnum(elementsByTd.get(8)))
								.build();
						nfeServicesStatusList.add(nfeServicesStatus);
					}
				}
			}

			return nfeServicesStatusList;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public NfeServicesStatus searchCurrentStatusServicesNfeByState(String state) {
		List<NfeServicesStatus> nfeServicesStatusList = searchCurrentStatusServicesNfe();
		States stateEnum = getStatesEnum(state.toUpperCase());
		if (!nfeServicesStatusList.isEmpty()) {
			for (NfeServicesStatus nfeServicesStatus : nfeServicesStatusList) {
				if (nfeServicesStatus.getState().equals(stateEnum)) {
					return nfeServicesStatus;
				}
			}
		}
		return null;
	}

	public List<NfeServicesStatus> searchCurrentStatusServicesNfeByDate(LocalDate date) {
		return repository.findByDate(date.atTime(0, 0, 0), date.atTime(23, 59, 59));
	}

	public States getServiceUnavailabilityByState() {
		States stateUnavailability = null;
		int lastCount = 0;
		for (States state : States.values()) {
			int countUnavailability = countServiceUnavailabilityByState(state);
			if (lastCount < countUnavailability) {
				stateUnavailability = state;
				lastCount = countUnavailability;
			}
		}
		return stateUnavailability;
	}

	private int countServiceUnavailabilityByState(States state) {

		List<NfeServicesStatus> nfeServicesStatusList = repository.findByState(state);
		int countUnavailability = 0;

		if (nfeServicesStatusList.isEmpty()) {
			return countUnavailability;
		}

		for (NfeServicesStatus nfeServicesStatus : nfeServicesStatusList) {
			if (nfeServicesStatus.getAuthorization() != null
					&& !nfeServicesStatus.getAuthorization().equals(Status.GREEN)) {
				countUnavailability++;
			} else if (nfeServicesStatus.getDisable() != null
					&& !nfeServicesStatus.getDisable().equals(Status.GREEN)) {
				countUnavailability++;
			} else if (nfeServicesStatus.getProtocolConsultation() != null
					&& !nfeServicesStatus.getProtocolConsultation().equals(Status.GREEN)) {
				countUnavailability++;
			} else if (nfeServicesStatus.getQueryRegister() != null
					&& !nfeServicesStatus.getQueryRegister().equals(Status.GREEN)) {
				countUnavailability++;
			} else if (nfeServicesStatus.getReceptionEvent() != null &&
					!nfeServicesStatus.getReceptionEvent().equals(Status.GREEN)) {
				countUnavailability++;
			} else if (nfeServicesStatus.getReturnAuthorization() != null
					&& !nfeServicesStatus.getReturnAuthorization().equals(Status.GREEN)) {
				countUnavailability++;
			} else if (nfeServicesStatus.getStatusService() != null
					&& !nfeServicesStatus.getStatusService().equals(Status.GREEN)) {
				countUnavailability++;
			}
		}

		return countUnavailability;
	}

	@Transactional(rollbackOn = Exception.class)
	public void saveStatusServicesNfe(NfeServicesStatus nfeServicesStatus) {
		repository.save(nfeServicesStatus);
	}

	private Status getStatusEnum(Element element) {
		Status status = null;
		String valueElement = element.getElementsByTag("img").attr("src");
		if (valueElement.contains("verde")) {
			status = Status.GREEN;
		} else if (valueElement.contains("amarela")) {
			status = Status.YELLOW;
		} else if (valueElement.contains("vermelho")) {
			status = Status.RED;
		}
		return status;
	}

	private States getStatesEnum(String value) {
		try {
			return States.valueOf(value.replace("-", "_"));
		} catch (java.lang.IllegalArgumentException ex) {
			System.out.println("Error: " + ex);
			return null;
		}
	}

}

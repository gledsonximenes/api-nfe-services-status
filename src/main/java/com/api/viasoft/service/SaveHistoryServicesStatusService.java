package com.api.viasoft.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.viasoft.model.NfeServicesStatus;

@Service
public class SaveHistoryServicesStatusService {

	@Autowired
	private NfeServicesStatusService service;

	public final long TIME = (5000 * 60);

	public void jobSaveStatusServicesNfe() {
		try {
			saveHistoryServicesStatus();
			Timer timer;
			timer = new Timer();
			TimerTask task = new TimerTask() {
				public void run() {

					saveHistoryServicesStatus();
				}
			};
			timer.scheduleAtFixedRate(task, TIME, TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveHistoryServicesStatus() {
		System.out.println("Iniciando o armazenamento dos status dos serviços de nota fiscal eletrônica!");
		List<NfeServicesStatus> nfeServicesStatusList = service.searchCurrentStatusServicesNfe();
		for (NfeServicesStatus nfeServicesStatus : nfeServicesStatusList) {
			service.saveStatusServicesNfe(nfeServicesStatus);
		}
		System.out.println("Status dos serviços de nota fiscal eletrônica armazenados com sucesso!");
	}

}

package com.rsvier.workshop.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rsvier.workshop.domein.Persoon;
import com.rsvier.workshop.domein.Persoon.PersoonStatus;

@Controller
@RequestMapping("/klantenzaken")
@SessionAttributes("statusName")
public class KlantenZakenController {
	
	@GetMapping
	public String getKlantenZaken(Model model) {
		PersoonStatus[] persoonStatusList = PersoonStatus.values();
		List<String> persoonStatusNames = getNamesOfPersoonStatus(persoonStatusList);
		model.addAttribute("statusName",new String());
		model.addAttribute("persoonStatusNames", persoonStatusNames);
		return "klantenzaken";
	}
	
	private List<String> getNamesOfPersoonStatus(PersoonStatus[] persoonStatusList) {
		List<String> persoonStatusNames = new ArrayList<>();
		for(PersoonStatus status: persoonStatusList) {
			persoonStatusNames.add(status.name());
		}
		return persoonStatusNames;
	}

}

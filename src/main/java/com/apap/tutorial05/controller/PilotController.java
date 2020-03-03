package com.apap.tutorial05.controller;

import com.apap.tutorial05.model.FlightModel;
import com.apap.tutorial05.model.PilotModel;
import com.apap.tutorial05.service.FlightService;
import com.apap.tutorial05.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	private FlightService flightService;

	@Autowired
	public PilotController(PilotService pilotService, FlightService flightService) {
		this.pilotService = pilotService;
		this.flightService = flightService;
	}

	@RequestMapping("/")
	private String home(){
		return "home";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("pilot", new PilotModel());
		return "addPilot";
	}
	
	@GetMapping(value = "/pilot/view")
	private String view(@RequestParam("licenseNumber") String license, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(license);
		model.addAttribute("pilot", pilot);
		model.addAttribute("flights", pilot.getPilotFlight());
		return "view-pilot";
	}

	@GetMapping(value = "/pilot/view/{licenseNumber}")
	private String viewPilot(@PathVariable(value = "licenseNumber") String license, Model model){
		PilotModel pilotModel = pilotService.getPilotDetailByLicenseNumber(license);
		model.addAttribute("pilot", pilotModel);
		model.addAttribute("flights", pilotModel.getPilotFlight());
		model.addAttribute("title", "Pilot Detail");

		return "view-pilot";
	}

	@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot) {
		pilotService.addPilot(pilot);
		return "add";
	}
	
	 @GetMapping(value = "/pilot/edit/{id}")
	    private String edit(@PathVariable(value = "id") Long id, ModelMap model){

	        PilotModel pilotModel = pilotService.getPilotDetailByID(id);

	        model.addAttribute("pilot", pilotModel);

	        return "editPilot";

	    }
	
	@PostMapping(value = "/pilot/edit")
    private String submitedit(@ModelAttribute PilotModel pilotModel){

        pilotService.addPilot(pilotModel);
        return "redirect:/pilot/view/"+pilotModel.getLicenseNumber();

    }

    @GetMapping(value = "/pilot/delete/{id}")
    private String delete(@PathVariable(value = "id") Long id, ModelMap model){

        PilotModel pilotModel = pilotService.getPilotDetailByID(id);

        model.addAttribute("pilot", pilotModel);

        return "deletePilot";

    }

    @PostMapping(value = "/pilot/delete")
    private String submitdelete(@ModelAttribute PilotModel pilotModel){

        pilotModel = pilotService.getPilotDetailByID(pilotModel.getId());

        for(FlightModel flight : pilotModel.getPilotFlight()){
            flightService.deleteFlight(flight);
        }

        pilotService.delete(pilotModel);

        return "home";
    }
}

package com.apap.tutorial05.controller;

import com.apap.tutorial05.model.FlightModel;
import com.apap.tutorial05.model.PilotModel;
import com.apap.tutorial05.service.FlightService;
import com.apap.tutorial05.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;

	@Autowired
	public FlightController(FlightService flightService, PilotService pilotService) {
		this.flightService = flightService;
		this.pilotService = pilotService;
	}

	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		model.addAttribute("flight", flight);
		return "addFlight";
	}
	
	@PostMapping(value = "/flight/add")
	private String addFlightSubmit(@ModelAttribute FlightModel flight) {
		flightService.addFlight(flight);
		return "redirect:/pilot/view/"+flight.getPilot().getLicenseNumber();
	}


	
	@GetMapping(value = "/flight/edit/{id}")
    private String edit(@PathVariable(value = "id") Long id, ModelMap model){

        FlightModel flightModel = flightService.getById(id);

        model.addAttribute("flight", flightModel);

        return "editFlight";

    }

    @PostMapping(value = "/flight/edit")
    private String submitedit(@ModelAttribute FlightModel flightModel){

        flightService.addFlight(flightModel);
        return "redirect:/pilot/view/"+flightModel.getPilot().getLicenseNumber();

    }
	
	 @GetMapping(value = "/flight/delete/{id}")
	    private String delete(@PathVariable(value = "id") Long id, ModelMap model){

	        FlightModel flightModel = flightService.getById(id);

	        model.addAttribute("flight", flightModel);

	        return "deleteFlight";

	    }

	    @PostMapping(value = "/flight/delete")
	    private String deleteFlight(@ModelAttribute PilotModel pilot, Model model){

			for(FlightModel flightModel : pilot.getPilotFlight()){
				flightService.deleteFlightbyId(flightModel.getId());
			}

	        return "delete";
	    }

	@GetMapping({"/flights"})
	public String showFlights(PilotModel pilot, @RequestParam(value = "licenseNumber") String lNumber, Model model) {

		pilot = pilotService.getPilotDetailByLicenseNumber(lNumber);
		model.addAttribute("pilot", pilot);

		return "flights";
	}



	@RequestMapping(value="/flights", params={"save"})
	public String saveFlights(PilotModel pilot, final BindingResult bindingResult, final ModelMap model) {
		if (bindingResult.hasErrors()) {
			return "flights";
		}

		if (pilot.getPilotFlight() != null && !pilot.getPilotFlight().isEmpty()){
			pilot.getPilotFlight().stream().forEach(f-> f.setPilot(pilot));
		}

		pilotService.addPilot(pilot);
		model.clear();
		return "redirect:/pilot/view/"+pilot.getLicenseNumber();
	}



	@RequestMapping(value="/flights", params={"addRow"})
	public String addRow(PilotModel pilot, final BindingResult bindingResult, final ModelMap model) {
		pilot.getPilotFlight().add(new FlightModel());
		model.addAttribute("pilot", pilot);
		return "flights";
	}


	@RequestMapping(value="/flights", params={"removeRow"})
	public String removeRow(PilotModel pilot, final BindingResult bindingResult, Model model, final HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
		pilot.getPilotFlight().remove(rowId.intValue());

		model.addAttribute("pilot", pilot);
		return "flights";
	}
}

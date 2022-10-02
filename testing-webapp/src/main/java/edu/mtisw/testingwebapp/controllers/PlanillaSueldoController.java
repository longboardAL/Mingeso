package edu.mtisw.testingwebapp.controllers;

import edu.mtisw.testingwebapp.entities.*;
import edu.mtisw.testingwebapp.services.PlanillaSueldosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.util.ArrayList;

@Controller
@RequestMapping
public class PlanillaSueldoController {

    @Autowired
    PlanillaSueldosServices planillaSueldosServices;

    @GetMapping("/PlanillaSueldos")
    public String PlanillSueldo(Model model) throws ParseException {
        planillaSueldosServices.generarPlanilla();
        ArrayList<PlanillaSueldoEntity>planilla=planillaSueldosServices.obtenerPlanilla();
        model.addAttribute("planilla",planilla);
        return "PlanillSueldo";
    }


}

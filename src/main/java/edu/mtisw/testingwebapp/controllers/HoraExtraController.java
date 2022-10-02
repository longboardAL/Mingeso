package edu.mtisw.testingwebapp.controllers;

import edu.mtisw.testingwebapp.services.HoraExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class HoraExtraController {

    @Autowired
    HoraExtraService horaExtraService;

    @GetMapping("/HorasExtras")
    public String ingresoHoraExtra(){
        return "ingresoHoraExtra";
    }
    @PostMapping("/cargaHoraExtra")
    public String cargaHoraExtra(@RequestParam("archivos") MultipartFile file, RedirectAttributes ms) throws Exception {
        horaExtraService.saveFileHoraExtra(file);
        System.out.println(file);
        String s = horaExtraService.saveTableHoraExtra();
        System.out.println(s);
        ms.addFlashAttribute("mensaje", "Archivo guardado correctamente!!");
        return "redirect:/HorasExtras";
    }

}

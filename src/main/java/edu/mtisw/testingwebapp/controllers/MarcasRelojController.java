package edu.mtisw.testingwebapp.controllers;

import edu.mtisw.testingwebapp.services.MarcasRelojService;
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
public class MarcasRelojController {

    @Autowired
    MarcasRelojService marcasRelojService;

    @GetMapping("/MarcasIngreso")
    public String MarcasReloj(){
        return "MarcasReloj";
    }

    @PostMapping("/cargaIngreso")
    public String cargaIngreso(@RequestParam("archivos") MultipartFile file, RedirectAttributes ms) throws Exception {
        marcasRelojService.saveFileIngreso(file);
        System.out.println(file);
        String s = marcasRelojService.saveTable();
        System.out.println(s);
        ms.addFlashAttribute("mensaje", "Archivo guardado correctamente!!");
        return "redirect:/MarcasIngreso";
    }



}

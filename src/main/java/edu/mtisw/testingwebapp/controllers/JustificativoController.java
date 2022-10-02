package edu.mtisw.testingwebapp.controllers;

import edu.mtisw.testingwebapp.services.JustificativoService;
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
public class JustificativoController {

    @Autowired
    JustificativoService justificativoService;

    @GetMapping("/MarcasJustificadas")
    public String ingresoJustificativos(){
        return "ingresoJustificativos";
    }

    @PostMapping("/cargaIngresoJustificativo")
    public String cargaIngresoJustificativo(@RequestParam("archivos") MultipartFile file, RedirectAttributes ms) throws Exception {
        justificativoService.saveFileJustificativo(file);
        System.out.println(file);
        String s = justificativoService.saveTableJustificativo();
        System.out.println(s);
        ms.addFlashAttribute("mensaje", "Archivo guardado correctamente!!");
        return "redirect:/MarcasJustificadas";
    }




}

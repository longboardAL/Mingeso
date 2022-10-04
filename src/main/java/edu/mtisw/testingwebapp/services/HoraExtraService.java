package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.EmpleadoEntity;
import edu.mtisw.testingwebapp.entities.HoraExtraEntity;
import edu.mtisw.testingwebapp.entities.JustificativoEntity;
import edu.mtisw.testingwebapp.repositories.HoraExtraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class HoraExtraService {

    @Autowired
    HoraExtraRepository horaExtraRepository;

    @Autowired
    OficinaRRHH oficinaRRHH;

    private String folder="cargas/";
    private String archivo = "/HorasExtras.txt";
    private final Logger logg = LoggerFactory.getLogger(HoraExtraService.class);
    public String saveFileHoraExtra(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte [] bytes= file.getBytes();
                Path path = Paths.get(folder, file.getOriginalFilename());
                try {
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");

                } catch (IOException e) {
                    System.err.println(e);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            return "No existe!";
        }
        return "Archivo guardado correctamente";
    }

    public String saveTableHoraExtra() throws Exception {

        File doc = new File(folder + archivo);
        System.out.println(doc);
        BufferedReader bufferLectura = new BufferedReader(new FileReader(doc));
        String SEPARADOR = ";";

        // Leer una linea del archivo
        String linea = bufferLectura.readLine();
        DateFormat formato =  new SimpleDateFormat("yyyy/MM/dd HH:mm");

        while (linea != null) {
            HoraExtraEntity horaExtra = new HoraExtraEntity();
            // Sepapar la linea leída con el separador definido previamente
            String[] campos = linea.split(SEPARADOR);
            Date fecha = formato.parse(campos[0]+" "+ campos[1]);
            horaExtra.setFechaAutorizada(fecha);
            horaExtra.setAutorizacion(campos[3]);
            horaExtra.setRut(campos[2]);
            horaExtraRepository.save(horaExtra);
            // Volver a leer otra línea del fichero
            linea = bufferLectura.readLine();

        }
        return "Archivo guardado";
    }


    public long calculoHorasExtras(String rut, String categoria, HoraExtraRepository horaExtraRepository, OficinaRRHH oficinaRRHH) throws ParseException {

        DateFormat formato =  new SimpleDateFormat("yyyy/MM/dd HH:mm");
        java.util.Date salida = formato.parse("2022/03/26 18:00");
        long hour = 0;
        long totalHoras = 0;
        long horaActual = 0;

        for(int j = 0; j < horaExtraRepository.count(); j++){
            if((horaExtraRepository.findAll().get(j).getAutorizacion().equals("Autorizado"))&&(horaExtraRepository.findAll().get(j).getRut().equals(rut))){

                long l = Math.abs((salida.getTime()-horaExtraRepository.findAll().get(j).getFechaAutorizada().getTime()));
                hour = ((l/(60*60*1000))%24) - 1;
                if(hour < 0){
                    hour = 0;
                }
                horaActual = oficinaRRHH.calcularBonificacionPorHorasExtras(hour, categoria);
                //una vez obtenida la hora extra, calculo el monto de esa hora extra
                totalHoras = totalHoras + horaActual;
                System.out.println("Usuario hora extra: "+ horaExtraRepository.findAll().get(j) +" hrs : "+hour+" Total: "+totalHoras);
            }
        }

        return totalHoras;
    }

    public long calculoHorasExtras2(String rut, String categoria, ArrayList<HoraExtraEntity> horaExtraEntities, OficinaRRHH oficinaRRHH) throws ParseException {

        DateFormat formato =  new SimpleDateFormat("yyyy/MM/dd HH:mm");
        java.util.Date salida = formato.parse("2022/03/26 18:00");
        long hour = 0;
        long totalHoras = 0;
        long horaActual = 0;

        for(int j = 0; j < horaExtraEntities.size(); j++){
            if((horaExtraEntities.get(j).getAutorizacion().equals("Autorizado"))&&(horaExtraEntities.get(j).getRut().equals(rut))){

                long l = Math.abs((salida.getTime()-horaExtraEntities.get(j).getFechaAutorizada().getTime()));
                hour = ((l/(60*60*1000))%24) - 1;
                if(hour < 0){
                    hour = 0;
                }
                horaActual = oficinaRRHH.calcularBonificacionPorHorasExtras(hour, categoria);
                //una vez obtenida la hora extra, calculo el monto de esa hora extra
                totalHoras = totalHoras + horaActual;
                //System.out.println("Usuario hora extra: "+ horaExtraRepository.findAll().get(j) +" hrs : "+hour+" Total: "+totalHoras);
            }
        }

        return totalHoras;
    }
}

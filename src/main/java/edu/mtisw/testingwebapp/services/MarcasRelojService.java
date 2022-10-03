package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.MarcasRelojEntity;
import edu.mtisw.testingwebapp.repositories.MarcasRelojRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.*;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class MarcasRelojService {

    @Autowired
    MarcasRelojRepository marcasRelojRepository;

    private String folder="cargas/";
    private String archivo = "/DATA.txt";
    private final Logger logg = LoggerFactory.getLogger(MarcasRelojService.class);

    public String saveFileIngreso(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte [] bytes= file.getBytes();
                Path path = Paths.get(folder, file.getOriginalFilename());
                System.out.println(path);
                System.out.println(bytes);
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

    public String obtenerInstancia(Date fecha) throws ParseException {
        String instancia = "Salida";
        DateFormat formato =  new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date entrada = formato.parse("2022/08/17 09:10");

        long l = entrada.getTime()-fecha.getTime();
        long day = l/(24*60*60*1000);
        long hour = (l/(60*60*1000)-day*24);
        //System.out.println(hour);
        if(hour >= 0){
            instancia = "Entrada";
        }
        return instancia;
    }

    public String saveTable() throws Exception {

        File doc = new File(folder + archivo);
        System.out.println(doc);
        BufferedReader bufferLectura = new BufferedReader(new FileReader(doc));
        String SEPARADOR = ";";

        // Leer una linea del archivo
        String linea = bufferLectura.readLine();
        DateFormat formato =  new SimpleDateFormat("yyyy/MM/dd HH:mm");

        while (linea != null) {
            MarcasRelojEntity marcasReloj = new MarcasRelojEntity();
            // Sepapar la linea leída con el separador definido previamente
            String[] campos = linea.split(SEPARADOR);
            Date fecha = formato.parse(campos[0]+" "+ campos[1]);
            String instancia = obtenerInstancia(fecha);
            marcasReloj.setFechaIngreso(fecha);
            marcasReloj.setRut(campos[2]);
            marcasReloj.setInstancia(instancia);
            marcasRelojRepository.save(marcasReloj);
            // Volver a leer otra línea del fichero
            linea = bufferLectura.readLine();

        }
        return "Archivo guardado";
    }
}

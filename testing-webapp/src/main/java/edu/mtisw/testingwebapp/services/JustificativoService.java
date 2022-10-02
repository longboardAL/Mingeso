package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.JustificativoEntity;
import edu.mtisw.testingwebapp.repositories.JustificativoRepository;
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
import java.util.Date;

@Service
public class JustificativoService {
    @Autowired
    JustificativoRepository justificativoRepository;

    @Autowired
    OficinaRRHH oficinaRRHH;

    private String folder="C:\\Users\\angel\\Desktop\\Mingeso\\testing-webapp\\testing-webapp\\cargas\\";
    private String archivo = "\\Justificativos.txt";
    private final Logger logg = LoggerFactory.getLogger(JustificativoService.class);

    public String saveFileJustificativo(MultipartFile file) {
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

    public String saveTableJustificativo() throws Exception {
        File doc = new File(folder + archivo);
        System.out.println(doc);
        BufferedReader bufferLectura = new BufferedReader(new FileReader(doc));
        String SEPARADOR = ";";

        // Leer una linea del archivo
        String linea = bufferLectura.readLine();
        DateFormat formato =  new SimpleDateFormat("yyyy/MM/dd HH:mm");

        while (linea != null) {
            JustificativoEntity justificativo = new JustificativoEntity();
            // Sepapar la linea leída con el separador definido previamente
            String[] campos = linea.split(SEPARADOR);
            Date fecha = formato.parse(campos[0]+" "+ campos[1]);
            justificativo.setFechaJustificada(fecha);
            justificativo.setJustificativo(campos[3]);
            justificativo.setRut(campos[2]);
            justificativo.setDiasInasistencia(campos[4]);
            justificativoRepository.save(justificativo);
            // Volver a leer otra línea del fichero
            linea = bufferLectura.readLine();

        }
        return "Archivo guardado";
    }


    public double calculoHoraAtrasos(String rut, double sueldoFijo, JustificativoRepository justificativoRepository, OficinaRRHH oficinaRRHH) throws ParseException {

        DateFormat formato =  new SimpleDateFormat("yyyy/MM/dd HH:mm");
        java.util.Date salida = formato.parse("2022/03/26 08:00");
        double totalDescuento = 0;
        double atrasoUnit = 0;

        // Para cada inasistencia no justificada
        for(int j = 0; j < justificativoRepository.count(); j++){
            if((justificativoRepository.findAll().get(j).getJustificativo().equals("0"))
                    && (justificativoRepository.findAll().get(j).getRut().equals(rut))){

                // si faltó ese día al trabajo
                if(justificativoRepository.findAll().get(j).getDiasInasistencia().equals("1")){
                    atrasoUnit = oficinaRRHH.calcularMontoDescuentoPorAtrasoDias(sueldoFijo);
                    totalDescuento = totalDescuento + atrasoUnit;
                }else{
                    // Si la falta no corresponde a un día, sino que a minutos/hora
                    long l = Math.abs(salida.getTime()-justificativoRepository.findAll().get(j).getFechaJustificada().getTime());
                    long min = (l/(60*1000))%60;
                    atrasoUnit = oficinaRRHH.calcularMontoDescuentoPorAtraso(min, sueldoFijo);
                    totalDescuento = totalDescuento + atrasoUnit;
                }
                System.out.println("No está validado:"+ justificativoRepository.findAll().get(j)+" con total desc: "+totalDescuento);
            }
        }
        return totalDescuento;
    }
}

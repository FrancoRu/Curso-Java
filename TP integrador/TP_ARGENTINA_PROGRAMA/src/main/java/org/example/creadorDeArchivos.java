package org.example;

import java.util.*;

import java.io.*;
//CLASE CREADA EXCLUSIVAMENTE PARA CREAR ARCHIVOS .TXT Y REALIZAR PRUEBAS, ESA ES LA RAZON QUE TIENE SU PROPIO MAIN Y ESTA SEPARADO DEL PROYECTO FINAL
public class creadorDeArchivos {
    public static void main(String[] args){
        String [] Selecciones = {"Argentina", "Brasil","Chile", "Colombia","España", "Francia","Inglaterra", "Italia","Mexico", "Portugal","Uruguay", "Alemania"};
        //String [] grupos = {"GRUPO A", "GRUPO B", "GRUPO C", "GRUPO D"};
        //String [] nombre = {"Pepe","Gaston","Camila","Carla","Micaela","Fernando","Juan","Clarisa"};
        //String path = "Rounds.txt";
        //generaGrupos(Selecciones, grupos, path);
        //generarDNIAleatorios(nombre, "Personas.txt");
        generarPartidos(Selecciones, "Games.txt");
    }
    public static void mostrar(String path){
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Process the values array here

                System.out.println(line);

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void generarDNIAleatorios(String[] personas, String path){
        try{
            File file = new File(path);
            FileWriter writer = new FileWriter(file);
            Random rand = new Random();
            for(String value : personas){
                writer.write(value + ";" + rand.nextInt(40000000)+'\n');
            }
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void generarPartidos(String[] selecciones, String path){
        try {
            Random rand = new Random();
            // Crear archivo "partidos.txt" y escribir información
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < selecciones.length; i += 2) {
                bufferedWriter.write(selecciones[i] + ";" + selecciones[i+1] + ";" + rand.nextInt(7) + ";" + rand.nextInt(7) + ";" + (i/2)+'\n');
                //bufferedWriter.newLine();
            }
            bufferedWriter.close();
            System.out.println("File written successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void generaGrupos(String[] selecciones, String[] grupos, String path){
        try {
            // Crear archivo "teams.txt" y escribir información
            File file = new File(path);
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < selecciones.length; i++) {
                writer.write(selecciones[i] + ": " + grupos[i%(selecciones.length/4)] + "\n");
            }
            writer.close();
            System.out.println("File written successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void generaPronostico(String path, ArrayList<game> partidos, ArrayList<people> personas){
        try{
            File file = new File(path);
            FileWriter writer = new FileWriter(file);
            Random rand = new Random();
            String equipoSeleccionado;
            enums.result resultEsperado;
            for(org.example.people people : personas){
                for(game value : partidos){
                    switch(rand.nextInt(2)){
                        case 0 : equipoSeleccionado = value.getTeam(true).getName();
                            break;
                        case 1: equipoSeleccionado = value.getTeam(false).getName();
                            break;
                        default : equipoSeleccionado = "null";
                            break;
                    }
                    switch(rand.nextInt(3)){
                        case 0 : resultEsperado = enums.result.EMPATE;
                            break;
                        case 1 : resultEsperado = enums.result.GANA;
                            break;
                        case 2 : resultEsperado = enums.result.PIERDE;
                            break;
                        default: resultEsperado = enums.result.NULL;
                            break;
                    }
                    writer.write(people.getName()+";"+ people.getDNI()+";"+Integer.toString(value.getCod())+";"+equipoSeleccionado+";"+ resultEsperado.toString()+'\n');
                }
            }
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}


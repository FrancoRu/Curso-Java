package org.example;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


import java.util.ArrayList;
//CLASE CREADA PARA LA CARGA DE ARCHIVOS .TXT
public class fileUpload {
    public fileUpload(){}
    public static HashMap<Integer, game> getGame(String path, HashMap<String, team> team) {
        try (Scanner scanner = new Scanner(new File(path))) {
            HashMap<Integer , game> game = new HashMap<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] array = line.split(";");
                String team1 = array[0];
                String team2 = array[1];
                int golTeam1 =  Integer.parseInt(array[2]);
                int golTeam2 =  Integer.parseInt(array[3]);
                int cod =  Integer.parseInt(array[4]);
                int codRound = Integer.parseInt(array[5])+1;
                if(golTeam1 >= 0 && golTeam2 >= 0 && array.length == 6){
                    game.put(cod, new game(team.get(team1),team.get(team2), cod, golTeam1, golTeam2, codRound));
                    System.out.println("Added: "+codRound);
                }else{
                    message("getGames");
                }
            }
            return game;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return null;
    }

    //RETORNA UN HASH MAP DE EQUIPOS DEL ARCHIVO EN EL PATH SELECCIONADO
    public static HashMap<String, team> getTeams(String path){
        HashMap<String, team> list = new HashMap<>();
        try(Scanner scanner = new Scanner(new File(path))){
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String [] Arr = line.split(";");
                list.put(Arr[0], new team(Arr[0], Arr[1]));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }
    //GENERA EL PRONOSTICO
    public static void getBet(String path, HashMap<Long, people> listOfPeople, HashMap<Integer, game> listOfGame){
        try (Scanner scanner = new Scanner(new File(path))) {
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] Arr = line.split(";");
                enums.result result = enums.result.valueOf(Arr[4]);
                if(Arr.length == 5){ 
                    listOfPeople.get(Long.parseLong(Arr[1])).addBet(listOfGame.get(Integer.parseInt(Arr[2])).getTeam(Arr[3]), result, Integer.parseInt(Arr[2]));
                }
                //EXPLICACION DE LAS DOS LINEAS ANTERIORES:
                //1: TRAIGO A LA PERSONA DE UN HASH MAP CON SU DNI (LA CLAVO LONG)
                //2: LLAMO AL METODO addPronostico(equipo, enums)
                //3: BUSCO EL EQUIPO SELECCIONADO DENTRO DEL HASH MAP PARTIDOS PARA EVITAR COPIAS INESESARIAS DE OBJETOS
                //4: CONSULTO SI EL NOMBRE ES IGUAL AL QUE TENGO EN EL ARREGLO PARA ENVIAR EL EQUIPO 1 O EL EQUIPO 2
                //5: POR ULTIMO AGREGGO EL RESULTADO
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    //RETORNO DE APUESTA PARA LA BASE DE DATOS
    public static ArrayList<String> getBet(String path){
        ArrayList<String> listBet = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(path))){
            while(scanner.hasNextLine()){
                listBet.add(scanner.nextLine());
            }
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        return listBet;
    }
    //RETORNA UN HASH MAP DE PERSONAS
    public static HashMap<Long, people> getPeople(String path, int points, int pointsRound, int pointsPhase){
        HashMap<Long, people> listOfPeople = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] array = line.split(";");
                listOfPeople.put(Long.parseLong(array[1]), new people(array[0], Long.parseLong(array[1]), points, pointsRound, pointsPhase));
            }
            return listOfPeople;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return null;
    }
    public  static ArrayList<String> getPhases(String path){
        ArrayList<String> listOfPhases = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))){
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                listOfPhases.add(line);
            }
            return listOfPhases;
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public static ArrayList<String> getRound(String path){
        ArrayList<String> listOfRound = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                listOfRound.add(line);
            }
            return listOfRound;
        }catch (IOException e){
            messageFileError(e);
        }
        return null;
    }
    //MESSAGE
    private static void message(String function){
        System.out.println("File corrupt of function : "+function);
    }
    private static void messageFileError(IOException e){
        System.out.println("Error: "+e.getMessage() + " Message: "+e.getMessage() + '\n');
    }
}

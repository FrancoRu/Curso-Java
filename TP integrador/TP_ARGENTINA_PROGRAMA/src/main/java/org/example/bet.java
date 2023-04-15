package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class bet {
    private final ArrayList<team> selectedTeams; //ARREGLO DINAMICO DE EQUIPOS
    private final ArrayList<enums.result> finalResul; //ARREGLO DINAMICO DE ENUMS

    private final  HashMap<Integer, Integer> listResult;
    private final ArrayList<Integer> listOfCodGames;
    private final int points;

    public bet(int points){
        this.selectedTeams = new ArrayList<>();
        this.finalResul = new ArrayList<>();
        this.listOfCodGames = new ArrayList<>();
        this.listResult = new HashMap<>();
        this.points = points;
    }//CONSTRUCTOR DE PRONOSTICO
    public void setAddBet(team selectedTeam, enums.result result, int codGame){ // CARGA DE LOS ARREGLOS CON LA INFORMACION ANTES DICHA
        this.selectedTeams.add(selectedTeam);
        this.finalResul.add(result);
        this.listOfCodGames.add(codGame);
    }

    public int getCalculatePoints(HashMap<Integer, game> listOfGames){ // RETORNA EL PUNTAJE TOTAL DE LA PERSONA
        int points = 0;

        for (game p : listOfGames.values()){
            int cod = this.listOfCodGames.indexOf(p.getCod());
            if(cod>-1){

                if(this.finalResul.get(cod) == p.getWinner(this.selectedTeams.get(cod))){
                    points += this.points;
                    this.listResult.put(p.getCodRound(), this.listResult.getOrDefault(p.getCodRound(), 0) + 1);
                }
            }
        }

        return points;
    }
    public int getRound(HashMap<Integer, Integer> listRound){
        int point = 0;
        for(Map.Entry<Integer, Integer> entry : listRound.entrySet()){
            int keyListRound = entry.getKey();
            int valueListRound = entry.getValue();
            int valueListResult =  listResult.getOrDefault(keyListRound,0);
            if(valueListRound == valueListResult && valueListResult !=0){
                point++;

            }
        }
        return point;
    }
    public int getPhase(HashMap<Integer, Integer> list, HashMap<Integer, Integer> listRound){
        int point = 0;
        int ant =1;
        int aux = 0;
        for(Integer cantRound : list.values()){

            for(int i = ant ; i<cantRound+ant; i++){
                int valueListRound = listRound.get(i);
                int valueListResult =  listResult.getOrDefault(i,0);
                if(valueListRound == valueListResult && valueListResult != 0){
                    aux++;
                }
            }
            if(aux == cantRound) point++;
            aux = 0;
            ant +=cantRound+1;
        }
        return point;
    }
}

package org.example;

import java.util.HashMap;
//CLASE QUE DEFINE LA ESTRUCTURA DE PERSONA

class people {
    private final String name;
    private final long DNI;
    private final bet ownBet; //EN ESTE OBJETO SE ALMACENARA EL PRONOSTICO DE CADA PERSONA YA QUE CADA UNO ES UNICO POR PERSONA
    private int points;
    private final int pointsPhase;
    private final int pointsRounds;
    private int rounds;
    private int game;
    private int phase;

    public people(String name, long DNI, int points, int pointsRound, int pointsPhase){ //CONSTRUCTOR DE PERSONA
        this.name = name;
        this.DNI = DNI;
        ownBet = new bet(points);
        this.points = 0;
        this.pointsPhase = pointsPhase;
        this.pointsRounds = pointsRound;
    }

    //SE UTILIZA PARA SETEAR EL PRONOSTICO
    public void addBet(team selectedTeam, enums.result bet, int codGame){
        this.ownBet.setAddBet(selectedTeam, bet, codGame);
    }

    //RETORNA EL NOMBRE DE LA PERSONA
    public String getName(){
        return this.name;
    }

    //RETORNA EL DNI O DOCUMENTO DE LA PERSONA
    public long getDNI(){
        return this.DNI;
    }

    //RETORNA EL ARRAYLIST DE PRONOSTICO QUE TIENE LA PERSONA PARA PODER OPERAR CON EL MISMO

    //RETORNO DE LOS PUNTOS OBTENIDOS POR LA PERSONA INDICADA
    public int getPoints(){
        return this.points;
    }
    //LLAMO A LA CLASE PRONOSTICO PARA QUE CALCULE LOS PUNTOS DE CADA PERSONA

    public void setPoints(HashMap<Integer, org.example.game> playGame, HashMap<Integer, Integer> listOfRounds, HashMap<Integer, Integer> listOfPhases){
        this.game = this.ownBet.getCalculatePoints(playGame);
        this.rounds = this.ownBet.getRound( listOfRounds );
        this.phase = this.ownBet.getPhase( listOfPhases , listOfRounds );
        this.points = this.game + this.rounds * this.pointsRounds + this.phase * this.pointsPhase;
    }
    //SOBRECARGA OPERADOR
    @Override
    public String toString(){
        return "**********************************************" + '\n' +
                "Name: " + this.getName() + " DNI: " + this.getDNI() + '\n' +
                "Total points: " + this.getPoints() + '\n' +
                "Correct games: " + this.game + '\n' +
                "Correct rounds: " + this.rounds + '\n' +
                "Correct phases: " + this.phase + '\n' +
                "**********************************************" + '\n';
    }
}

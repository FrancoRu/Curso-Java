package org.example;

import java.util.HashMap;

class people {
    private final String name;
    private final long DNI;
    private final bet ownBet;
    private int points;
    private final int pointsPhase;
    private final int pointsRounds;
    private final int pointsGame;
    private int rounds;
    private int game;
    private int phase;

    public people(String name, long DNI, int points, int pointsRound, int pointsPhase){ //CONSTRUCTOR DE PERSONA
        if(name==null || name.isEmpty() || DNI <= 0 || points < 0 || pointsRound < 0 || pointsPhase < 0){
            throw new IllegalArgumentException("one or all of the parameters are invalid.");
        }
        this.name = name;
        this.DNI = DNI;
        ownBet = new bet();
        this.points = 0;
        this.pointsPhase = pointsPhase;
        this.pointsRounds = pointsRound;
        this.pointsGame = points;
    }

    public void addBet(team selectedTeam, enums.result bet, int codGame){
        this.ownBet.setAddBet(selectedTeam, bet, codGame);
    }

    public String getName(){
        return this.name;
    }

    public long getDNI(){
        return this.DNI;
    }

    public int getPoints(){
        return this.points;
    }
    public void setPoints(HashMap<Integer, game> playGame, HashMap<Integer, Integer> listOfRounds, HashMap<Integer, Integer> listOfPhases){
        this.game = this.ownBet.getCalculatePoints(playGame);
        this.rounds = this.ownBet.getRound( listOfRounds );
        this.phase = this.ownBet.getPhase( listOfPhases , listOfRounds );
        this.points = this.game * this.pointsGame + this.rounds * this.pointsRounds + this.phase * this.pointsPhase;
    }

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

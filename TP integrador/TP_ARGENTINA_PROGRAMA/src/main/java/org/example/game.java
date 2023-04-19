package org.example;



class game {
    private final team team1;
    private final team team2;
    private final int scoreTeam1;
    private final int scoreTeam2;
    private final int cod;
    private final int codRound;
    public game(team team1, team team2, int scoreTeam1, int scoreTeam2, int cod, int codRound){
        if((team1 == null )|| team2 == null || scoreTeam1 < 0 || scoreTeam2 < 0 || cod < 0 || codRound < 0){
            throw new IllegalArgumentException("one or all of the parameters are invalid.");
        }
        this.team1 = team1;
        this.team2 = team2;
        this.scoreTeam1 = scoreTeam1;
        this.scoreTeam2 = scoreTeam2;
        this.cod = cod;
        this.codRound = codRound;
    }
    public int getCod(){
        return this.cod;
    }
    public  int getCodRound(){
        return this.codRound;
    }
    public team getTeam(boolean value){
        return value? this.team1 : this.team2;
    }
    public team getTeam(String name){
        return name.equals(this.team1.getName())? this.team1 : this.team2;
    }
    public int getScore(boolean value){
        return value? this.scoreTeam1 : this.scoreTeam2;
    }

    public enums.result getWinner(team selectecTeam){
        if(this.scoreTeam1 == this.scoreTeam2) return  enums.result.EMPATE;
        if(selectecTeam.getName().equals(this.team1.getName())){
            return (this.scoreTeam1 > this.scoreTeam2)? enums.result.GANA : enums.result.PIERDE;
        }else{
            return (this.scoreTeam1 > this.scoreTeam2)? enums.result.PIERDE : enums.result.GANA;
        }
    }
    @Override
    public String toString(){

        return "*********************"+ '\n'+"Team 1: "+this.team1.getName()+'\n'+"Team 2: "+this.team2.getName()+'\n'+"Score Team 1: "+this.scoreTeam1+'\n'
                +"Score Team 2: "+this.scoreTeam2+'\n'+"id Game: "+this.cod+'\n'+"*********************"+ '\n';

    }

}


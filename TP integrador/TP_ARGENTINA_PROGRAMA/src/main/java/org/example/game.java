package org.example;

//CLASE QUE DEFINE LA ESTRUCTURA DE PARTIDOS


class game {
    //UN PARTIDO LO DEFINI CON 2 EQUIPOS, 2 CONTADORES DE GOLES Y UN CODIGO DE IDENTIFICACION DE PARTIDO
    private final team team1;
    private final team team2;
    private final int scoreTeam1;
    private final int scoreTeam2;
    private final int cod;
    private final int codRound;
    public game(team team1, team team2, int scoreTeam1, int scoreTeam2, int cod, int codRound){ //CONSTRUCTOR DE PARTIDOS
        this.team1 = team1;
        this.team2 = team2;
        this.scoreTeam1 = scoreTeam1;
        this.scoreTeam2 = scoreTeam2;
        this.cod = cod;
        this.codRound = codRound;
    }
    public int getCod(){ //RETORNA EL CODIGO DEL PARTIDO
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

    public enums.result getWinner(team selectecTeam){ //RETORNA LA CONDICION DEL EQUIPO SELECCIONADO EN EL PARTIDO
        if(this.scoreTeam1 == this.scoreTeam2) return  enums.result.EMPATE;
        if(selectecTeam.getName().equals(this.team1.getName())){
            return (this.scoreTeam1 > this.scoreTeam2)? enums.result.GANA : enums.result.PIERDE;
        }else{
            return (this.scoreTeam1 > this.scoreTeam2)? enums.result.PIERDE : enums.result.GANA;
        }
    }
    @Override
    public String toString(){
        return "Equipo 1: "+this.team1.getName()+'\n'+"Equipo 2: "+this.team2.getName()+'\n'+"Goles Equipo 1: "+this.scoreTeam1+'\n'
                +"Goles Equipo 2: "+this.scoreTeam2+'\n'+"Codigo de partido: "+this.cod+'\n';

    }

}


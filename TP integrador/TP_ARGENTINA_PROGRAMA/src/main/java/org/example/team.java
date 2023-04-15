package org.example;

class team {
    private final String name;      //NOMBBRE DE EQUIPO
    private final String description; //DESCRIPCION DEL EQUIPO

    public team(String name, String description){ //CONSTRUCTOR DE EQUIPOS
        this.description = description;
        this.name = name;
    }
    public String getName(){  //DEVUELVE EL NOMBRE DEL EQUIPO
        return this.name;
    }
    public String getDesc(){    //DEVUELVE LA DESCRIPCION DEL EQUIPO
        return this.description;
    }
    @Override
    public String toString(){
        return "Nombre: "+this.name +'\n'+"description: "+this.description +'\n';
    }
}

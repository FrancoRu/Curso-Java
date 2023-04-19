package org.example;

class team {
    private final String name;
    private final String description;

    public team(String name, String description){
        if(name == null || name.isEmpty() || description == null || description.isEmpty() ){
                throw new IllegalArgumentException("name or description invalid.");
        }
        this.description = description;
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public String getDesc(){
        return this.description;
    }
    @Override
    public String toString(){
        return "Name: "+this.name +'\n'+"description: "+this.description +'\n';
    }
}

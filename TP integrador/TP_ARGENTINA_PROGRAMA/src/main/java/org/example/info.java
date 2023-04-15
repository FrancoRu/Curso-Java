package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.sql.ResultSet;

public  class info{
    private static Connection conn;
    private static PreparedStatement pstmt;

    private static final HashMap<Integer, Integer> roundMatches;
    private static final HashMap<Integer, Integer> phasedRounds;
    private static final Scanner scanner;

    static {
        conn = null;
        pstmt = null;
        roundMatches = new HashMap<>();
        phasedRounds = new HashMap<>();
        scanner = new Scanner(System.in);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println("Error loading data: " + e.getMessage());
        }
        String url = "jdbc:mysql://localhost:3306/MyFirstDataBase";
        String user = "root";
        String pass = "password";
        setConn(url, user, pass);
        downRoundMatches();
        downPhasedRounds();
    }
    //SETTERS
    private static void setConn(String url, String username, String password){
        try{
            conn = DriverManager.getConnection(url, username, password);
        }catch(SQLException e){
            messageSQL(e);
        }
    }
    private static void setPreparedStatement(String sql){
        try{
            pstmt = conn.prepareStatement(sql);
        }catch(SQLException e){
            messageSQL(e);
        }
    }
    private static void incrementMapValue(Map<Integer, Integer> map, int key) {
        map.put(key, map.getOrDefault(key, 0) + 1);
    }

    //GETTERS
    private static PreparedStatement getPreparedStatement(){
        return pstmt;
    }

    //BETS DOWNLOADS

    private static void downBets(HashMap<Long, people> listOfPeople, HashMap<String, team> listOfTeams){
        try {
            String sql = "SELECT * FROM PERSON_BETS_MATCH_TEAM" ;
            setPreparedStatement(sql);
            try (ResultSet resultSet = getPreparedStatement().executeQuery()) {
                while (resultSet.next()) {
                    int idGame = resultSet.getInt("idGame");
                    int idTeam = resultSet.getInt("idTeam");
                    int idEnum = resultSet.getInt("idEnum");
                    int idPeople = resultSet.getInt("idPeople");
                    listOfPeople.get(FindPeople(idPeople)).addBet(
                            listOfTeams.get(FindTeam(idTeam)), FindEnum(idEnum), idGame);
                }
                for (people p : listOfPeople.values()) {
                    p.setPoints(downGames(listOfTeams), roundMatches, phasedRounds);
                }
                listOfPeople = sortPeople(listOfPeople);
                show(listOfPeople);
            }
        }catch (SQLException e) {
            messageSQL(e);
        }
    }
    //INFORMATION DOWNLOAD
    private static HashMap<Long, people> downPeople(int points, int pointRounds, int pointsPhase){
        HashMap<Long, people> listOfPeople = new HashMap<>();
        try {
            String sql = "SELECT * FROM PEOPLE" ;
            setPreparedStatement(sql);
            try (ResultSet resultSet = getPreparedStatement().executeQuery()) {
                while(resultSet.next()){
                    String username = resultSet.getString("Username");
                    long dni = resultSet.getLong("UserDNI");
                    listOfPeople.put(dni, new people(username, dni, points,pointRounds, pointsPhase));
                }
                return listOfPeople;
            }
        }catch (SQLException e){
            messageSQL(e);
        }
        return null;
    }
    private static HashMap<String, team> downTeam(){
        try{
            HashMap<String, team> ListOfTeam = new HashMap<>();
            String sql = "SELECT * FROM TEAM";
            setPreparedStatement(sql);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                while (resultSet.next()){
                    String nameTeam = resultSet.getString("nameTeam");
                    String descTeam = resultSet.getString("descriptionTeam");
                    ListOfTeam.put(nameTeam, new team(nameTeam, descTeam));
                }
                return ListOfTeam;
            }
        }catch (SQLException e) {
            messageSQL(e);
        }
        return null;
    }
    private static ArrayList<enums.result> downEnum() {
        try {
            ArrayList<enums.result> listOfEnums = new ArrayList<>();
            String sql = "SELECT * FROM ENUMS";
            setPreparedStatement(sql);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                while (resultSet.next()) {
                    String resul = resultSet.getString("enums");
                    try {
                        enums.result result = enums.result.valueOf(resul);
                        listOfEnums.add(result);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                return listOfEnums;
            }
        } catch (SQLException e) {
            messageSQL(e);
        }
        return null;
    }
    private static HashMap<Integer, game> downGames(HashMap<String, team> listOfTeams){
        try {
            HashMap<Integer, game> listOfGames = new HashMap<>();
            String sql = "SELECT * FROM GAME";
            setPreparedStatement(sql);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                while (resultSet.next()){
                    int idTeam1 = resultSet.getInt("idTeam1");
                    int idTeam2 = resultSet.getInt("idTeam2");
                    int scoreEquip1 = resultSet.getInt("scoreGame1");
                    int scoreEquip2 = resultSet.getInt("scoreGame2");
                    int id = resultSet.getInt("id");
                    int idRound = resultSet.getInt("idRound");
                    listOfGames.put(id,new game(
                            listOfTeams.get(FindTeam(idTeam1)),
                            listOfTeams.get(FindTeam(idTeam2)),
                            scoreEquip1, scoreEquip2, id, idRound
                    ));
                }
                return listOfGames;
            }
        }catch (SQLException e){
            messageSQL(e);
        }
        return null;
    }
    //INFORMATION DOWNLOADS
    private static enums.result FindEnum(int key){
        try{
            String sql = "SELECT enums FROM ENUMS WHERE id = ?";
            setPreparedStatement(sql);
            getPreparedStatement().setInt(1, key);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                if(resultSet.next()) {
                    String resul = resultSet.getString("enums");
                    try {
                        return enums.result.valueOf(resul);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }catch (SQLException e){
            messageSQL(e);
        }
        return null;
    }
    private static team FindTeams(int key){
        try{
            String sql = "SELECT * FROM TEAM WHERE id = ?";
            setPreparedStatement(sql);
            getPreparedStatement().setInt(1, key);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                if(resultSet.next()){
                    return new team(resultSet.getString("nameTeam"),resultSet.getString("descriptionTeam") );
                }
            }
        }catch (SQLException e){
            messageSQL(e);
        }
        return null;
    }
    private static people FindPeoples(int key){
        try{
            String sql = "SELECT * FROM PEOPLE WHERE id = ?";
            setPreparedStatement(sql);
            getPreparedStatement().setInt(1, key);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                if(resultSet.next()){
                    return new people(resultSet.getString("Username"),
                            Long.parseLong(resultSet.getString("UserDNI")), 0,0,0);
                }
            }

        }catch (SQLException e){
            messageSQL(e);
        }
        return null;
    }
    private static String FindTeam(int key){
        try{
            String sql = "SELECT nameTeam FROM TEAM WHERE id = ?";
            setPreparedStatement(sql);
            getPreparedStatement().setInt(1, key);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                if(resultSet.next()){
                    return resultSet.getString("nameTeam");
                }
            }
        }catch (SQLException e){
            messageSQL(e);
        }
        return null;
    }
    private static Long FindPeople(int key){
        try{
            String sql = "SELECT UserDNI FROM PEOPLE WHERE id = ?";
            setPreparedStatement(sql);
            getPreparedStatement().setInt(1, key);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                if(resultSet.next()){
                    return Long.parseLong(resultSet.getString("UserDNI"));
                }
            }
        }catch (SQLException e){
            messageSQL(e);
        }
        return null;
    }

    private static int FindIdPeople(Long DNI){
        try{
            String sql = "SELECT id FROM PEOPLE WHERE UserDNI = ?";
            setPreparedStatement(sql);
            getPreparedStatement().setString(1, Long.toString(DNI));
            try(ResultSet rs = getPreparedStatement().executeQuery()){
                if(rs.next()){
                    return rs.getInt("id");
                }
            }
        }catch (SQLException e) {
            messageSQL(e);
        }
        return -1;
    }
    private static int FindIdTeam(String nameEquipment){
        try {
            String sql = "SELECT id FROM TEAM WHERE nameTeam = ?";
            setPreparedStatement(sql);
            getPreparedStatement().setString(1, nameEquipment);
            try(ResultSet rs = getPreparedStatement().executeQuery()){
                if(rs.next()) return rs.getInt("id");
            }
        }catch (SQLException e){
            messageSQL(e);
        }
        return -1;
    }
    private static int FindIdGame(int idGame){
        try{
            String sql = "SELECT id FROM GAME WHERE id = ?";
            setPreparedStatement(sql);
            getPreparedStatement().setInt(1, idGame);
            try(ResultSet rs = getPreparedStatement().executeQuery()){
                if(rs.next()){
                    return rs.getInt("id");
                }
            }
        }catch (SQLException e){
            messageSQL(e);
        }
        return -1;
    }
    private static int FindIdEnum(String enums){
        try{
            String sql = "SELECT id FROM ENUMS WHERE enums = ?";
            setPreparedStatement(sql);
            getPreparedStatement().setString(1, enums);
            try(ResultSet rs = getPreparedStatement().executeQuery()){
                if(rs.next()){
                    return rs.getInt("id");
                }
            }
        }catch (SQLException e) {
            messageSQL(e);
        }
        return -1;
    }
    private static  int FindIdRound(String round){
        try{
            String sql = "SELECT id FROM ROUNDS WHERE nameRounds = ?";
            setPreparedStatement(sql);
            getPreparedStatement().setString(1, round);
            try(ResultSet rs = getPreparedStatement().executeQuery()){
                if(rs.next()){
                    return rs.getInt("id");
                }
            }
        }catch (SQLException e) {
            messageSQL(e);
        }
        return -1;
    }
    private static  String FindRound(int key){
        try{
            String sql = "SELECT nameRounds FROM ROUNDS WHERE id = ?";
            setPreparedStatement(sql);
            getPreparedStatement().setInt(1, key);
            try(ResultSet rs = getPreparedStatement().executeQuery()){
                if(rs.next()){
                    return rs.getString("nameRounds");
                }
            }
        }catch (SQLException e) {
            messageSQL(e);
        }
        return null;
    }
    private static void downRoundMatches(){
        String sql = "SELECT idRound FROM GAME;";
        try{
            setPreparedStatement(sql);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                while (resultSet.next()){
                    incrementMapValue(roundMatches,resultSet.getInt(1));
                }
            }
        }catch (SQLException e){
            messageSQL(e);
        }
    }
    private static void downPhasedRounds(){
        String sql = "SELECT idFase FROM ROUNDS;";
        try{
            setPreparedStatement(sql);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                while (resultSet.next()){
                    incrementMapValue(phasedRounds,resultSet.getInt(1));
                }
            }
        }catch (SQLException e){
            messageSQL(e);
        }
    }
    //FILE UPLOADS
    //EQUIPMENT INFORMATION UPLOAD

   private static void LoadDataTeam(String path) {
        HashMap<String, team> ListOfEquipment = fileUpload.getTeams(path);
        String sql = "INSERT INTO TEAM (nameTeam, descriptionTeam) SELECT ?, ? FROM DUAL WHERE NOT EXISTS (SELECT * FROM TEAM WHERE nameTeam = ?)";
        setPreparedStatement(sql);
        try {
            for (team team : ListOfEquipment.values()) {
                getPreparedStatement().setString(1, team.getName());
                getPreparedStatement().setString(2, team.getDesc());
                getPreparedStatement().setString(3, team.getName());
                getPreparedStatement().executeUpdate();
            }
            upload("equipment");
        }catch (SQLException e) {
           messageSQL(e);
        }
    }
    //PEOPLES INFORMATION UPLOAD
    private static void LoadDataPeople(String path, int points, int pointsRounds, int pointsPhases){
        HashMap<Long, people> people = fileUpload.getPeople(path, points, pointsRounds, pointsPhases);
        String sql = "INSERT INTO PEOPLE (UserDNI, Username) SELECT ?,? FROM DUAL WHERE NOT EXISTS (SELECT * FROM PEOPLE WHERE UserDNI = ?)";
        setPreparedStatement(sql);
        try{
            if(people==null) return;
            for(org.example.people Persona : people.values()){
                getPreparedStatement().setString(1, Long.toString(Persona.getDNI()));
                getPreparedStatement().setString(2, Persona.getName());
                getPreparedStatement().setString(3, Long.toString(Persona.getDNI()));
                getPreparedStatement().executeUpdate();
            }
            upload("people");
        }catch(SQLException e){
            messageSQL(e);
        }
    }
    //ENUMS UPLOAD
    private static void LoadEnum(){
        enums.result[] result = {enums.result.EMPATE, enums.result.PIERDE, enums.result.GANA};
        String sql = "INSERT INTO ENUMS (enums) SELECT ? FROM  DUAL WHERE NOT EXISTS (SELECT * FROM ENUMS WHERE enums = ?)";
        try{
            setPreparedStatement(sql);
            for(enums.result res : result){
                getPreparedStatement().setString(1, res.toString());
                getPreparedStatement().setString(2, res.toString());
                getPreparedStatement().executeUpdate();
            }
            upload("EnumsResul");
        }catch(SQLException e){
            messageSQL(e);
        }
    }
    //GAMES INFORMATION UPLOAD
    private static void LoadDataGame(String pathGame, String pathEquipment){
        HashMap<String, team> teams = fileUpload.getTeams(pathEquipment);
        HashMap<Integer, game> games = fileUpload.getGame(pathGame, teams);
        int idTeam1, idTeam2;
        try{
            if(games == null) return;
            for(org.example.game game : games.values()){
                idTeam1 = FindIdTeam(game.getTeam(true).getName());
                idTeam2 = FindIdTeam(game.getTeam(false).getName());
                /*
                 *This SQL statement inserts the specified values into the "Game" table and if a
                 *unique key violation, update the values of the columns specified in the clause
                 *"ON DUPLICATE KEY UPDATE".
                 */
                if(idTeam1!=-1 && idTeam2!=-1) {
                    String sql = "INSERT INTO GAME (id, idTeam1, idTeam2, scoreGame1, scoreGame2,idRound) VALUES (?,?,?,?,?,?) " +
                            "ON DUPLICATE KEY UPDATE idTeam1 = VALUES(idTeam1), idTeam2 = VALUES(idTeam2), " +
                            "scoreGame1 = VALUES(scoreGame1), scoreGame2 = VALUES(scoreGame2)";
                    setPreparedStatement(sql);
                    getPreparedStatement().setInt(1, game.getCod());
                    getPreparedStatement().setInt(2, idTeam1);
                    getPreparedStatement().setInt(3, idTeam2);
                    getPreparedStatement().setInt(4, game.getScore(true));
                    getPreparedStatement().setInt(5, game.getScore(false));
                    getPreparedStatement().setInt(6, game.getCodRound());
                    getPreparedStatement().executeUpdate();
                }
            }
            upload("games");
        }catch(SQLException e){
            messageSQL(e);
        }
    }
    //BETS UPLOAD
    private static void LoadDataBet(String path){
        ArrayList<String> list = fileUpload.getBet(path);
        try{
            String[] arrBet;
            int idUser, idGame, idTeam, idEnum, idRound;
            for(String List : list){
                arrBet = List.split(";");
                idUser = FindIdPeople(Long.parseLong(arrBet[1]));
                idGame = FindIdGame(Integer.parseInt(arrBet[2]));
                idRound = Integer.parseInt(arrBet[3]);
                idTeam = FindIdTeam(arrBet[4]);
                idEnum = FindIdEnum(arrBet[5]);
                if(idUser > -1 && idTeam > -1 && idEnum > -1 && idGame > -1) {
                    String sql = "INSERT INTO PERSON_BETS_MATCH_TEAM (idGame, idTeam, idEnum, idPeople, idRound) " +
                            "SELECT ?, ?, ?, ?, ? FROM DUAL " +
                            "WHERE NOT EXISTS (SELECT * FROM PERSON_BETS_MATCH_TEAM WHERE idPeople = ? AND idGame = ?)";
                    setPreparedStatement(sql);
                    getPreparedStatement().setInt(1, idGame);
                    getPreparedStatement().setInt(2, idTeam);
                    getPreparedStatement().setInt(3, idEnum);
                    getPreparedStatement().setInt(4, idUser);
                    getPreparedStatement().setInt(5,idRound);
                    getPreparedStatement().setInt(6, idUser);
                    getPreparedStatement().setInt(7, idGame);
                    getPreparedStatement().executeUpdate();
                }
            }
            upload("bets");
        }catch(SQLException e){
            messageSQL(e);
        }
    }
    private static void LoadPhases(String path){
        ArrayList<String> listOfPhases = fileUpload.getPhases(path);
        if(listOfPhases == null) return;
        try{
            String sql = "INSERT INTO FASE (nameFase) VALUES (?);";
            setPreparedStatement(sql);
            for(String p : listOfPhases){
                getPreparedStatement().setString(1, p);
                getPreparedStatement().executeUpdate();
            }
        }catch (SQLException e){
            messageSQL(e);
        }
    }
    private static void LoadRound(String path){
        ArrayList<String> listOfRound = fileUpload.getRound(path);
        if(listOfRound != null){
            try {
                String sql = "INSERT INTO ROUNDS (nameRounds, idFase) SELECT ?,? FROM" +
                " DUAL WHERE NOT EXISTS (SELECT * FROM ROUNDS WHERE nameRounds = ?)";
                setPreparedStatement(sql);
                for (String r : listOfRound){
                    String []array = r.split(";");
                    getPreparedStatement().setString(1, array[0]);
                    getPreparedStatement().setInt(2, Integer.parseInt(array[1]));
                    getPreparedStatement().setString(3, array[0]);
                    getPreparedStatement().executeUpdate();
                }
            }catch (SQLException e){
                messageSQL(e);
            }
        }
    }
    //
    private static void show(HashMap<Long, people> listOfPeople){
        for(people p : listOfPeople.values()) {
            System.out.println(p.toString());
        }
    }
    //SORT HASHMAP PEOPLE
    private static HashMap<Long, people> sortPeople(HashMap<Long, people> listOfPeople){
        List<people> listOfPersonas = new ArrayList<>(listOfPeople.values());
        Collections.sort(listOfPersonas, (p1, p2) -> Integer.compare(p2.getPoints(), p1.getPoints()));
        HashMap<Long, people> listOfPeopleSort = new LinkedHashMap<>();
        for (people p : listOfPersonas) {
            listOfPeopleSort.put(p.getDNI(), p);
        }
        return listOfPeopleSort;
    }

    //MESSAGE
    private static void messageSQL(SQLException e){
        System.out.println("Error: " + e.getErrorCode() + " Description: " + e.getMessage() + " State: " + e.getSQLState() + '\n');
    }
    private static void  messageMenus(String context){
        System.out.println("invalid parameters in " + context);
    }
    private static void upload(String function){
        System.out.println("Successful loading of "+ function);
    }
    //CLOSURE OF BUFFERS
    private static void Close(){
        try {
            if (pstmt!= null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            System.out.println("Error closing connection: " + ex.getMessage());
        }
        scanner.close();
    }
    //START APP
    public static void start(){
        boolean band = true;
        while (band) {
            System.out.println("Welcome.....");
            System.out.println("------------");
            System.out.println("Select options:");
            System.out.println("1: Upload Information.");
            System.out.println("2: Load files.");
            System.out.println("3: Download Information.");
            System.out.println("4: Show data.");
            System.out.println("99: Exit.");
            int opc = scanner.nextInt();
            switch (opc){
                case 1 -> uploadInformation();
                case 2 -> loadFiles();
                case 3 -> DownloadInformation();
                case 4 -> ShowData();
                case 99 -> band = false;
                default -> messageMenus("Start Menu ");
            }
        }
        Close();
    }
    private static  void uploadInformation(){
        System.out.println("Upload Information");
        System.out.println("------------------");
        boolean band = true;
        while (band) {
            System.out.println("Select options:");
            System.out.println("1: Upload People.");
            System.out.println("2: Upload Teams.");
            System.out.println("3: Upload Games.");
            System.out.println("4: Upload Bets.");
            System.out.println("99: Menu before.");
            int opc = scanner.nextInt();
            switch (opc) {
                case 1 -> uploadMenuPeople();
                case 2 -> uploadMenuTeam();
                case 3 -> uploadMenuGames();
                case 4 -> uploadMenuBets();
                case 99 -> band = false;
                default -> messageMenus("Load Menu");
            }
        }
    }
    private static  void DownloadInformation(){
        System.out.println("Upload Information");
        System.out.println("------------------");
        boolean band = true;
        while (band) {
            System.out.println("Select options:");
            System.out.println("1: Download People.");
            System.out.println("2: Download Teams.");
            System.out.println("3: Download Games.");
            System.out.println("4: Download Bets.");
            System.out.println("5: Download authorized results.");
            System.out.println("99: Menu before.");
            int opc = scanner.nextInt();
            switch (opc) {
                case 1 -> downMenuPeople();
                case 2 -> downMenuTeams();
                case 3 -> downMenuGames();
                case 4 -> downMenuBets();
                case 5 -> downMenuEnum();
                case 99 -> band = false;
                default -> messageMenus("Download Menu");
            }
        }
    }
    private static  void ShowData(){
        System.out.println("Show Information");
        System.out.println("------------------");
        boolean band = true;
        while (band) {
            System.out.println("Select options:");
            System.out.println("1: Show People.");
            System.out.println("2: Show Teams.");
            System.out.println("3: Show Games.");
            System.out.println("4: Show Bets.");
            System.out.println("5: Show Points.");
            System.out.println("99: Menu before.");
            int opc = scanner.nextInt();
            switch (opc) {
                case 1 -> showMenuPeople();
                case 2 -> showMenuTeams();
                case 3 -> showMenuGames();
                case 4 -> showMenuBets();
                case 5 -> showMenuResult();
                case 99 -> band = false;
                default -> messageMenus("Show Menu");
            }
        }
    }
    private static void loadFiles(){
        boolean band = true;
        int points, pointsPhase, pointsRound;
        String path , path2;
        while (band){
            System.out.println("Select option");
            System.out.println("1: Load info Teams.");
            System.out.println("2: Load info People.");
            System.out.println("3: Load info Game.");
            System.out.println("4: Load info Bets.");
            System.out.println("5: Load info Rounds.");
            System.out.println("6: Load info Phases.");
            System.out.println("7: Load Enums.");
            System.out.println("99: Before menu.");
            System.out.print("Option: ");
            int opc = scanner.nextInt();
            scanner.nextLine();
            switch (opc){
                case 1: {
                    System.out.print("enter file path: ");
                    path = scanner.nextLine();
                    LoadDataTeam(path);
                }
                break;
                case 2: {
                    System.out.print("enter file path: ");
                    path = scanner.nextLine();
                    System.out.println("enter points per hit: ");
                    points = scanner.nextInt();
                    System.out.println("enter points for round hit: ");
                    pointsRound = scanner.nextInt();
                    System.out.println("enter points for phase success: ");
                    pointsPhase = scanner.nextInt();
                    LoadDataPeople(path, points, pointsRound, pointsPhase);
                }
                break;
                case 3: {
                    System.out.print("enter file path Game: ");
                    path = scanner.nextLine();
                    System.out.print("enter file path Team: ");
                    path2 = scanner.nextLine();
                    LoadDataGame(path, path2);
                }
                case 4: {
                    System.out.print("enter file path Bet: ");
                    path = scanner.nextLine();
                    LoadDataBet(path);
                }
                case 5:{
                    System.out.print("enter file path Rounds: ");
                    path = scanner.nextLine();
                    LoadRound(path);
                }
                break;
                case 6:{
                    System.out.print("enter file path Phases: ");
                    path = scanner.nextLine();
                    LoadPhases(path);
                }
                break;
                case 7:LoadEnum();
                break;
                case 99: band = false;
                default: messageMenus("load files.");
            }
        }

    }
    //OPTION MENUS
    //MENUS UPLOAD
    private static void uploadMenuPeople(){
        long DNI;
        String name;
        System.out.println("enter DNI: ");
        DNI = scanner.nextLong();
        scanner.nextLine();
        System.out.println("enter name: ");
        name = scanner.nextLine();
        if(name.isEmpty()) {
            messageMenus("Load People");
            return;
        }
        try {
            String sql = "INSERT INTO PEOPLE (UserDNI, Username )SELECT (? , ?) FROM" +
                        " DUAL WHERE NOT EXISTS (SELECT UserDNI FROM PEOPLE WHERE UserDNI = ?)";
            setPreparedStatement(sql);
            getPreparedStatement().setString(1, Long.toString(DNI));
            getPreparedStatement().setString(2, name);
            getPreparedStatement().setString(3, Long.toString(DNI));
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                if(!resultSet.next()){
                    messageMenus("the person already exists");
                }
            }
        }catch (SQLException e){
            messageSQL(e);
        }
        upload("People name: "+name+" DNI: "+DNI);
    }
    private  static  void uploadMenuTeam(){
        String name, descName;
        System.out.println("Enter Team Name: ");
        name = scanner.nextLine();
        System.out.println("Enter Team Description: ");
        descName = scanner.nextLine();
        if(descName.isEmpty() || name.isEmpty()) {
            messageMenus("Load Team");
            return;
        }
        try {
            String sql = "INSERT INTO TEAM (nameTeam, descriptionTeam )SELECT (? , ?) FROM" +
                        " DUAL WHERE NOT EXISTS (SELECT UserDNI FROM TEAM WHERE nameTeam = ?)";
            setPreparedStatement(sql);
            getPreparedStatement().setString(1, name);
            getPreparedStatement().setString(2, descName);
            getPreparedStatement().setString(3, name);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                if(!resultSet.next()){
                    messageMenus("the team already exists");
                }
            }
        }catch (SQLException e){
            messageSQL(e);
        }
        upload("Name Team: "+name+" Description Team: "+descName);
    }
    private static void uploadMenuGames(){
        String nameTeam1, nameTeam2, nameRound;
        int idGame, scoreGame1, scoreGame2;
        int idTeam1, idTeam2, idRound;
        System.out.println("Enter Team 1 Name: ");
        nameTeam1 = scanner.nextLine();
        System.out.println("Enter Team 2 Name: ");
        nameTeam2 = scanner.nextLine();
        System.out.println("Enter score team 1: ");
        scoreGame1 = scanner.nextInt();
        System.out.println("Enter score team 2:");
        scoreGame2 = scanner.nextInt();
        System.out.println("Enter id game: ");
        idGame = scanner.nextInt();
        System.out.println("Enter round name: ");
        nameRound = scanner.nextLine();

        if(nameTeam1.isEmpty() || nameTeam2.isEmpty()||nameRound.isEmpty()) {
            messageMenus("one or all names are empty");
            return;
        }
        if(scoreGame1 < 0 || scoreGame2 < 0|| idGame < 0) {
            messageMenus("one or all scores or id invalid");
            return;
        }
        idTeam1 = FindIdTeam(nameTeam1);
        idTeam2 = FindIdTeam(nameTeam2);
        idRound = FindIdRound(nameRound);
        if(idTeam1<0 || idTeam2 < 0 || idRound < 0){
            messageMenus("one or all names do not exist");
            return;
        }
        try {
            String sql = "INSERT INTO GAME (id, idTeam1, idTeam2, scoreGame1, scoreGame2,idRound) VALUES (?,?,?,?,?,?) " +
                    "ON DUPLICATE KEY UPDATE idTeam1 = VALUES(idTeam1), idTeam2 = VALUES(idTeam2), " +
                    "scoreGame1 = VALUES(scoreGame1), scoreGame2 = VALUES(scoreGame2)";
            setPreparedStatement(sql);
            getPreparedStatement().setInt(1, idGame);
            getPreparedStatement().setInt(2, idTeam1);
            getPreparedStatement().setInt(3, idTeam2);
            getPreparedStatement().setInt(4, scoreGame1);
            getPreparedStatement().setInt(5, scoreGame2);
            getPreparedStatement().setInt(6, idRound);
            getPreparedStatement().executeUpdate();
        }catch (SQLException e){
            messageMenus("the game already exists");
            messageSQL(e);
        }
        upload("game.");
    }
    private static void uploadMenuBets(){
        int idUser, idGame, idTeam, idEnum, idRound;
        long DNI;
        String nameTeam, result, nameRound;
        System.out.println("Enter DNI: ");
        DNI = scanner.nextLong();
        System.out.println("Enter id Game: ");
        idGame = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter name round: ");
        nameRound = scanner.nextLine();
        System.out.println("Enter name Team: ");
        nameTeam = scanner.nextLine();
        System.out.println("Enter result only 'EMPATE, GANA, PIERDE' :");
        result = scanner.nextLine();
        if(DNI < 0 || idGame < 0) {
            messageMenus("DNI or id invalid.");
            return;
        }
        if(nameRound.isEmpty() || nameTeam.isEmpty() || result.isEmpty()){
            messageMenus("one or all parameters are empty.");
            return;
        }
        idUser = FindIdPeople(DNI);
        idRound = FindIdRound(nameRound);
        idTeam = FindIdTeam(nameTeam);
        idEnum = FindIdEnum(result);
        if(idUser == -1 || idRound==-1 || idTeam == -1 || idEnum == -1){
            messageMenus("one or all parameters invalid.");
            return;
        }
        try{
            String sql = "INSERT INTO PERSON_BETS_MATCH_TEAM (idGame, idTeam, idEnum, idPeople, idRound) " +
                          "SELECT ?, ?, ?, ?, ? FROM DUAL " +
                          "WHERE NOT EXISTS (SELECT * FROM PERSON_BETS_MATCH_TEAM WHERE idPeople = ? AND idGame = ?)";
            setPreparedStatement(sql);
            getPreparedStatement().setInt(1, idGame);
            getPreparedStatement().setInt(2, idTeam);
            getPreparedStatement().setInt(3, idEnum);
            getPreparedStatement().setInt(4, idUser);
            getPreparedStatement().setInt(5,idRound);
            getPreparedStatement().setInt(6, idUser);
            getPreparedStatement().setInt(7, idGame);
            int par = getPreparedStatement().executeUpdate();
            if(par>0){
                upload("bets");
            }else{
                messageMenus("the bets already exists");
            }
        }catch(SQLException e){
            messageSQL(e);
        }
    }
    //Menu Downloading
    private static void downMenuPeople(){
        long DNI;
        int idUser;
        System.out.println("enter DNI of the person to search");
        DNI = scanner.nextLong();
        if(DNI<0){
            messageMenus("DNI invalid");
            return;
        }
        idUser = FindIdPeople(DNI);
        if(idUser == -1){
            messageMenus("person not found");
            return;
        }
        try {
            String sql = "SELECT * FROM PEOPLE WHERE id = ?;";
            setPreparedStatement(sql);
            getPreparedStatement().setInt(1,idUser);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                if(resultSet.next()){
                    System.out.println("Name: "+ resultSet.getString("Username"));
                    System.out.println("DNI: "+ resultSet.getString("UserDNI"));
                    System.out.print("Bet Code: ");
                    try{
                        sql = "SELECT id FROM PERSON_BETS_MATCH_TEAM WHERE idPeople = ?;";
                        setPreparedStatement(sql);
                        getPreparedStatement().setInt(1,idUser);
                        try(ResultSet rs = getPreparedStatement().executeQuery()){
                            while (rs.next()){
                                System.out.print(rs.getInt("id")+" ,");
                            }
                        }
                        System.out.println();
                    }catch (SQLException e){
                        messageSQL(e);
                    }
                }
            }
        }catch (SQLException e){
            messageSQL(e);
        }
    }

    private static void downMenuTeams(){
        String nameTeam;
        int idTeam;
        System.out.println("selected team: ");
        nameTeam = scanner.nextLine();
        if(nameTeam.isEmpty()){
            messageMenus(" name team invalid.");
            return;
        }
        idTeam = FindIdTeam(nameTeam);
        if(idTeam == - 1){
            messageMenus("team not found.");
        }
        try{
            String sql = "SELECT * FROM TEAM WHERE id = ?;";
            setPreparedStatement(sql);
            getPreparedStatement().setInt(1, idTeam);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                if(resultSet.next()){
                    team e = new team(resultSet.getString("nameTeam"), resultSet.getString("descriptionTeam"));
                    System.out.println(e.toString());
                }
            }
        }catch (SQLException e){
            messageSQL(e);
        }
    }
    private static void downMenuGames(){
        int idGame;
        System.out.println("selected id game: ");
        idGame = scanner.nextInt();
        if(idGame < 0){
            messageMenus("game not found.");
        }
        try{
            String sql = "SELECT * FROM GAME WHERE id = ?;";
            setPreparedStatement(sql);
            getPreparedStatement().setInt(1, idGame);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                if(resultSet.next()){
                    team Team1 = FindTeams(resultSet.getInt("idTeam1"));
                    team Team2 = FindTeams(resultSet.getInt("idTeam2"));
                    game e = new game(Team1, Team2, resultSet.getInt("scoreGame1"),
                            resultSet.getInt("scoreGame2"),resultSet.getInt("id")
                            ,resultSet.getInt("idRound"));
                    System.out.println(e.toString());
                }
            }
        }catch (SQLException e){
            messageSQL(e);
        }
    }
    private static void downMenuBets(){
        int idBet;
        System.out.println("selected id bet: ");
        idBet = scanner.nextInt();
        if(idBet < 0){
            messageMenus("bet invalid.");
        }
        try{
            String sql = "SELECT * FROM PERSON_BETS_MATCH_TEAM WHERE id = ?;";
            setPreparedStatement(sql);
            getPreparedStatement().setInt(1, idBet);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                if(resultSet.next()){
                    int idGame = resultSet.getInt("idGame");
                    int idEnum = resultSet.getInt("idEnum");
                    int idRound = resultSet.getInt("idRound");
                    team Team = FindTeams(resultSet.getInt("idTeam"));
                    people Person = FindPeoples(resultSet.getInt("idPeople"));
                    if(Person == null || Team == null) return;
                    System.out.println("Name: "+Person.getName());
                    System.out.println("DNI: "+Person.getDNI());
                    System.out.println("id Game: "+idGame);
                    System.out.println("selected name Team: "+Team.getName());
                    System.out.println("Bet: "+FindEnum(idEnum));
                    System.out.println("Round: "+FindRound(idRound));

                }else{
                    messageMenus("bet not found.");
                }
            }
        }catch (SQLException e){
            messageSQL(e);
        }
    }
    private static void downMenuEnum(){
        ArrayList<enums.result> list = downEnum();
        if(list==null){
            messageMenus("enums not found.");
            return;
        }
        System.out.println("authorized results: ");
        for(enums.result r : list){
            System.out.println(r.toString());
        }
    }
    //SHOW MENU
    private static void showMenuPeople(){
        try{
            String sql = "SELECT * FROM PEOPLE;";
            setPreparedStatement(sql);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                if(!resultSet.next()){
                    messageMenus("people not found.");
                    return;
                }
                while(resultSet.next()){
                    System.out.println("Name: "+resultSet.getString("Username"));
                    System.out.println("DNI: "+resultSet.getString("UserDNI"));
                }
            }
        }catch (SQLException e){
            messageSQL(e);
        }
    }
    private static void showMenuTeams(){
        try{
            String sql = "SELECT * FROM TEAM;";
            setPreparedStatement(sql);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                if(!resultSet.next()){
                    messageMenus("teams not found.");
                    return;
                }
                while(resultSet.next()){
                    System.out.println("Name: "+resultSet.getString("nameTeam"));
                    System.out.println("Description: "+resultSet.getString("descriptionTeam"));
                }
            }
        }catch (SQLException e){
            messageSQL(e);
        }
    }
    private static void showMenuGames(){
        try{
            String sql = "SELECT * FROM GAME;";
            setPreparedStatement(sql);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                if(!resultSet.next()){
                    messageMenus("Games not found.");
                    return;
                }
                while(resultSet.next()){
                    team Team1 = FindTeams(resultSet.getInt("idTeam1"));
                    team Team2 = FindTeams(resultSet.getInt("idTeam2"));
                    game e = new game(Team1, Team2, resultSet.getInt("scoreGame1"),
                            resultSet.getInt("scoreGame2"),resultSet.getInt("id")
                            ,resultSet.getInt("idRound"));
                    System.out.println(e.toString());
                }
            }
        }catch (SQLException e){
            messageSQL(e);
        }
    }
    private static void showMenuBets(){
        try {
            String sql = "SELECT * FROM PERSON_BETS_MATCH_TEAM;";
            setPreparedStatement(sql);
            try(ResultSet resultSet = getPreparedStatement().executeQuery()){
                if (!resultSet.next()) {
                    messageMenus("Bets not found.");
                    return;
                }
                while (resultSet.next()) {
                    int idGame = resultSet.getInt("idGame");
                    int idEnum = resultSet.getInt("idEnum");
                    int idRound = resultSet.getInt("idRound");
                    team Team = FindTeams(resultSet.getInt("idTeam"));
                    people Person = FindPeoples(resultSet.getInt("idPeople"));
                    if(Team == null || Person == null) return;
                    System.out.println("Name: " + Person.getName());
                    System.out.println("DNI: " + Person.getDNI());
                    System.out.println("id Game: " + idGame);
                    System.out.println("selected name Team: " + Team.getName());
                    System.out.println("Bet: " + FindEnum(idEnum));
                    System.out.println("Round: " + FindRound(idRound));
                }
            }
        } catch (SQLException e) {
            messageSQL(e);
        }
    }
    private static void showMenuResult(){
        int points, pointsPhase, pointsRound;
        System.out.println("enter points per hit: ");
        points = scanner.nextInt();
        System.out.println("enter points for round hit: ");
        pointsRound = scanner.nextInt();
        System.out.println("enter points for phase success: ");
        pointsPhase = scanner.nextInt();
        downBets(downPeople(points, pointsRound, pointsPhase), downTeam());
    }
}



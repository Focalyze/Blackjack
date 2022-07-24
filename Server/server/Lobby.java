package server;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Lobby {
    private String lobbyName;
    private static ArrayList<Player> players;
    public boolean isEmpty;
    private LobbyStatus lobbyStatus;
    public int numOfPlayers;

    public Lobby(String lobbyName) {
        players = new ArrayList<>();
        this.lobbyStatus = LobbyStatus.Open;
        this.lobbyName = lobbyName;
    }

    public void connectClient(Player player) {
        players.add(player);
        numOfPlayers++;
        final int MAXIMUM_CLIENT = 50;
        if (numOfPlayers == MAXIMUM_CLIENT) {
            this.lobbyStatus = LobbyStatus.Full;
        }
    }

    public void disconnectClient(Player player) {
        players.remove(player);
        numOfPlayers--;
        if (numOfPlayers == 0) {
            isEmpty = true;
        }
    }

    public String displayClientNames() {
        String s = "";
        for (Player player : players) {
            s += player.username + ", ";
        }
        return s;
    }

    public boolean playerExist(Player player) {
        return players.contains(player);
    }

    public LobbyStatus getLobbyStatus() {
        return this.lobbyStatus;
    }

    public String getLobbyName() {
        return this.lobbyName;
    }

    public void send(Message msg) {
        try{
            for(Player player : players) {
                player.getOos().writeObject(msg);
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

enum LobbyStatus {
    Open,
    Full
}

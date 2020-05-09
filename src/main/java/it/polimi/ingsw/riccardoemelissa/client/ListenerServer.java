package it.polimi.ingsw.riccardoemelissa.client;

import it.polimi.ingsw.riccardoemelissa.GameProxy;
import it.polimi.ingsw.riccardoemelissa.GameState;
import it.polimi.ingsw.riccardoemelissa.elements.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ListenerServer extends Thread {
    private static Client client_javafx;
    private Socket socket;
    private GameProxy game;

    public ListenerServer(Socket s, Client client) {
        socket=s;
        client_javafx=client;
    }

    public void run()
    {
        while (true)
        {
            try {
                ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
                client_javafx.from_server = (GameProxy) in.readObject();
                makeBoard();
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void makeBoard()
    {
        if(client_javafx.from_server.getPlayers().size()==0)
            return;
        for (Player p : client_javafx.from_server.getPlayers()) {
            if(p.GetNickname().compareTo("nome")==0) {
               System.out.println(p.GetNickname());
                return;
            }
        }

        if(client_javafx.from_server.getBoard()==null)
            client_javafx.initializeBoardgame(client_javafx.myboard);

        Platform.runLater(()-> {
            try {
                client_javafx.changeScene("board.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        //agg scorrere bord per settare worker gia presenti+ livello blocco

    }

    public GameProxy getGameProxy() {

        return game;
    }

    private static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {}

    }

    @FXML
    private void initializeBoardgame (GridPane myboard) {
        client_javafx.initializeBoardgame(client_javafx.myboard);
    }


}

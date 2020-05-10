package it.polimi.ingsw.riccardoemelissa.client;

import it.polimi.ingsw.riccardoemelissa.CommandType;
import it.polimi.ingsw.riccardoemelissa.GameProxy;
import it.polimi.ingsw.riccardoemelissa.GameState;
import it.polimi.ingsw.riccardoemelissa.elements.Player;
import it.polimi.ingsw.riccardoemelissa.elements.Worker;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
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
                client_javafx.messageToServer(CommandType.UPDATE);
            }
        }
    }

    private void makeBoard()
    {
        if(client_javafx.from_server.getPlayers().size()==0)
            return;

        for (Player p : client_javafx.from_server.getPlayers()) {
            System.out.println(p.GetNickname());
            if(p.GetNickname().compareTo("nome")==0) {
                return;
            }
        }

        if(client_javafx.loader.getLocation()!=getClass().getResource("board.fxml"))
        Platform.runLater(()-> {
            try {
                client_javafx.changeScene("board.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    Pane pane = new Pane();
                    client_javafx.myboard.getChildren().add(pane);
                    GridPane.setColumnIndex(pane, x);
                    GridPane.setRowIndex(pane, y);
                }
            }
        });

        Platform.runLater(()->
        {
            for(int i = 0; i< 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (!client_javafx.from_server.getBoard().GetStateBox(i, j)) {
                        System.out.println(client_javafx.from_server.getBoard().GetStateBox(i, j));


                        /*if (!client_javafx.from_server.getBoard().GetStateBox(i, j)) {
                            System.out.println(client_javafx.from_server.getBoard().GetStateBox(i, j));

                            Circle worker = new Circle(client_javafx.myboard.getHeight()/10, client_javafx.myboard.getWidth()/10, client_javafx.myboard.getHeight()/15,client_javafx.from_server.getBoard().GetOccupant(i,j).GetProprietary().GetColor());
                            //worker.setFill(client_javafx.from_server.getBoard().GetOccupant(i,j).GetProprietary().GetColor());
                            client_javafx.myboard.add(worker, j,i);
                        }
                         */

                        Circle worker = new Circle(client_javafx.myboard.getHeight()/10, client_javafx.myboard.getWidth()/10, client_javafx.myboard.getHeight()/15);

                        switch (client_javafx.from_server.getBoard().GetOccupant(i,j).GetProprietary().GetColor())
                        {
                            case "MAGENTA":
                                worker.setFill(Color.MAGENTA);
                                break;
                            case "AQUAMARINE":
                                worker.setFill(Color.AQUAMARINE);
                                break;
                            case "GOLD":
                                worker.setFill(Color.GOLD);
                                break;
                        }
                        client_javafx.myboard.add(worker, j,i);
                    }
                }
            }

        }
        );


        //agg scorrere bord per settare worker gia presenti+ livello blocco

    }

    public GameProxy getGameProxy() {

        return game;
    }

    private static void sleep(int time) {
        try
        {
            Thread.sleep(time);
        } catch (InterruptedException e) {}

    }


}

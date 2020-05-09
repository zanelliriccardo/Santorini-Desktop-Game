package it.polimi.ingsw.riccardoemelissa.client;

import it.polimi.ingsw.riccardoemelissa.CommandType;
import it.polimi.ingsw.riccardoemelissa.GameProxy;
import it.polimi.ingsw.riccardoemelissa.GameState;
import it.polimi.ingsw.riccardoemelissa.elements.Player;
import it.polimi.ingsw.riccardoemelissa.elements.Worker;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
            //Worker worker;

        for(int i = 0; i< 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!client_javafx.from_server.getBoard().GetStateBox(i, j)) {
                    System.out.println(client_javafx.from_server.getBoard().GetStateBox(i, j));

                    /*Node box = null;
                    ObservableList<Node> boxes = client_javafx.myboard.getChildren();

                    for(Node node : boxes)
                    {
                        if(client_javafx.myboard.getRowIndex(node) == i && client_javafx.myboard.getColumnIndex(node) == j)
                        {
                            box = node;
                            break;
                        }
                    }

                    Pane pane = (Pane) box;

                    Circle worker = new Circle(pane.getHeight()/2, pane.getWidth()/2, pane.getHeight()/3);
                    switch ("magenta")
                    {
                        case "magenta":
                            worker.setFill(Color.MAGENTA);
                            break;
                        case "aquamarine":
                            worker.setFill(Color.AQUAMARINE);
                            break;
                        case "gold":
                            worker.setFill(Color.GOLD);
                            break;
                    }
                    client_javafx.myboard.add(worker, j,i);

                     */

                    //Pane box = (Pane) client_javafx.myboard.getChildren();

                    Circle workerImage = new Circle(40.0f, 40.0f, 40.0f);
                    workerImage.setFill(Color.MAGENTA);
                    /*String color="magenta";

                    switch (color)
                    {
                        case "magenta":
                            workerImage.setFill(Color.MAGENTA);
                            break;

                        case "aquamarine":
                            workerImage.setFill(Color.AQUAMARINE);
                            break;

                        case "gold":
                            workerImage.setFill(Color.GOLD);
                            break;
                    }

                     */

                    client_javafx.myboard.add(workerImage,j,i);


                }
            }
        }
        });


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

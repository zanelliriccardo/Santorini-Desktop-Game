package it.polimi.ingsw.riccardoemelissa.client;

import it.polimi.ingsw.riccardoemelissa.CommandType;
import it.polimi.ingsw.riccardoemelissa.GameProxy;
import it.polimi.ingsw.riccardoemelissa.elements.Player;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class ListenerServer extends Thread {
    private static Client client_javafx;
    private Socket socket;
    private GameProxy game;
    private String godCard;

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
        AtomicInteger count = new AtomicInteger();

        if(client_javafx.from_server.getPlayers().size()==0)
            return;

        for (Player p : client_javafx.from_server.getPlayers()) {
            System.out.println(p.GetNickname());
            if(p.GetNickname().compareTo("nome")==0) {
                return;
            }
        }

        if(client_javafx.loader.getLocation()!=getClass().getResource("board2.fxml"))
        Platform.runLater(()-> {
            try {
                client_javafx.changeScene("board2.fxml");
                client_javafx.set_turn.setText("Turn of " + client_javafx.from_server.getActivePlayer().GetNickname());
                client_javafx.set_turn.setFont(Font.font(" Franklin Gothic Medium Cond", FontWeight.BOLD, 18));
                client_javafx.setServerMessage.setText("Hi " + client_javafx.from_server.getActivePlayer().GetNickname()+ "!" +"\nThe first thing you have to do \nis choose the initial position of your workers. \nPlace them in two free boxes.");
                client_javafx.setServerMessage.setFont(Font.font(" Franklin Gothic Medium Cond", 15));

                for (Player p: client_javafx.from_server.getPlayers())
                {
                    if(client_javafx.nickname.getText().compareTo(p.GetNickname()) == 0) {
                        godCard = "images/card/" + p.getGodImagePath();
                        client_javafx.set_godcard.setImage(new Image(String.valueOf(getClass().getResource(godCard))));
                    }

                    else if (client_javafx.from_server.getPlayers().size() == 2) {
                        client_javafx.opponent2.setVisible(false);
                        client_javafx.godOpponent2.setVisible(false);
                        client_javafx.opponent1.setText(p.GetNickname());
                        client_javafx.opponent1.setFont(Font.font(" Franklin Gothic Medium Cond"));
                        godCard = "images/card/" + p.getGodImagePath();
                        client_javafx.godOpponent1.setImage(new Image(String.valueOf(getClass().getResource(godCard))));
                    }

                    else if (client_javafx.from_server.getPlayers().size() == 3)
                    {
                        if(count.get() == 0)
                        {
                            client_javafx.opponent1.setText(p.GetNickname());
                            client_javafx.opponent1.setFont(Font.font(" Franklin Gothic Medium Cond"));
                            godCard = "images/card/" + p.getGodImagePath();
                            client_javafx.godOpponent1.setImage(new Image(String.valueOf(getClass().getResource(godCard))));
                            count.getAndIncrement();
                        }

                        else
                        {
                            client_javafx.opponent2.setText(p.GetNickname());
                            client_javafx.opponent2.setFont(Font.font(" Franklin Gothic Medium Cond"));
                            godCard = "images/card/" + p.getGodImagePath();
                            client_javafx.godOpponent2.setImage(new Image(String.valueOf(getClass().getResource(godCard))));
                        }

                    }


                }
                System.out.println("Immagine god : " + godCard);


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
                        for (Node child : client_javafx.myboard.getChildren()) {
                            Pane pane = (Pane) child;
                            Integer r = client_javafx.myboard.getRowIndex(child);
                            Integer c = client_javafx.myboard.getColumnIndex(child);
                            if(r!=null && r.intValue() == i && c != null && c.intValue() == j)
                                pane.getChildren().add(worker);
                        }
                    }
                    else {
                        for(Node node : client_javafx.myboard.getChildren()) {
                            if(node instanceof Circle && GridPane.getRowIndex(node) == i && GridPane.getColumnIndex(node) == j)
                            {
                                Circle worker = (Circle) node; // use what you want to remove
                                client_javafx.myboard.getChildren().remove(worker);
                                break;
                            }
                        }
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

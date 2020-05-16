package it.polimi.ingsw.riccardoemelissa.client;

import it.polimi.ingsw.riccardoemelissa.CommandType;
import it.polimi.ingsw.riccardoemelissa.GameProxy;
import it.polimi.ingsw.riccardoemelissa.elements.GodCardType;
import it.polimi.ingsw.riccardoemelissa.elements.Player;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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

    /**
     * wait for an update from server
     */
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

    /**
     * first platform.runlater set and show the board,
     * second manage it
     */
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

        if((client_javafx.loader.getLocation().getPath()).compareTo(getClass().getResource("board2.fxml").getPath())!=0)
            Platform.runLater(()-> {
            try {
                client_javafx.changeScene("board2.fxml");
                client_javafx.setServerMessage.setStyle("-fx-background-color: transparent");
                client_javafx.setServerMessage.setStyle("-fx-font-size : 15");
                client_javafx.setServerMessage.setStyle("-fx-font-family : Franklin Gothic Medium Cond");
                client_javafx.setServerMessage.setText("Hi " + client_javafx.from_server.getActivePlayer().GetNickname()+ "!" +"\nThe first thing you have to do \nis to choose the initial position \nof your workers. \nPlace them in two free boxes.");


                for (Player p: client_javafx.from_server.getPlayers())
                {
                    client_javafx.endTurn.setVisible(false);

                    if(p.GetGodCard().getIn_action().isPassive()&&p.GetNickname().compareTo(client_javafx.nickname.getText())==0)
                    {
                        client_javafx.button_setpower.setDisable(true);
                        client_javafx.button_setpower.setStyle("-fx-font-family : Franklin Gothic Medium Cond");
                        client_javafx.button_setpower.setText("ACTIVE");
                        client_javafx.set_power.setImage(new Image((String.valueOf(getClass().getResource("images/heropower_active.png")))));

                    }
                    if(client_javafx.nickname.getText().compareTo(p.GetNickname()) == 0) {
                        client_javafx.playerBoard.setStyle("-fx-background-color: #20B2AA");
                        client_javafx.playerBoard.setStyle("-fx-font-family : Franklin Gothic Medium Cond");
                        client_javafx.playerBoard.setText(p.GetNickname());
                        String color = p.GetColor();
                        client_javafx.colorPlayerBoard.setFill(Paint.valueOf(color));
                        godCard = "images/card/" + p.getGodImagePath();
                        client_javafx.set_godcard.setImage(new Image(String.valueOf(getClass().getResource(godCard))));
                    }

                    else if (client_javafx.from_server.getPlayers().size() == 2) {
                        client_javafx.opponent2.setVisible(false);
                        client_javafx.godOpponent2.setVisible(false);
                        client_javafx.colorOpponent2.setVisible(false);
                        client_javafx.opponent1.setStyle("-fx-background-color: #20B2AA");
                        client_javafx.opponent1.setStyle("-fx-font-family : Franklin Gothic Medium Cond");
                        client_javafx.opponent1.setText(p.GetNickname());
                        String color = p.GetColor();
                        client_javafx.colorOpponent1.setFill(Paint.valueOf(color));
                        godCard = "images/card/" + p.getGodImagePath();
                        client_javafx.godOpponent1.setImage(new Image(String.valueOf(getClass().getResource(godCard))));
                    }

                    else if (client_javafx.from_server.getPlayers().size() == 3)
                    {
                        if(count.get() == 0)
                        {
                            client_javafx.opponent1.setStyle("-fx-background-color: #20B2AA");
                            client_javafx.opponent1.setStyle("-fx-font-family : Franklin Gothic Medium Cond");
                            client_javafx.opponent1.setText(p.GetNickname());
                            String color = p.GetColor();
                            client_javafx.colorOpponent1.setFill(Paint.valueOf(color));
                            godCard = "images/card/" + p.getGodImagePath();
                            client_javafx.godOpponent1.setImage(new Image(String.valueOf(getClass().getResource(godCard))));
                            count.getAndIncrement();
                        }

                        else
                        {
                            client_javafx.opponent2.setFont(Font.font(" Franklin Gothic Medium Cond"));
                            client_javafx.opponent2.setStyle("-fx-background-color: #20B2AA");
                            client_javafx.opponent2.setText(p.GetNickname());
                            String color = p.GetColor();
                            client_javafx.colorOpponent2.setFill(Paint.valueOf(color));
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
                    Label label = new Label();
                    client_javafx.myboard.getChildren().add(pane);
                    label.setStyle("-fx-alignment : TOP_RIGHT");
                    label.setPrefSize(21,21);
                    label.setStyle("-fx-background-color: #90EE90");
                    label.setStyle("-fx-font-size : 15");
                    label.setStyle("-fx-font-family : Franklin Gothic Medium Cond");
                    pane.getChildren().add(label);
                    label.setText(String.valueOf(client_javafx.from_server.getBoard().GetLevelBox(x, y)));
                    GridPane.setColumnIndex(pane, x);
                    GridPane.setRowIndex(pane, y);
                }
            }
        });

        Platform.runLater(()->
        {
            client_javafx.endTurn.setVisible(client_javafx.from_server.getActivePlayer().GetGodCard().getCardType().isEndTurn()&&client_javafx.from_server.getBoard().getActivePlayer().GetNickname().compareTo(client_javafx.nickname.getText())==0);

            client_javafx.set_turn.setText("Turn of " + client_javafx.from_server.getActivePlayer().GetNickname());
            client_javafx.set_turn.setFont(Font.font(" Franklin Gothic Medium Cond", FontWeight.BOLD, 18));

            for(int i = 0; i< 5; i++) {
                for (int j = 0; j < 5; j++)
                {
                    if (!client_javafx.from_server.getBoard().GetStateBox(i, j)) {
                        System.out.println(client_javafx.from_server.getBoard().GetStateBox(i, j));
                        Circle worker = new Circle(client_javafx.myboard.getHeight()/10, client_javafx.myboard.getWidth()/10, client_javafx.myboard.getHeight()/15);

                        switch (client_javafx.from_server.getBoard().GetOccupantProprietary(i,j).GetColor())
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

                        for (Node gridPaneChild : client_javafx.myboard.getChildren())
                        {
                            Pane pane = (Pane) gridPaneChild;
                            //Integer r = client_javafx.myboard.getRowIndex(gridPaneChild);
                            //Integer c = client_javafx.myboard.getColumnIndex(gridPaneChild);
                            if(GridPane.getRowIndex(pane) == i && GridPane.getColumnIndex(pane) == j)
                            {
                                if(pane.getChildren().size()>2) {
                                    pane.getChildren().removeIf(paneChild -> paneChild instanceof Circle);
                                }
                                pane.getChildren().add(worker);
                            }

                        }
                    }
                    else {
                        for(Node gridPaneChild : client_javafx.myboard.getChildren()) {
                            Pane pane = (Pane) gridPaneChild;
                            if(GridPane.getRowIndex(pane) == i && GridPane.getColumnIndex(pane) == j)
                            {
                                pane.getChildren().removeIf(paneChild -> paneChild instanceof Circle);
                            }
                        }
                    }

                    for (Node child : client_javafx.myboard.getChildren()) {
                        Integer r = GridPane.getRowIndex(child);
                        Integer c = GridPane.getColumnIndex(child);
                        if (r != null && r == i && c != null && c == j)
                        {
                            for (Node label : ((Pane) child).getChildren())
                                if(label instanceof Label)
                                    ((Label) label).setText(String.valueOf(client_javafx.from_server.getBoard().GetLevelBox(r, c)));
                        }
                    }
                }
            }

            if(client_javafx.from_server.getActivePlayer().GetNickname().compareTo(client_javafx.nickname.getText())==0)
            {
                if (client_javafx.from_server.getActivePlayer().GetGodCard().getCardType() == GodCardType.WIN)
                    client_javafx.messageToServer(CommandType.WIN,client_javafx.nickname.getText());
                if (client_javafx.from_server.getActivePlayer().GetGodCard().getCardType() == GodCardType.LOSE)
                    client_javafx.messageToServer(CommandType.LOSE,client_javafx.nickname.getText());
            }

            if(client_javafx.from_server.getActive_worker()!=null&&client_javafx.from_server.getActivePlayer().GetNickname().compareTo(client_javafx.nickname.getText())==0)
            {
                client_javafx.activeWorker = client_javafx.from_server.getBoard().GetOccupant(client_javafx.from_server.getActive_worker().GetPosition());

                if (client_javafx.activeWorker.GetProprietary().GetGodCard().getCardType().isBuild()&&client_javafx.from_server.getActive_worker().GetProprietary().GetNickname().compareTo(client_javafx.nickname.getText())==0)
                    client_javafx.activeBuildCells();
                else if (client_javafx.activeWorker.GetProprietary().GetGodCard().getCardType().isMove()&&client_javafx.from_server.getActive_worker().GetProprietary().GetNickname().compareTo(client_javafx.nickname.getText())==0)
                    client_javafx.activeMoveCells();
            }
            else
            {
                client_javafx.cleanBoard();
                client_javafx.activeWorker=null;
            }

            if(client_javafx.from_server.getActivePlayer().GetNickname().compareTo(client_javafx.nickname.getText())==0)
                client_javafx.setDisable(false);
            else
                client_javafx.setDisable(true);

            if(client_javafx.from_server.getBoard().getGameover())
            {
                try {
                    if (client_javafx.from_server.getActivePlayer().GetNickname().compareTo(client_javafx.nickname.getText()) == 0) {
                        client_javafx.changeScene("winner.fxml");
                    }
                    else {
                        client_javafx.changeScene("loser.fxml");
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


        );
    }
}

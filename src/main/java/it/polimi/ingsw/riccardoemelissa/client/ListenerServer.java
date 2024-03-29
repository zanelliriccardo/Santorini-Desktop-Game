package it.polimi.ingsw.riccardoemelissa.client;

import it.polimi.ingsw.riccardoemelissa.CommandType;
import it.polimi.ingsw.riccardoemelissa.GameProxy;
import it.polimi.ingsw.riccardoemelissa.elements.Player;
import it.polimi.ingsw.riccardoemelissa.elements.PowerType;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

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
     * Wait for an update from server
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
     * First platform.runlater set and show the board,
     * second manage it
     */
    private void makeBoard()
    {
        AtomicInteger count = new AtomicInteger();

        if(client_javafx.from_server.getPlayers().size()==0)
            return;

        for (Player p : client_javafx.from_server.getPlayers()) {
            if(p.getNickname().compareTo("nome")==0) {
                return;
            }
        }

        if((client_javafx.loader.getLocation().getPath()).compareTo(getClass().getResource("board2.fxml").getPath())!=0)
            Platform.runLater(()-> {
            try {
                client_javafx.changeScene("board2.fxml");

                for (Player p: client_javafx.from_server.getPlayers())
                {
                    client_javafx.endTurn.setVisible(false); // endturn not visible when the players are setting the initial position of their workers

                    //if the godcard's power of the active player is passive, the button used to active the power is disable and the power is always active
                    if(p.getGodCard().getIn_action().isPassive()&&p.getNickname().compareTo(client_javafx.nickname.getText())==0)
                    {
                        client_javafx.button_setpower.setDisable(true);
                        client_javafx.button_setpower.setText("ACTIVE");
                        client_javafx.set_power.setImage(new Image((String.valueOf(getClass().getResource("images/heropower_active.png")))));

                    }

                    //if is the active player, this part of the method sets his name, his worker color and his godcard
                    if(client_javafx.nickname.getText().compareTo(p.getNickname()) == 0) {
                        client_javafx.playerBoard.setText(p.getNickname());
                        String color = p.getColor();
                        client_javafx.colorPlayerBoard.setFill(Paint.valueOf(color));
                        godCard = "images/card/" + p.getGodImagePath();
                        client_javafx.set_godcard.setImage(new Image(String.valueOf(getClass().getResource(godCard))));
                    }

                    //if the number of player is two, this part of the method sets the name, the worker color and the godcard of the opponent
                    else if (client_javafx.from_server.getPlayers().size() == 2) {
                        client_javafx.opponent2.setVisible(false);
                        client_javafx.godOpponent2.setVisible(false);
                        client_javafx.colorOpponent2.setVisible(false);
                        client_javafx.opponent1.setText(p.getNickname());
                        String color = p.getColor();
                        client_javafx.colorOpponent1.setFill(Paint.valueOf(color));
                        godCard = "images/card/" + p.getGodImagePath();
                        client_javafx.godOpponent1.setImage(new Image(String.valueOf(getClass().getResource(godCard))));
                    }

                    //if the number of player is three, this part of the method sets the name, the worker color and the godcard of the opponents
                    else if (client_javafx.from_server.getPlayers().size() == 3)
                    {
                        if(count.get() == 0)
                        {
                            client_javafx.opponent1.setText(p.getNickname());
                            String color = p.getColor();
                            client_javafx.colorOpponent1.setFill(Paint.valueOf(color));
                            godCard = "images/card/" + p.getGodImagePath();
                            client_javafx.godOpponent1.setImage(new Image(String.valueOf(getClass().getResource(godCard))));
                            count.getAndIncrement();
                        }

                        else
                        {
                            client_javafx.opponent2.setText(p.getNickname());
                            String color = p.getColor();
                            client_javafx.colorOpponent2.setFill(Paint.valueOf(color));
                            godCard = "images/card/" + p.getGodImagePath();
                            client_javafx.godOpponent2.setImage(new Image(String.valueOf(getClass().getResource(godCard))));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //initialize the gridpane with a pane in every cell and a label (for the level) in every pane
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    Pane pane = new Pane();
                    Label label = new Label();
                    client_javafx.myboard.getChildren().add(pane);
                    label.setPrefSize(21,21);
                    label.setStyle("-fx-font-size : 15");
                    pane.getChildren().add(label);
                    label.setText(String.valueOf(" " + client_javafx.from_server.getBoard().getLevelBox(x, y)));
                    GridPane.setColumnIndex(pane, x);
                    GridPane.setRowIndex(pane, y);
                }
            }
        });

        Platform.runLater(()->
        {
            //button endturn enable only at the end of the turn
            client_javafx.endTurn.setVisible(client_javafx.from_server.getActivePlayer().getGodCard().getCardType().isEndTurn()&&client_javafx.isMyTurn());

            //Turn of
            client_javafx.set_turn.setText("Turn of " + client_javafx.from_server.getActivePlayer().getNickname());

            for(int i = 0; i< 5; i++) {
                for (int j = 0; j < 5; j++)
                {
                    if (!client_javafx.from_server.getBoard().getStateBox(i, j)) {
                        System.out.println(client_javafx.from_server.getBoard().getStateBox(i, j));
                        Circle worker = new Circle(client_javafx.myboard.getHeight()/10, client_javafx.myboard.getWidth()/10, client_javafx.myboard.getHeight()/15);

                        switch (client_javafx.from_server.getBoard().getOccupantProprietary(i,j).getColor())
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

                        //add a worker to a cell
                        for (Node gridPaneChild : client_javafx.myboard.getChildren())
                        {
                            Pane pane = (Pane) gridPaneChild;
                            if(GridPane.getRowIndex(pane) == i && GridPane.getColumnIndex(pane) == j)
                            {
                                if(pane.getChildren().size()>2) {
                                    pane.getChildren().removeIf(paneChild -> paneChild instanceof Circle);
                                }
                                pane.getChildren().add(worker);
                            }

                        }
                    }
                    //remove  aworker from a cell
                    else {
                        for(Node gridPaneChild : client_javafx.myboard.getChildren()) {
                            Pane pane = (Pane) gridPaneChild;
                            if(GridPane.getRowIndex(pane) == i && GridPane.getColumnIndex(pane) == j)
                            {
                                pane.getChildren().removeIf(paneChild -> paneChild instanceof Circle);
                            }
                        }
                    }

                    //change the text of a label
                    for (Node child : client_javafx.myboard.getChildren()) {
                        Integer r = GridPane.getRowIndex(child);
                        Integer c = GridPane.getColumnIndex(child);
                        if (r != null && r == i && c != null && c == j)
                        {
                            for (Node label : ((Pane) child).getChildren())
                                if(label instanceof Label)
                                    ((Label) label).setText(" "+String.valueOf(client_javafx.from_server.getBoard().getLevelBox(r, c)));
                        }
                    }
                }
            }

            if(client_javafx.from_server.getActive_worker()==null&&client_javafx.isMyTurn()&&!client_javafx.from_server.getActivePlayer().getGodCard().getIn_action().isPassive())
            {
                client_javafx.button_setpower.setDisable(false);
                client_javafx.button_setpower.setSelected(false);
                client_javafx.button_setpower.setText("INACTIVE");
                client_javafx.set_power.setImage(new Image(String.valueOf((getClass().getResource("images/heropower_inactive.png")))));
                for (Player p :
                        client_javafx.from_server.getPlayers()) {
                    if (p.getNickname().compareTo(client_javafx.nickname.getText()) == 0)
                        p.getGodCard().setIn_action(PowerType.DISABLE);
                }
                client_javafx.activedPower(PowerType.DISABLE);
            }

            client_javafx.checkGameOver();
            client_javafx.updatePossibleCell();
            client_javafx.myboard.setDisable(!client_javafx.isMyTurn());
        }
        );
    }

}

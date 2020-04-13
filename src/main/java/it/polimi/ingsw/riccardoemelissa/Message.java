package it.polimi.ingsw.riccardoemelissa;

import java.io.PrintWriter;

public class Message {
    public void Waiting(PrintWriter out) {
        out.println("Waiting for your turn \n");
        out.flush();
    }

    // metodo : chiedere se vuole spostarsi ancora --> ARTEMIS

    //metodo : comunicare se una box in cui è consentito spostarsi è già occupata --> APOLLO


}

package it.polimi.ingsw.riccardoemelissa;

import java.io.PrintWriter;

public class Message {
    public void Waiting(PrintWriter out) {
        out.println("Waiting for your turn \n");
        out.flush();
    }

    public void InitialPositionFirstW (PrintWriter out) {
        out.println("where do you want to put your first worker? \n");
        out.flush();
    }

    public void PositioningX (PrintWriter out) {
        out.println("Position X: ");
        out.flush();
    }

    public void Verification(PrintWriter out) {
        out.println("Position must be between 1 and 5\n");
        out.flush();
    }

    public void PositioningY(PrintWriter out) {
        out.println("Position Y: ");
        out.flush();
    }

    public void InitialPositionSecondW(PrintWriter out) {
        out.println("where do you want to put your second worker? \n");
        out.flush();
    }

    public void YourTurn(PrintWriter out) {
        out.println("Your turn! \n");
        out.flush();
    }

    public void WhichWorker(PrintWriter out) {
        out.println("Which worker do you want to move? (1 or 2) \n");
        out.flush();
    }

    public void WhereMove(PrintWriter out) {
        out.println("Where do you want to move your worker? \n");
        out.flush();
    }

    public void Winner(PrintWriter out) {
        out.println("Congratulations! You Win! \n");
        out.flush();
    }

    public void Success(PrintWriter out) {
        out.println("Action has been completed Successfully \n");
        out.flush();
    }

    public void Failure(PrintWriter out) {
        out.println("Action failed! \n");
        out.flush();
    }

    public void WhereBuild(PrintWriter out) {
        out.println("Where do you want to build? \n");
        out.flush();
    }

    public void EnterNickname(PrintWriter out) {
        out.println("Enter your nickname:\n");
        out.flush();
    }

    public void NicknameNotAvailable(PrintWriter out) {
        out.println("Nickname already existing, please retry");
        out.flush();
    }

    // metodo : chiedere se vuole spostarsi ancora --> ARTEMIS

    //metodo : comunicare se una box in cui è consentito spostarsi è già occupata --> APOLLO


}

package it.polimi.ingsw.riccardoemelissa;

import elements.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler implements Runnable {
    private Socket socket;
    private String nickname;
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            nickname=ChooseNickname(in,out);

            if(nickname==App.g.GetActivePlayer()) {

            }
            else {
                try {
                    wait();
                }
                catch (InterruptedException i){}
            }

            in.close();
            out.close();
            socket.close();
        }
        catch (IOException e) {System.err.println(e.getMessage());}
    }

    public int[] getInputMovement()
    {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
        }
        catch (IOException e) { System.err.println(e.getMessage()); }
        return new int[0];
    }

    public String ChooseNickname(Scanner in, PrintWriter out)
    {
        String str;
        while(true) {
            out.println("Enter your nickname:\n");
            out.flush();
            str = in.nextLine();
            if(App.g.CheckNickname(str))
                break;

            out.println("Nickname already existing, please retry");
            out.flush();
        }
        App.g.MemorizeNickname(str);

        return str;
    }

}

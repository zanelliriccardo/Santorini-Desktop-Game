package it.polimi.ingsw.riccardoemelissa;

import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Message {
    private Scanner in;
    private PrintWriter out;

    public Message(Socket server) throws IOException {
            in = new Scanner(server.getInputStream());
            out = new PrintWriter(server.getOutputStream());
    }

    public int WhichWorker() {

        String str=in.nextLine();
        try {
            int n = Integer.parseInt(str);
            if (n != 1 && n != 2)
                throw new Exception();
            return n;

        } catch (Exception ex) {
            out.println("Input error, choose 1 or 2 \n");
            out.flush();
            return 0;
        }
    }

    public int[] GetInputPosition()
    {
        int[] pos=new int[2];
        while (true) {
            PositioningX(out);
            try {
                pos[0] = Integer.parseInt(in.nextLine());
            } catch (Exception e) {
                Verification(out);
                continue;
            }
            break;
        }

        while (true) {
            PositioningY(out);
            try {
                pos[1] = Integer.parseInt(in.nextLine());
            } catch (Exception e) {
                Verification(out);
                continue;
            }
            break;
        }
        return pos;
    }

    public boolean SecondMove()
    {
        out.println("Another move?(Yes or No)");
        out.flush();

        while (true) {
            String str = in.nextLine();
            if (str.equalsIgnoreCase("yes"))
            {
                return true;
            } else if (str.equalsIgnoreCase("no"))
            {
                return false;
            }
            else
                continue;
        }
    }

    public boolean UsePower() {
        out.println("Do you want to use Prometheus's power? (Yes or No) ");
        out.flush();

        while (true) {
            String str = in.nextLine();
            if (str.equalsIgnoreCase("yes"))
            {
                return true;
            } else if (str.equalsIgnoreCase("no"))
            {
                return false;
            }
            else
                continue;
        }

    }

    public void OutMessage(String message)
    {
        out.println(message);
        out.flush();
    }

    public boolean YesOrNoMessage()
    {
        while (true) {
            String str = in.nextLine();
            if (str.equalsIgnoreCase("yes"))
            {
                return true;
            } else if (str.equalsIgnoreCase("no"))
            {
                return false;
            }
            else
                continue;
        }
    }

    public String inputString()
    {
        String str;
        while (true)
        {
            str=null;
            str = in.nextLine();

            if(str!=null)
                break;
        }
        return str;
    }



    // metodo : chiedere se vuole spostarsi ancora --> ARTEMIS

    //metodo : comunicare se una box in cui è consentito spostarsi è già occupata --> APOLLO


}

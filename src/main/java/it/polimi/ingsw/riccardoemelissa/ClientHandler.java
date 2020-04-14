package it.polimi.ingsw.riccardoemelissa;

import elements.Player;
import elements.Worker;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket socket;
    private String nickname;
    private Message m = new Message();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String str;

            nickname=ChooseNickname(in,out);
            m.Waiting(out);

            while (true)
            {
                try {
                    wait(500);
                }
                catch (InterruptedException i) {
                    if (nickname.compareTo(App.g.GetActivePlayer())==0) {
                        {
                            int[] pos1=new int[2];
                            int[] pos2=new int[2];

                            m.InitialPositionFirstW(out);

                            while (true) {
                                m.PositioningX(out);
                                try {
                                    pos1[0] = Integer.parseInt(in.nextLine());
                                } catch (Exception e) {
                                    m.Verification(out);
                                    continue;
                                }
                                break;
                            }

                            while (true) {
                                m.PositioningY(out);
                                try {
                                    pos1[1] = Integer.parseInt(in.nextLine());
                                } catch (Exception e) {
                                    m.Verification(out);
                                    continue;
                                }
                                break;
                            }

                            m.InitialPositionSecondW(out);

                            while (true) {
                                m.PositioningX(out);
                                try {
                                    pos2[0] = Integer.parseInt(in.nextLine());
                                } catch (Exception e) {
                                    m.Verification(out);
                                    continue;
                                }
                                break;
                            }

                            while (true) {
                                m.PositioningY(out);
                                    try {
                                        pos2[1] = Integer.parseInt(in.nextLine());
                                    } catch (Exception e) {
                                        m.Verification(out);
                                        continue;
                                    }
                                    break;
                                }
                            App.g.SetInitialWorkerPosition(App.g.GetIndexPlayer(nickname), pos1, pos2);
                            break;
                        }
                    }
                }
            }

            App.g.NextTurn();

            m.Waiting(out);
            if(App.g.CheckPower("move"))
            {

            }

            while(true) {
                try
                {
                    wait(500);
                }
                catch (Exception e) {
                    if (nickname.compareTo(App.g.GetActivePlayer())==0) {
                        Worker activeWorker = null;
                        int[] pos=new int[2];

                        if(!App.g.HaveAPossibleMove(nickname))//se non mossa possibile --> SCONFITTA
                        {
                            App.g.Lose(nickname);
                            App.g.NextTurn();
                            break;
                        }

                        App.g.GetBoard();
                        m.YourTurn(out);

                        while (true) {
                            m.WhichWorker(out);
                            str = in.nextLine();
                            try {
                                int n = Integer.parseInt(str);
                                if (n != 1 && n != 2)
                                    throw new Exception();
                                activeWorker = App.g.GetWorkerToMove(nickname, n-1);
                            } catch (Exception ex) {
                                out.println("Input error, choose 1 or 2 \n");
                                out.flush();
                                break;
                            }
                        }

                        while (true) {
                            m.WhereMove(out);

                            while (true) {
                                m.PositioningX(out);
                                try {
                                    pos[0] = Integer.parseInt(in.nextLine());
                                } catch (Exception ex) {
                                    m.Verification(out);
                                    continue;
                                }
                                break;
                            }

                            while (true) {
                                m.PositioningY(out);
                                try {
                                    pos[1] = Integer.parseInt(in.nextLine());
                                } catch (Exception ex) {
                                    m.Verification(out);
                                    continue;
                                }
                                break;
                            }

                            int[][] possiblemove=App.g.PossibleMoves(pos,activeWorker.GetPosition());//intervallo boardgame da controllare

                            if(App.g.DoMove(pos,activeWorker))
                            {
                                m.Success(out);
                                break;
                            }
                            m.Failure(out);
                        }

                        if(App.g.Win(activeWorker.GetPosition()))
                        {
                            App.g.AddWinner(activeWorker.GetProprietary());
                            m.Winner(out);
                            App.g.NextTurn();
                            break;
                        }
                        //build

                        if(App.g.CheckPower("build"))
                        {

                        }

                        while (true) {
                            m.WhereBuild(out);
                            while (true) {
                                m.PositioningX(out);
                                try {
                                    pos[0] = Integer.parseInt(in.nextLine());
                                } catch (Exception ex) {
                                    m.Verification(out);
                                    continue;
                                }
                                break;
                            }

                            while (true) {
                                m.PositioningY(out);
                                try {
                                    pos[1] = Integer.parseInt(in.nextLine());
                                } catch (Exception ex) {
                                    m.Verification(out);
                                    continue;
                                }
                                break;
                            }

                            if(App.g.DoBuild(pos,activeWorker))
                            {
                                m.Success(out);
                                break;
                            }
                            m.Failure(out);
                        }

                        //end turn da fare in grafica
                        App.g.NextTurn();
                    }
                }

                in.close();
                out.close();
                socket.close();
            }
        }
        catch (IOException e) {System.err.println(e.getMessage());}
    }

    public String ChooseNickname(Scanner in, PrintWriter out)
    {
        String str;
        while(true) {
            m.EnterNickname(out);
            str = in.nextLine();
            if(App.g.CheckNickname(str))
                break;
            m.NicknameNotAvailable(out);
        }
        App.g.MemorizeNickname(str);
        return str;
    }

}

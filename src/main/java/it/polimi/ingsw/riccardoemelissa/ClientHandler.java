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
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String str;

            nickname=ChooseNickname(in,out);

            out.println("Waiting for your turn \n");
            out.flush();

            while (true)
            {
                try {
                    wait(500);
                }
                catch (InterruptedException i) {
                    if (nickname == App.g.GetActivePlayer()) {
                        {
                            int[] pos1=new int[2];
                            int[] pos2=new int[2];

                            out.println("where do you want to put your first worker? \n");
                            out.flush();

                            while (true) {
                                out.println("Position X: ");
                                out.flush();
                                try {
                                    pos1[0] = Integer.parseInt(in.nextLine());
                                } catch (Exception e) {
                                    out.println("Position must be between 1 and 5\n");
                                    out.flush();
                                    continue;
                                }
                                break;
                            }

                            while (true) {
                                out.println("Position Y: ");
                                out.flush();
                                try {
                                    pos1[1] = Integer.parseInt(in.nextLine());
                                } catch (Exception e) {
                                    out.println("Position must be between 1 and 5\n");
                                    out.flush();
                                    continue;
                                }
                                break;
                            }

                            out.println("where do you want to put your second worker? \n");
                            out.flush();

                            while (true) {
                                out.println("Position X: ");
                                out.flush();
                                try {
                                    pos2[0] = Integer.parseInt(in.nextLine());
                                } catch (Exception e) {
                                    out.println("Position must be between 1 and 5\n");
                                    out.flush();
                                    continue;
                                }
                                break;
                            }

                            while (true) {
                                    out.println("Position Y: ");
                                    out.flush();
                                    try {
                                        pos2[1] = Integer.parseInt(in.nextLine());
                                    } catch (Exception e) {
                                        out.println("Position must be between 1 and 5\n");
                                        out.flush();
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
            out.println("Waiting for your turn \n");
            out.flush();

            while(true) {
                try
                {
                    wait(500);
                }
                catch (Exception e) {
                    if (nickname == App.g.GetActivePlayer()) {
                        Worker activeWorker = null;
                        int[] pos=new int[2];

                        App.g.HaveAPossibleMove(nickname);
                        
                        out.println("Your turn! \n");
                        out.flush();
                        App.g.GetBoard();
                        while (true) {
                            out.println("Which worker do you want to move? (1 or 2) \n");
                            out.flush();
                            str = in.nextLine();
                            try {
                                int n = Integer.parseInt(str);
                                if (n != 1 && n != 2)
                                    throw new Exception();
                                activeWorker = App.g.GetWorkerToMove(nickname, n);
                            } catch (Exception ex) {
                                out.println("Input error, choose 1 or 2 \n");
                                out.flush();
                                break;
                            }
                        }

                        while (true) {
                            out.println("Where do you want to move your worker? \n");
                            out.flush();

                            while (true) {
                                out.println("Position X: ");
                                out.flush();
                                try {
                                    pos[0] = Integer.parseInt(in.nextLine());
                                } catch (Exception ex) {
                                    out.println("Position must be between 1 and 5\n");
                                    out.flush();
                                    continue;
                                }
                                break;
                            }

                            while (true) {
                                out.println("Position Y: ");
                                out.flush();
                                try {
                                    pos[1] = Integer.parseInt(in.nextLine());
                                } catch (Exception ex) {
                                    out.println("Position must be between 1 and 5\n");
                                    out.flush();
                                    continue;
                                }
                                break;
                            }

                            if(App.g.DoMove(pos,activeWorker))
                            {
                                if(App.g.Win(activeWorker.GetPosition()))
                                {
                                    App.g.AddWinner(activeWorker.GetProprietary());
                                    out.println("Congratulation! You Win! \n");
                                    out.flush();
                                    break;
                                }
                                out.println("Move successful \n");
                                out.flush();
                                break;
                            }
                            out.println("Move not allowed \n");
                            out.flush();
                        }

                        //build

                        while (true) {
                            out.println("Where do you want to build? \n");
                            out.flush();

                            while (true) {
                                out.println("Position X: ");
                                out.flush();
                                try {
                                    pos[0] = Integer.parseInt(in.nextLine());
                                } catch (Exception ex) {
                                    out.println("Position must be between 1 and 5\n");
                                    out.flush();
                                    continue;
                                }
                                break;
                            }

                            while (true) {
                                out.println("Position Y: ");
                                out.flush();
                                try {
                                    pos[1] = Integer.parseInt(in.nextLine());
                                } catch (Exception ex) {
                                    out.println("Position must be between 1 and 5\n");
                                    out.flush();
                                    continue;
                                }
                                break;
                            }

                            if(App.g.DoBuild(pos,activeWorker))
                            {
                                out.println("Build successful\n");
                                out.flush();
                                break;
                            }
                            out.println("Build not allowed! You can only build in adjacent boxes and at the same level as your worker. \n");
                            out.flush();
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

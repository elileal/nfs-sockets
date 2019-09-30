package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private static String HOME = "/home/eliabe/IFPB/P6/DE/unidade2/nfs-sockets/labNFS/";

    public static void main(String[] args) throws IOException {
        Parse parse = new Parse(HOME);

        System.out.println("== Servidor ==");

        // Configurando o socket
        ServerSocket serverSocket = new ServerSocket(7001);
        Socket socket = serverSocket.accept();

        // pegando uma referência do canal de saída do socket. Ao escrever nesse canal, está se enviando dados para o
        // servidor
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // pegando uma referência do canal de entrada do socket. Ao ler deste canal, está se recebendo os dados
        // enviados pelo servidor
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // laço infinito do servidor
        while (true) {
            System.out.println("Cliente: " + socket.getInetAddress());

            String mensagem = dis.readUTF();
            System.out.println(mensagem);
            String[] cmd = mensagem.split(" ");
            if (cmd.length == 2 || cmd.length == 3) {
                switch (cmd[0]){
                    case "readdir":
                        dos.writeUTF(parse.readdir(cmd[1]));
                        break;
                    case "touch":
                        dos.writeUTF(parse.createFile(cmd[1]));

                        break;
                    case "mkdir":
                        dos.writeUTF(parse.createDir(cmd[1]));
                        break;
                    case "rename":
                        dos.writeUTF(parse.renameFile(cmd[1], cmd[2]));
                        break;
                    case "delete":
                        dos.writeUTF(parse.deleteFile(cmd[1]));
                        break;
                    case "remove":
                        dos.writeUTF(parse.deleteDir(cmd[1]));
                        break;
                    default:
                        dos.writeUTF("Comando inválido: "+cmd[0]);
                }
            } else {
                dos.writeUTF("==HELP==\n\n" +
                        "Uso: comando [DIRETÓRIO ou ARQUIVO.txt]\n\n" +
                        "Comandos:\n\n" +
                        "readdir [DIR]: ler o diretório atual\n" +
                        "touch [FILE]: cria um arquivo\n" +
                        "mkdir [PASTA]: cria uma pasta\n" +
                        "rename [ATUAL NOME] [NOVO NOME]: renomeia um arquivo\n" +
                        "delete [FILE]: deleta um arquivo\n" +
                        "remove [PASTA]: deleta uma pasta (vazia)\n");
            }



        }
        /*
         * Observe o while acima. Perceba que primeiro se lê a mensagem vinda do cliente (linha 29, depois se escreve
         * (linha 32) no canal de saída do socket. Isso ocorre da forma inversa do que ocorre no while do Cliente2,
         * pois, de outra forma, daria deadlock (se ambos quiserem ler da entrada ao mesmo tempo, por exemplo,
         * ninguém evoluiria, já que todos estariam aguardando.
         */
    }

}

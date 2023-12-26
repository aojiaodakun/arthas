package org.apache.commons.net.telnet;

import com.taobao.arthas.client.IOUtil;
import jline.Terminal;
import jline.TerminalSupport;
import jline.UnixTerminal;
import jline.console.ConsoleReader;
import jline.console.KeyMap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStreamWriter;

public class TelnetClientTest {

    private static final byte CTRL_C = 0x03;

    public static void main(String[] args) throws Exception{

        final TelnetClient telnet = new TelnetClient();
        telnet.setConnectTimeout(5000);
//        int width = TerminalSupport.DEFAULT_WIDTH;
//        int height = TerminalSupport.DEFAULT_HEIGHT;
//        // send init terminal size
//        TelnetOptionHandler sizeOpt = new WindowSizeOptionHandler(width, height, true, true, false, false);
//        try {
//            telnet.addOptionHandler(sizeOpt);
//        } catch (InvalidTelnetOptionException e) {
//            // ignore
//        }
//        final ConsoleReader consoleReader = new ConsoleReader(System.in, System.out);
//        consoleReader.setHandleUserInterrupt(true);
//        Terminal terminal = consoleReader.getTerminal();
//
//        // support catch ctrl+c event
//        terminal.disableInterruptCharacter();
//        if (terminal instanceof UnixTerminal) {
//            ((UnixTerminal) terminal).disableLitteralNextCharacter();
//        }
//        // ctrl + c event callback
//        consoleReader.getKeys().bind(Character.toString((char) CTRL_C), new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    consoleReader.getCursorBuffer().clear(); // clear current line
//                    telnet.getOutputStream().write(CTRL_C);
//                    telnet.getOutputStream().flush();
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//            }
//
//        });
//        // ctrl + d event call back
//        consoleReader.getKeys().bind(Character.toString(KeyMap.CTRL_D), null);


        telnet.connect("localhost", 3658);
        IOUtil.readWrite(telnet.getInputStream(), telnet.getOutputStream(), System.in, new OutputStreamWriter(System.out));
//        telnet._closeOutputStream();
    }

}

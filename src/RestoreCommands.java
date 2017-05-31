import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class RestoreCommands {
    private File cmdsFile;
    //private DatabaseInterface receiver;
    private Scanner scanner;

    public class CommandInfo {
        public String cmd;
        public String key;
        public Object value;
    }

    public RestoreCommands(File file) {
        cmdsFile = file;
        //this.receiver = receiver;
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(cmdsFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner = new Scanner(fileReader);
    }

    public CommandInfo nextCommand() {
        if (scanner.hasNext()) {
            CommandInfo cmdInfo = new CommandInfo();
            JSONParser parser = new JSONParser();
            JSONObject jsonCmd = null;
            try {
                jsonCmd = (JSONObject) parser.parse(scanner.nextLine());
                cmdInfo.cmd = jsonCmd.get(SaveCommands.CMD).toString();
                cmdInfo.key = jsonCmd.get(SaveCommands.KEY).toString();

                String type = null;
                String strValue = null;
                Object value = null;

                Object typeObj = jsonCmd.get(SaveCommands.TYP);
                if (typeObj != null) {
                    type = jsonCmd.get(SaveCommands.TYP).toString();
                    strValue = jsonCmd.get(SaveCommands.VAL).toString();
                    if (type.equals("class java.lang.Integer")) {
                        value = new Integer(strValue);
                    } else if (type.equals("class java.lang.String")) {
                        value = new String(strValue);
                    } else if (type.equals("class java.lang.Double")) {
                        value = new Double(strValue);
                    } else if (type.equals("class CustomArray")) {
                        value = CustomArray.fromString(strValue);
                    } else if (type.equals("class CustomObject")) {
                        value = CustomObject.fromString(strValue);
                    }
                }
                cmdInfo.value = value;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
            return cmdInfo;
        }
        return null;
    }
}

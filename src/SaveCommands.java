import org.json.simple.JSONObject;

import java.io.*;


public class SaveCommands {

    private File cmdsFile;
    private DatabaseInterface receiver;

    public static final String ADD_CMD = "ADD";
    public static final String DEL_CMD = "DEL";

    public static final String CMD = "CMD";
    public static final String KEY = "KEY";
    public static final String TYP = "TYP";
    public static final String VAL = "VAL";

    public SaveCommands(File file, DatabaseInterface receiver) {
        cmdsFile = file;
        this.receiver = receiver;
    }

    public void writeCommand(String cmd, String key, Object value){
        JSONObject jsonCmd = new JSONObject();
        jsonCmd.put(CMD, cmd);
        jsonCmd.put(KEY, key);
        if (value != null) {
            jsonCmd.put(TYP, value.getClass().toString());
            jsonCmd.put(VAL, value.toString());
        }
        try {
            FileWriter file = new FileWriter(cmdsFile, true);
            BufferedWriter bw = new BufferedWriter(file);
            bw.write(jsonCmd.toJSONString());
            bw.newLine();
            bw.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

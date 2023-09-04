import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.HashMap;
public class Parser extends Terminal{
    private HashMap<String,Integer> Commands_List = new HashMap<>();
    Parser(){
        Commands_List.put("help" ,1);
        Commands_List.put("exit" ,2);
        Commands_List.put("clear",3);
        Commands_List.put("pwd"  ,4);
        Commands_List.put("date" ,5);
        Commands_List.put("ls"   ,100);
        Commands_List.put("cd"   ,101);
        Commands_List.put("cp"   ,102);
        Commands_List.put("rm"   ,103);
        Commands_List.put("mkdir",104);
        Commands_List.put("rmdir",105);
        Commands_List.put("touch",106);
        Commands_List.put("echo" ,107);
        Commands_List.put("cat"  ,108);
        Commands_List.put("mv"   ,109);
        Commands_List.put("more" ,110);

        Commands_List.put(">"    ,111);
        Commands_List.put(">>"   ,112);
        Commands_List.put("<"    ,113);
        Commands_List.put("<<"   ,114);
    }
    protected void CommandExtraction(String command_line){
        String []tokens = command_line.split(" ");
        ArrayList<String>Command_Arguments = new ArrayList<>();
        int Function_Code = -1;

        for(int i=0; i<tokens.length; i++){
            if(tokens[i].equals("&")) {
                if(Function_Code!=-1)
                    CommandExecute(Function_Code, Command_Arguments);
                Function_Code = -1;
                Command_Arguments.clear();
            }else{
                Command_Arguments.add(tokens[i]);
                Function_Code = (Commands_List.get(tokens[i]) == null? Function_Code : Commands_List.get(tokens[i]));
                if(Function_Code == 107){
                    Echo(command_line);
                    return;
                }
            }
        }
        if(!Command_Arguments.isEmpty() && Function_Code!=-1)
            CommandExecute(Function_Code, Command_Arguments);

        if(Function_Code==-1)
            System.err.println("Error. can't solve null as command\n  Make Sure that commands are Sensitive Keywords");
    }

    protected void CommandExecute(Integer code,ArrayList<String>Command_Arguments){
        switch(code){
            case 1:     Help();     break;
            case 2:     Exit();     break;
            case 3:     Clear();    break;
            case 4:     PWD();      break;
            case 5:     Date();     break;
            case 100:   LS();       break;
            case 101:   CD(Command_Arguments);      break;
            case 102:   CP(Command_Arguments);      break;
            case 103:   RM(Command_Arguments);      break;
            case 104:   MKDIR(Command_Arguments);   break;
            case 105:   RMDIR(Command_Arguments);   break;
            case 106:   Touch(Command_Arguments);   break;
            //case 107 -> Echo(command_line);
            case 108:   CAT(Command_Arguments);     break;
            case 109:   MV(Command_Arguments);      break;
            case 110:   MORE(Command_Arguments);    break;
        }
    }

}

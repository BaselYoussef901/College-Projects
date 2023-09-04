import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Terminal extends Command_Check {
    protected void Help(){
        System.out.println(
                "\t\thelp : prints all the command the program can process\n" +
                        "\t\texit : Stop all and close cmd\n" +
                        "\t\tclear: Clears the current terminal screen\n" +
                        "\t\tcd   : Changes the current directory\n" +
                        "\t\tls   : Lists the contents (files & directories) of the current directory\n" +
                        "\t\tcp   : copies one or more files to a directory\n" +
                        "\t\tmv   : moves one or more files/directories to a directory\n" +
                        "\t\trm   : removes each given file\n" +
                        "\t\tmkdir: creates a new directory with each given name\n" +
                        "\t\trmdir: removes each given directory only if it is empty\n" +
                        "\t\tcat  : concatenates the content of the files and prints it\n" +
                        "\t\tmore : allows us to display and scroll down the output in one direction only\n" +
                        "\t\tpwd  : prints the working directory\n" +
                        "\t\tdate : displays or sets the date and time of the system\n" +
                        "\t\t>    : redirect the standard output of a command to a file (overwrite)\n" +
                        "\t\t>>   : similar to single but append the output to a file (append)\n" +
                        "\t\t<    : redirect the standard output of a command from a file (overwrite)\n" +
                        "\t\t<<   : similar to single but append the output from a file (append)\n"
        );
    }
    protected void Exit(){
        System.out.println("System has stopped running..");
        System.exit(1);
    }
    protected void Clear(){
        for(int i=0; i<25; i++){
            System.out.println();
        }
    }
    protected void LS(){
        LS_list();
        LS_printDirectories();
        LS_printFiles();
    }
    protected void CD(ArrayList<String>args){
        LS_list();
        if(CD_Exceptions(args)){
            if(args.get(1).equals(".."))
                CD_Back();
            else
                CD_Directory(args.get(1));
            LS_list();
        }
    }
    protected void CP(ArrayList<String>args){
        if(CP_Execptions(args)){
            CP_Files(args);
        }
    }
    protected void RM(ArrayList<String>args){
        if(RM_Exceptions(args)){
            RM_Files(args);
        }
    }
    protected void MKDIR(ArrayList<String>args){
        if(MKDIR_Exceptions(args)){
            Make_Directory(args);
        }
    }
    protected void RMDIR(ArrayList<String>args){
        if(RMDIR_Exceptions(args)){
            Remove_Directory(args);
        }
    }
    protected void PWD(){
        String current_path = System.getProperty("user.dir") + '\\';
        System.out.println(current_path);
    }
    protected void Date(){
        String time_date = new SimpleDateFormat("yyyy_MM_dd | HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println(time_date);
    }
    protected void Touch(ArrayList<String>args){
        if(Touch_Exceptions(args)){
            Create_Files(args);
        }
    }
    protected void Echo(String WholeMessage){
        ArrayList<String>Messages = new ArrayList<>();
        String each_command = "";
        //Adding each line command
        for(int i=0; i<WholeMessage.length(); i++){
            if(WholeMessage.charAt(i)=='&'){
                if(each_command.charAt(0) == ' ')
                    each_command = each_command.substring(1);
                if(each_command.charAt(each_command.length()-1) == ' ')
                    each_command = each_command.substring(0,each_command.length()-1);
                Messages.add(each_command);
                each_command = "";
                continue;
            }
            each_command += WholeMessage.charAt(i);
        }
        if(!each_command.isEmpty()){
            if(each_command.charAt(0) == ' ')
                each_command = each_command.substring(1);
            if(each_command.charAt(each_command.length()-1) == ' ')
                each_command = each_command.substring(0,each_command.length()-1);
            Messages.add(each_command);
        }


        //Filtering to hold only echo's command
        ArrayList<String> New_Messages = new ArrayList<>();
        for(int i=0; i<Messages.size(); i++){
            if(Messages.get(i).substring(0,4).equals("echo")){
                New_Messages.add(Messages.get(i));
            }
        }
        Print_Line(New_Messages);
    }

    protected void CAT(ArrayList<String>args){
        if(CAT_Exceptions(args)){
            Print_Content(args);
        }
    }
    protected void MV(ArrayList<String>args){
        if(MV_Exceptions(args)){
            Move(args);
        }
    }
    protected void MORE(ArrayList<String>args){
        if(MORE_Exceptions(args)){
            Enter_Print(args);
        }
    }
    protected void SR(){    //Single_redirection

    }
    protected void DR(){    //Double_redirection

    }




}

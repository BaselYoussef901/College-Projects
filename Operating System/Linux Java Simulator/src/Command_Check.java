import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;


public class Command_Check {
    /**                                 General Check                               **/
    final protected Boolean isDirectory(String DirectoryName){
        return DirectoryName.indexOf('.') == -1;
    }
    final protected Boolean isEmpty(String DirectoryName){
        String curr_dir = System.getProperty("user.dir");
        String inside_dir = System.getProperty("user.dir") +'\\'+ DirectoryName;
        System.setProperty("user.dir" , inside_dir);
        LS_list();
        System.setProperty("user.dir" , curr_dir);
        if(Directors.size() <= 0 && Files.size() <= 0){
            LS_list();
            return true;
        }else{
            LS_list();
            return false;
        }
    }
    final protected Boolean searchDirectory(String DirectoryName){
        return Directors.contains(DirectoryName);
    }
    final protected Boolean searchFile(String FileName){
        return Files.contains(FileName);
    }















    /**                                     LS                                          **/
    private ArrayList<String> Directors = new ArrayList<>();
    private ArrayList<String> Files     = new ArrayList<>();
    final protected void LS_list(){
        File[]Directories = new File(System.getProperty("user.dir")).listFiles();
        Directors = new ArrayList<>();
        Files = new ArrayList<>();
        assert Directories != null;
        for(File file:Directories){
            if(file.getName().indexOf('.') == -1)
                Directors.add(file.getName().toLowerCase());
            else
                Files.add(file.getName().toLowerCase());
        }
    }
    final protected void LS_printDirectories(){
        for(String dir:Directors)
            System.out.println("\u001B[36m"+dir);
        //Reset color
        System.out.print("\u001B[0m");
    }
    final protected void LS_printFiles(){
        for(String file:Files)
            System.out.println("\u001B[0m"+file);
    }



















    /**                                     CD                                      **/
    final protected boolean CD_Exceptions(ArrayList<String>args){
        if(args.size()>2)   {   System.err.println("cd: too many arguments");   return false;   }
        if(args.size()<2)   {   System.err.println("cd: few arguments");        return false;   }
        if(!searchDirectory(args.get(1))){
            System.err.println(args.get(1) + ": No such file or directory");
        }
        return true;
    }
    final protected void CD_Directory(String DirectoryName){
        if(searchDirectory(DirectoryName)) {
            String new_directory = System.getProperty("user.dir") + '\\' + DirectoryName;
            System.setProperty("user.dir", new_directory);
        }
    }
    final protected void CD_Back(){
        String new_directory = System.getProperty("user.dir");
        for(int i=new_directory.length()-1; i>=0; --i){
            if(new_directory.charAt(i) == '\\'){
                new_directory = new_directory.substring(0,i);
                break;
            }
        }
        System.setProperty("user.dir" , new_directory);
    }

















    /**                                     CP                                       **/
    final protected boolean CP_Execptions(ArrayList<String>args){
        if(args.size()<3){
            System.err.println("cp: few arguments");
            return false;
        }
        if(!searchDirectory(args.get(args.size()-1).toLowerCase())){
            System.err.println(args.get(args.size()-1) + ": is not a directory");
            return false;
        }
        return true;
    }
    final protected void CP_Files(ArrayList<String>args){
        try{
            int n_directors = args.size()-1;
            File src_file , des_file;
            for (int i = 1; i < n_directors; i++) {
                if(searchFile(args.get(i))) {
                    src_file = new File(System.getProperty("user.dir") + '\\' + args.get(i));
                    des_file = new File(System.getProperty("user.dir") + '\\' + args.get(n_directors) + '\\' + args.get(i));

                    java.nio.file.Files.copy(src_file.toPath(), des_file.toPath());
                }else
                    System.err.println(args.get(i)+": file not exist");
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }


















    /**                                     RM                                          **/
    final protected boolean RM_Exceptions(ArrayList<String>args){
        if(args.size()<2){
            System.err.println("rm: few arguments");
            return false;
        }
        return true;
    }
    final protected void RM_Files(ArrayList<String>args){
        int n_directors = args.size();
        for(int i = 1; i<n_directors; i++){
            if(isDirectory(args.get(i)))
                System.err.println(args.get(i)+": is not a file");
            else{
                if(searchFile(args.get(i))){
                    File current_file = new File(System.getProperty("user.dir") + '\\' + args.get(i));
                    current_file.delete();
                }else
                    System.err.println(args.get(i) +": does not exist");
            }
        }
    }
    final protected void RM_FullDirectories(ArrayList<String>args){

    }



















    /**                                     MKDIR                                          **/
    final protected boolean MKDIR_Exceptions(ArrayList<String>args){
        if(args.size()<2){
            System.err.println("mkdir: few arguments");
            return false;
        }
        return true;
    }
    final protected void Make_Directory(ArrayList<String>args){
        for(int i=1; i<args.size(); i++){
            if(isDirectory(args.get(i)))
                new File(System.getProperty("user.dir") + '\\' + args.get(i)).mkdir();
            else
                System.err.println(args.get(i)+": is not a directory");
        }
    }




















    /**                                     RMDIR                                          **/
    final protected boolean RMDIR_Exceptions(ArrayList<String>args){
        if(args.size()<2){
            System.err.println("rmdir: few arguments");
            return false;
        }
        return true;
    }
    final protected void Remove_Directory(ArrayList<String>args){
        for(int i=1; i<args.size(); i++){
            ///Check if it is a directory or not
            if(!isDirectory(args.get(i))){
                System.err.println(args.get(i)+": is not a directory");
                continue;
            }
            ///Check if there's a directory called by that name
            if(!searchDirectory(args.get(i)))
                System.err.println(args.get(i)+": No such directory");
                ///Check if there's a directory Empty or not
            else{
                if(!isEmpty(args.get(i)))
                    System.err.println(args.get(i)+": is not empty");
                else{
                    File current_file = new File(System.getProperty("user.dir") +'\\'+ args.get(i));
                    current_file.delete();
                }
            }
        }
    }




















    /**                                     Touch                                          **/
    final protected boolean Touch_Exceptions(ArrayList<String>args){
        if(args.size()<2){
            System.err.println("touch: few arguments");
            return false;
        }
        return true;
    }
    final protected void Create_Files(ArrayList<String>args){
        for(int i=1; i<args.size(); i++){
            if(isDirectory(args.get(i))){
                System.err.println(args.get(i)+": is not a file");
                continue;
            }
            try{
                new File(System.getProperty("user.dir") + '\\' + args.get(i)).createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }















    /**                                     Echo                                          **/
    final protected void Print_Line(ArrayList<String>Messages){
        for(int j=0; j<Messages.size(); j++){
            String WholeMessage = Messages.get(j);
            int start_quote=-1 , end_quote=-1;
            boolean start = true;
            for(int i=4; i<WholeMessage.length(); i++){
                if(WholeMessage.charAt(i)==' ')
                    continue;
                if(WholeMessage.charAt(i)==(char)39 || WholeMessage.charAt(i)==(char)34 || (WholeMessage.charAt(i)>=97 && WholeMessage.charAt(i)<=122)){
                    if(start){
                        start_quote = i;
                        start = false;
                    }else
                        end_quote = i+1;
                }
            }
            if(start_quote > end_quote){
                end_quote = WholeMessage.length()-1;
            }
            String line_out = WholeMessage.substring(start_quote,end_quote);
            line_out = line_out.replace(String.valueOf((char)39) , "");
            line_out = line_out.replace(String.valueOf((char)34) , "");
            System.out.println(line_out);
        }
    }

















    /**                                         Cat                                          **/
    final protected boolean CAT_Exceptions(ArrayList<String>args){
        if(args.size()<2){
            System.err.println("cat: few arguments");
            return false;
        }
        if(args.size()>2){
            System.err.println("cat: too many arguments");
            return false;
        }
        if(isDirectory(args.get(1))){
            System.err.println(args.get(1)+": is not a file");
            return false;
        }
        return true;
    }
    final protected void Print_Content(ArrayList<String>args){
        //how to open and read from a file in java
        try{
            File read_file = new File(System.getProperty("user.dir") + '\\' + args.get(1));
            Scanner read_line = new Scanner(read_file);
            while(read_line.hasNextLine())
                System.out.println(read_line.nextLine());
            read_line.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }























    /**                                         MV                                          **/
    final protected boolean MV_Exceptions(ArrayList<String>args){
        if(args.size()<3){
            System.err.println("mv: few arguments");
            return false;
        }
        if(args.size()>3 && !isDirectory(args.get(args.size()-1))){
            System.err.println(args.get(args.size()-1)+": is not a directory");
            return false;
        }
        return true;
    }
    final protected void Move(ArrayList<String>args){
        int n_directors = args.size();
        if(isDirectory(args.get(n_directors-1)) && CP_Execptions(args)){
            CP_Files(args);
            //Delete copied files (not the last - directory)
            for(int i=1; i<n_directors-1; i++){
                File delete_files = new File(System.getProperty("user.dir") + '\\' + args.get(i));
                delete_files.delete();
            }
        }
        else{
            //More than 2 files been error message from exceptions - it has to be 2 files only
            if(searchFile(args.get(1))){
                if(searchFile(args.get(2))){
                    //file2 exist
                    File src_file = new File(System.getProperty("user.dir"), args.get(1));
                    File des_file = new File(System.getProperty("user.dir"), args.get(2));
                    des_file.delete();
                    boolean renamed = src_file.renameTo(des_file);
                }else{
                    //file2 not exist
                    try {
                        String exe = args.get(1).substring(args.get(1).indexOf('.') , args.get(1).length()-1);
                        String des = args.get(2);
                        if(des.indexOf('.') == -1)
                            des += exe;
                        File src_file = new File(System.getProperty("user.dir"), args.get(1));
                        File des_file = new File(System.getProperty("user.dir"), des);
                        boolean renamed = src_file.renameTo(des_file);
                    } catch (SecurityException e) {
                        e.getMessage();
                    }
                }
            }else
                System.err.println(args.get(1)+": no such file");
        }
    }
















    /**                                     More                                          **/
    final protected boolean MORE_Exceptions(ArrayList<String>args){
        if(args.size()>2){
            System.err.println("more: too many arguments");
            return false;
        }
        if(args.size()<2){
            System.err.println("more: few arguments");
            return false;
        }
        if(isDirectory(args.get(1))){
            System.err.println(args.get(1)+": is not a file");
            return false;
        }
        if(!searchFile(args.get(1))){
            System.err.println(args.get(1)+": no such file");
            return false;
        }
        return true;
    }
    final protected void Enter_Print(ArrayList<String>args){
        try{
            ///Taking Whole Text into array
            File read_file = new File(System.getProperty("user.dir") + '\\' + args.get(1));
            Scanner read_line = new Scanner(read_file);
            ArrayList<String>Line_by_Line = new ArrayList<>();
            while(read_line.hasNextLine())
                Line_by_Line.add(read_line.nextLine());
            read_line.close();


           ///File Out
            int Quarter = 0;
            int File_Size = Line_by_Line.size();
            int Percent = File_Size/4;
            String More_Please;
            for(int i=0; i<File_Size; ){
                if(Quarter == Percent) {
                    System.out.print("\t \u001B[34m" + "------More-----");
                    System.out.print("\u001B[00m");
                    Scanner Yes = new Scanner(System.in);
                    More_Please = Yes.nextLine();
                    if(More_Please.isEmpty())
                            Quarter = 0;
               }
                if(Quarter < Percent) {
                    System.out.println(Line_by_Line.get(i));
                    Quarter++;
                    i++;
                }
                else
                    break;
           }
            System.out.println("\t \u001B[34m" + "-----DONE-----");
            System.out.print("\u001B[00m");

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }





























    /**                                     Operators                                          **/
























}

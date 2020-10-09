import java.io.*;
import java.util.Scanner;

class Main {
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a delimiter: ");
        char delimiter = in.nextLine().toCharArray()[0];
        System.out.print("Input a char for concantination: ");
        char conc = in.nextLine().toCharArray()[0];

        in.close();

        Process("input.txt", "output.txt", delimiter, conc);
    }
    public static void Process(String inputFile, String outputFile, char delimiter, char conc) {
        char[] lineArray;
        char quote = '"';
        String word = "";
        try(
                BufferedReader inp = new BufferedReader(new FileReader(
                        inputFile));
                FileWriter outp = new FileWriter(outputFile)

        )
        {
            String line = inp.readLine();

            int i = 0;
            boolean isComment = false;
            boolean isIgnoreDelimiter = false;
            if(line != null)
            {
                lineArray = line.toCharArray();
                while (true) {
                    if(lineArray[i] == delimiter && !isIgnoreDelimiter)
                    {
                        System.out.println(word);
                        outp.write(Integer.toString(word.length()));
                        outp.write(conc);
                        word = "";
                        i++;
                    }
                    else if(lineArray[i] == quote)
                    {
                        isIgnoreDelimiter = !isIgnoreDelimiter;
                        i++;
                    }
                    else if(lineArray[i] == '/')
                    {
                        i++;
                        if(i < lineArray.length && lineArray[i] == '*')
                        {
                            isComment = true;
                            i++;

                            while(isComment)
                            {
                                if(i >= lineArray.length)
                                {
                                    line = inp.readLine();
                                    lineArray = line.toCharArray();
                                    i = 0;
                                }
                                if(lineArray[i] == '*')
                                {
                                    i++;
                                    if(i < lineArray.length && lineArray[i] == '/')
                                    {
                                        isComment = false;
                                        if(i >= lineArray.length - 1)
                                        {
                                            line = inp.readLine();
                                            lineArray = line.toCharArray();
                                            i = 0;
                                        }
                                    }

                                }
                                else{ i++; }
                            }
                        }
                        else
                        {
                            word += "/"; i--;
                        }
                    }
                    else
                    {
                        word += lineArray[i];
                        i++;
                    }
                    if(i >= lineArray.length)
                    {
                        System.out.println(word);
                        outp.write(Integer.toString(word.length()));
                        outp.write('\n');
                        word = "";

                        line = inp.readLine();
                        if(line == null) break;
                        lineArray = line.toCharArray();
                        i = 0;

                    }
                }

            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}
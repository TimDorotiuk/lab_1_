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
        System.out.print("Done...");
    }
    public static String ProcessWord(String word)
    {
        if(word.length() == 1) return word;
        char quote = '"';
        char space = ' ';
        char[] wordArray = word.toCharArray();
        // find the first non-space character
        int i = 0;
        while(i < wordArray.length)
        {
            if(wordArray[i] != space) break;
            i++;
        }
        // find the last non-space character
        int j = wordArray.length - 1;
        while(j >= 0)
        {
            if(wordArray[j] != space) break;
            j--;
        }
        if ((i < j) && (i < wordArray.length) && (j >= 0))
            if (wordArray[i] == quote && wordArray[j] == quote)
                word = word.substring(i + 1, j);

        return word;
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
            boolean isEnd = false;
            if(line != null)
            {
                lineArray = line.toCharArray();
                while (!isEnd) {
                    if(lineArray[i] == delimiter && !isIgnoreDelimiter)
                    {
                        // Print a word
                        word = ProcessWord(word);
                        System.out.println(word);
                        outp.write(Integer.toString(word.length()));
                        // end print

                        outp.write(conc);
                        word = "";
                        i++;
                    }
                    else if(lineArray[i] == quote)
                    {
                        isIgnoreDelimiter = !isIgnoreDelimiter;
                        word += quote;
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
                                    if(line == null)
                                    {
                                        isEnd = true; break;
                                    }
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
                                            if(line == null)
                                            {
                                                isEnd = true; break;
                                            }
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
                        // Print a word
                        word = ProcessWord(word);
                        System.out.println(word);
                        outp.write(Integer.toString(word.length()));
                        // end print

                        outp.write('\n');
                        word = "";

                        line = inp.readLine();
                        if(line == null) break;
                        lineArray = line.toCharArray();
                        isIgnoreDelimiter = false;
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
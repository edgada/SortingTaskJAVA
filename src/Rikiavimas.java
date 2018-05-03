import java.io.*;
import java.util.ArrayList;

public class Rikiavimas {

    public static String surikiuotiFaila(String orgFailoPavadinimas)
    {
        try
        {
            ArrayList<Eilute> visosEilutes = new ArrayList<Eilute>();
            String naujoFailoPavadinimas = "output.txt";

            String line = null;

            FileReader fileReader = new FileReader(orgFailoPavadinimas);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                String[] eilutesZodziai = line.split("\t");
                Eilute temp = new Eilute(eilutesZodziai);
                visosEilutes.add(temp);
            }

            bufferedReader.close();


            //--surikiuojam duomenis--------------------------------------------------------------------------------------------------
            for (int j = 0; j < visosEilutes.size() - 1; j++)
            {
                for (int i = 0; i < visosEilutes.size() - 1; i++)
                {
                    String a = "";
                    String b = "";

                    for (int s = 0; s < visosEilutes.get(i).getZodziai().length; s++)
                    {
                        a = visosEilutes.get(i).getZodziai()[s];
                        b = visosEilutes.get(i+1).getZodziai()[s];

                        if (!a.equals(b)) break;
                    }

                    int kurisDidesnis = raskDidesni(a, b);

                    if (kurisDidesnis > 0)
                    {
                        Eilute temp = visosEilutes.get(i);

                        visosEilutes.set(i, visosEilutes.get(i+1));
                        visosEilutes.set(i+1, temp);
                    }

                }
            }

            //--rezultata issaugojam naujamr faile------------------------------------------------------------------------------------
            for (Eilute e : visosEilutes)
            {
                String eilute = "";
                for (String s : e.getZodziai())
                {
                    eilute += s + "\t";
                }
                irasomIfaila(eilute);
            }

            return naujoFailoPavadinimas;
        }
        catch (Exception e)
        {
            return "0";
        }
    }

    public static int raskDidesni(String a, String b)
    {
        int kurisDidesnis;

        if ((a == null && a.isEmpty()) && (b == null && b.isEmpty())) kurisDidesnis = 0;
        else if ((a == null && a.isEmpty())) kurisDidesnis = 1;
        else if ((b == null && b.isEmpty())) kurisDidesnis = -1;
        else
        {
            if (isNumeric(a) && isNumeric(b))
            {
                double aa = getSkaicius(a);
                double bb = getSkaicius(b);

                if(aa>bb) kurisDidesnis = 1;
                else if(aa<bb) kurisDidesnis = -1;
                else kurisDidesnis = 0;
            }
            else if (!isNumeric(a) && !isNumeric(b))
            {
                kurisDidesnis = a.compareTo(b);
            }
            else if (isNumeric(a)) kurisDidesnis = -1;
            else kurisDidesnis = 1;
        }

        return kurisDidesnis;
    }
    private static boolean isNumeric(String str)
    {
        try
        {
            getSkaicius(str);
        }
        catch(Exception ne)
        {
            return false;
        }
        return true;
    }
    private static double getSkaicius(String str)
    {
        if (str.startsWith("\uFEFF")) {
            str = str.substring(1);
        }
        double d = Double.parseDouble(str.trim());
        return  d;
    }

    private static void irasomIfaila(String info)
    {
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            File file = new File("output.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            bw.write(info + "\r\n");

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

}

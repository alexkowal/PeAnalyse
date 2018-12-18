package edu.analyser;

import com.github.katjahahn.parser.FileFormatException;
import com.github.katjahahn.parser.PEData;
import com.github.katjahahn.parser.PELoader;
import com.github.katjahahn.parser.sections.SectionLoader;
import com.github.katjahahn.parser.sections.idata.ImportDLL;
import com.github.katjahahn.parser.sections.idata.ImportSection;
import com.github.katjahahn.parser.sections.idata.NameImport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    static ArrayList<File> files = new ArrayList<File>();

    public static void listFilesForFolder(final File folder) {

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                if (fileEntry.getName().contains(".exe"))
                    if (fileEntry.canExecute())
                        files.add(fileEntry);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        listFilesForFolder(new File("/Users/aleksandr/ЗОС:ОКЭ/PrgEx"));
        BufferedReader br = new BufferedReader(new FileReader("baza.txt"));
        ArrayList<String> base = new ArrayList<String>();
        String temp = "";
        while ((temp = br.readLine()) != null)
            base.add(temp);

        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            {
                try {
                    PEData data = PELoader.loadPE(file);
                    System.out.println("<----------------------------------------------------------->");
                    System.out.println("Обработка программы " + file.getName() + ":" + "\n");

                   /* ReportCreator reporter = new ReportCreator(data);
                    // System.out.println(reporter.importsReport());*/


                    SectionLoader loader = new SectionLoader(data);
                    ImportSection idata;
                    try {
                        idata = loader.loadImportSection();
                    } catch (IllegalStateException e) {
                        return;
                    }
                    if (data == null)
                        continue;

                    List<ImportDLL> list = idata.getImports();

                    for (ImportDLL dll : list) {
                        for (NameImport nameImport : dll.getNameImports()) {
                            String name = nameImport.getName();

                            for (String s : base)
                                if (name.contains(s)) {
                                    System.out.println(file.getName() + " have Internet Connection with function "
                                            + name + "\n");
                                    break;
                                }
                        }
                    }

//                    System.out.println();
                } catch (FileFormatException e) {
                    System.out.println("File can't execute");
                    // break;
                }
            }
        }
    }
}











 /* System.out.println("Imports from " + dll.getName());
                        for (NameImport nameImport : dll.getNameImports()) {
                            System.out.print("Name: " + nameImport.getName());
                            System.out.print(" Hint: " + nameImport.getHint());
                            System.out.println(" RVA: " + nameImport.getRVA());
                        }
                        for (OrdinalImport ordImport : dll.getOrdinalImports()) {
                            System.out.println("Ordinal: " + ordImport.getOrdinal());

                        }*/
